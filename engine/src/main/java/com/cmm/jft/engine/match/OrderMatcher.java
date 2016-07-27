/**
 * 
 */
package com.cmm.jft.engine.match;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import quickfix.Message;
import quickfix.SessionID;

import com.cmm.jft.engine.SessionRepository;
import com.cmm.jft.engine.enums.MatchTypes;
import com.cmm.jft.engine.enums.StreamTypes;
import com.cmm.jft.engine.marketdata.UMDF;
import com.cmm.jft.messaging.MessageEncoder;
import com.cmm.jft.messaging.MessageRepository;
import com.cmm.jft.messaging.MessageSender;
import com.cmm.jft.messaging.fix44.Fix44EngineMessageEncoder;
import com.cmm.jft.trading.OrderEvent;
import com.cmm.jft.trading.Orders;
import com.cmm.jft.trading.enums.ExecutionTypes;
import com.cmm.jft.trading.enums.OrderStatus;
import com.cmm.jft.trading.enums.OrderValidityTypes;
import com.cmm.jft.trading.enums.Side;
import com.cmm.jft.trading.enums.WorkingIndicator;
import com.cmm.jft.trading.exceptions.OrderException;

/**
 * @author Cristiano M Martins
 * @version 17/08/15 11:17:42
 *
 */
public class OrderMatcher implements MessageSender {


	private class StopOrderReleaser implements Runnable {

		private PriorityBlockingQueue<Orders> queue;

		public StopOrderReleaser(PriorityBlockingQueue<Orders> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {

			while(verifyStopOrders) {
				queue.stream().filter(
						o -> o.getWorkingIndicator() == WorkingIndicator.No_Working && 
						o.getStopPrice() == lastPrice)
				.forEach(o -> o.setWorkingIndicator(WorkingIndicator.Working)
						);

			}
		}

	}



	private int phase;
	private boolean verifyStopOrders;
	private double highPrice;
	private double lowPrice;
	private double lastPrice;
	private double vwapPrice;
	private double lastVolume;
	private double totalVolume;
	private int totalTrades;
	
	private double sumPriceVolume;
	
	private UMDF umdf;
	
	private double protectionLevel;
	private PriorityBlockingQueue<Orders> buyQueue;
	private PriorityBlockingQueue<Orders> sellQueue;
	private ConcurrentHashMap<String, Orders> orders;
	

	public OrderMatcher(MatchTypes matchTypes, double protectionLevel, UMDF umdf) {
		this.umdf = umdf;
		this.verifyStopOrders = true;
		this.protectionLevel = protectionLevel;
		if(matchTypes == MatchTypes.FIFO) {
			this.orders = new ConcurrentHashMap<>(1000000);
			this.buyQueue = new PriorityBlockingQueue<>(1000000, new PriceTimeComparator());
			this.sellQueue = new PriorityBlockingQueue<>(1000000, new PriceTimeComparator());
		}

		//inicializa os verificadores de ordens stop
		new Thread(new StopOrderReleaser(buyQueue)).start();
		new Thread(new StopOrderReleaser(sellQueue)).start();

	}

	/**
	 * @return the highPrice
	 */
	public double getHighPrice() {
		return this.highPrice;
	}
	
	/**
	 * @return the lowPrice
	 */
	public double getLowPrice() {
		return this.lowPrice;
	}
	
	public double getLastPrice() {
		return lastPrice;
	}
	
	/**
	 * @return the vwapPrice
	 */
	public double getVwapPrice() {
		return this.vwapPrice;
	}

	public double getLastVolume() {
		return lastVolume;
	}
	
	/**
	 * @return the totalVolume
	 */
	public double getTotalVolume() {
		return this.totalVolume;
	}
	
	/**
	 * @return the totalTrades
	 */
	public int getTotalTrades() {
		return this.totalTrades;
	}
	
	

	/**
	 * @return the buyQueue
	 */
	public PriorityBlockingQueue<Orders> getBuyQueue() {
		return this.buyQueue;
	}

	/**
	 * @return the sellQueue
	 */
	public PriorityBlockingQueue<Orders> getSellQueue() {
		return this.sellQueue;
	}

	public boolean addOrder(Orders order) throws OrderException {
		boolean add = false;
		order.setWorkingIndicator(WorkingIndicator.Working);
		add = execute(order);

		return add;
	}

	private boolean addOnBook(Orders ordr) {
		boolean add = false;

		if(ordr.getSide() == Side.BUY) {
			add = buyQueue.offer(ordr);
		}
		else {
			add = sellQueue.offer(ordr);
		}

		orders.put(ordr.getClOrdID(), ordr);

		return add;
	}


	private boolean execute(Orders ordr) {

		boolean executed = false;
		switch(ordr.getOrderType()) {
		case Market:
			executed = executeMarketWithProtection(ordr);
			break;
		case Limit:
			executed = executeLimit(ordr);
			break;
		case Stop:
			adjustProtectionPrice(ordr);
			executed = addOnBook(ordr);//nao executa stop mas sim adiciona no "stop book" e aguarda para ser ativada
			break;
		case StopLimit:
			executed = addOnBook(ordr);//nao executa stoplimit, deve inserir no stop book e aguardar o gatilho
			break;
		case MarketWithLeftOverAsLimit:
			executed = executeMarketToLimit(ordr);
			break;
		}

		return executed;
	}

	private boolean fillOrders(Orders newOrder, Orders bookOrder, double qtyToFill, double priceToFill){
		boolean send = false;

		OrderEvent orderFill = new OrderEvent(ExecutionTypes.TRADE, qtyToFill, priceToFill);
		OrderEvent bookFill = new OrderEvent(ExecutionTypes.TRADE, qtyToFill, priceToFill);
		try {
			if(newOrder.addExecution(orderFill) && bookOrder.addExecution(bookFill)) {

				//ajusta os valores para ultima execucao
				lastPrice = priceToFill;
				highPrice = highPrice < priceToFill ? priceToFill : highPrice;
				lowPrice = lowPrice > priceToFill ? priceToFill : lowPrice;
				lastVolume = qtyToFill;
				totalVolume += qtyToFill;
				totalTrades++;
				
				sumPriceVolume += lastPrice * lastVolume;
				vwapPrice = sumPriceVolume/totalVolume;

				//recupera a sessao da ordem recebida
				SessionID orderSession = SessionRepository.getInstance().getSession(StreamTypes.ENTRYPOINT, newOrder.getTraderID());
				Fix44EngineMessageEncoder encoder = (Fix44EngineMessageEncoder) MessageEncoder.getEncoder(orderSession); 
				send = sendMessage(encoder.executionReport(orderFill), orderSession);

				//recupera a sessao da ordem que estava no book
				SessionID bookOrderSession = SessionRepository.getInstance().getSession(StreamTypes.ENTRYPOINT, bookOrder.getTraderID());
				send = sendMessage(encoder.executionReport(bookFill), bookOrderSession);
				
				//cria um evento de trade e um de vwap para enviar o MD
				umdf.informTrade(orderFill, bookFill, vwapPrice, totalVolume);
				
			}else {
				throw new OrderException("Error on add executions.");
			}

		} catch (OrderException e) {
			e.printStackTrace();
			send = false;
		}

		return send;
	}

	private PriorityBlockingQueue<Orders> getCounterpartyBookOrders(Side side) {

		if(side == Side.BUY) {
			return sellQueue;
		}

		return buyQueue;
	}

	private void adjustProtectionPrice(Orders ordr) {

		/*
		 * For bids, the protection price calculated is by adding an offset to the last trade price. 
		 * For offers, the offset is subtracted from the last trade. The protection price cannot be 
		 * specified in the incoming order.
		 */
		double offset = (lastPrice * protectionLevel) * (ordr.getSide()==Side.BUY? 1:-1);
		ordr.setProtectionPrice(lastPrice + offset);

	}

	/**
	 * Execute Market type orders received;
	 * @param ordr order to execute;
	 */
	private boolean executeMarketWithProtection(Orders ordr) {
		boolean exec = false;

		adjustProtectionPrice(ordr);
		exec = generalExecute(ordr, ordr.getProtectionPrice(), ordr.getVolume());

		return exec;
	}

	private boolean executeLimit(Orders ordr) {
		boolean exec = false;

		exec = generalExecute(ordr, ordr.getPrice(), ordr.getVolume());

		return exec;
	}

	private boolean executeMarketToLimit(Orders ordr) {
		boolean exec = false;

		exec = generalExecute(ordr, ordr.getPrice(), ordr.getVolume());

		return exec;
	}


	private boolean generalExecute(Orders ordr, double orderPrice, double orderVolume) {
		boolean exec = false;

		PriorityBlockingQueue<Orders> bookOrders = getCounterpartyBookOrders(ordr.getSide());
		List<OrderEvent> execs = createExecutions(ordr.getSide(), orderPrice, orderVolume);
		try {
			//verifica se pode executar a ordem 
			if(validateExecution(ordr, execs)) {

				for(OrderEvent ex:execs) {

					//recupera a ordem
					Orders bookOrdr = orders.get(ex.getOrderID().getClOrdID());

					//adiciona as execucoes e as informa para os participantes
					exec = fillOrders(ordr, bookOrdr, ex.getVolume(), ex.getPrice());

					//remove the filled order from book
					if(bookOrdr.getOrderStatus() != OrderStatus.PARTIALLY_FILLED) {
						orders.remove(ex.getOrderID());
					}
					else {//
						bookOrdr.setWorkingIndicator(WorkingIndicator.Working);
					}

				}

				//adiciona o restante da ordem recebida no book
				if(ordr.getOrderStatus() == OrderStatus.PARTIALLY_FILLED || ordr.getOrderStatus() == OrderStatus.NEW) {

					if(ordr.getValidityType() == OrderValidityTypes.IOC) {
						cancelOrder(ordr, true);
					}else {
						//ajusta o tipo da ordem para Limit
						ordr.changeToLimit(orderPrice);
						exec = addOnBook(ordr);
					}

				}

			}else {
				//cancela a ordem e informa que a ordem nao podera ser executada
				exec = false;
				cancelOrder(ordr, false);

			}

		}catch(OrderException e) {

		}


		return exec;
	}

	/**
	 * Cria as execucoes para os valores passados por parametro, referenciando
	 * na execucao a ordem que esta presente no book e fara a contra-parte na transacao.
	 * @param side
	 * @param price 
	 * @param volume
	 * @return
	 */
	private ArrayList<OrderEvent> createExecutions(Side side, double price, double volume) {

		ArrayList<OrderEvent> lst = new ArrayList<OrderEvent>();
		PriorityBlockingQueue<Orders> ordrs = getCounterpartyBookOrders(side);

		double cumVolume = 0;
		while(ordrs.iterator().hasNext() && cumVolume < volume) {

			Orders bookOrder = ordrs.iterator().next(); 
			if(bookOrder.getWorkingIndicator() == WorkingIndicator.Working) {
				bookOrder.setWorkingIndicator(WorkingIndicator.No_Working);
				double qtyToFill = 0;
				double priceToFill = bookOrder.getPrice();

				if(volume >= bookOrder.getLeavesVolume()) {
					qtyToFill = bookOrder.getLeavesVolume();
				}
				else {
					qtyToFill = volume;
				}

				if(cumVolume < volume && priceToFill <= price) {
					cumVolume += qtyToFill;
					OrderEvent fill = new OrderEvent(ExecutionTypes.TRADE, qtyToFill, priceToFill);
					fill.setOrderID(bookOrder);
					lst.add(fill);
				}
			}
		}

		return lst;
	}

	private boolean validateExecution(Orders ordr, List<OrderEvent> executions) {
		boolean validExecution = false;

		switch(ordr.getValidityType()) {
		case MOA:
			break;
		case DAY:
			validExecution = true;
			break;
		case GTC:
			validExecution = true;
			break;
		case IOC:
			validExecution = true;
			break;
		case FOK:
			double c=0;
			for(OrderEvent oe:executions) {
				c+=oe.getVolume();
			}
			validExecution = c == ordr.getVolume();
			break;
		case GTD:
			validExecution = new Date().compareTo(ordr.getDuration()) <=0;
			break;
		case MOC:
			validExecution = true;
			break;

		}

		return validExecution;
	}


	private void cancelOrder(Orders ordr, boolean expire) throws OrderException {

		OrderEvent oe = new OrderEvent(ExecutionTypes.CANCELED, ordr.getVolume(), ordr.getPrice());
		oe.setMessage("Order Canceled due to invalid execution.");
		if(expire) {
			oe.setOrdRejReason(-1);
			oe.setMessage("Order expired.");
		}

		ordr.addExecution(oe);

		SessionID bookOrderSession = SessionRepository.getInstance().getSession(StreamTypes.ENTRYPOINT, ordr.getTraderID());
		Fix44EngineMessageEncoder encoder = (Fix44EngineMessageEncoder) MessageEncoder.getEncoder(bookOrderSession);
		sendMessage(encoder.executionReport(oe), bookOrderSession);
	}
	

	@Override
	public boolean sendMessage(Message message, SessionID sessionID) {
		return MessageRepository.getInstance().addMessage(message, sessionID);
	}
	
	

}
