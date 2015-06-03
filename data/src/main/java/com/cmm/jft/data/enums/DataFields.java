package com.cmm.jft.data.enums;

public enum DataFields {

	/**
	 * Simbolo
	 */
	SYMBOL("Simbolo"), 

	/**
	 * Valor da Abertura do dia no ativo.
	 */
	OPN("Valor da Abertura do dia no ativo."), 

	/**
	 * Nome do Ativo
	 */
	NME("Nome do Ativo"),

	/**
	 * Data em que o ativo est� sendo negociado
	 */
	DAT("Data em que o ativo est� sendo negociado"), 

	/**
	 * Valor do fechamento anterior do ativo
	 */
	LST_CLOSE("Valor do fechamento anterior do ativo"), 

	/**
	 * Hora da �ltima negocia��o
	 */
	TIME("Hora da �ltima negocia��o"), 

	/**
	 * Valor M�ximo do Dia
	 */
	HIGH("Valor M�ximo do Dia"), 

	/**
	 * Valor M�nimo do Dia
	 */
	LOW("Valor M�nimo do Dia"), 

	/**
	 * Quantidade de neg�cios no dia
	 */
	TRADES("Quantidade de neg�cios no dia"), 

	/**
	 * Quantidade Negociada
	 */
	TRADED_QT("Quantidade Negociada"), 

	/**
	 * �ltimo Pre�o negociado
	 */
	LST_PRICE("�ltimo Pre�o negociado"), 

	/**
	 * Quantidade do �ltimo Neg�cio
	 */
	LST_TRADE_QT("Quantidade do �ltimo Neg�cio"), 

	/**
	 * Porcentagem entre o valor de fechamento do dia anterior e o valor atual
	 */
	VAR("Porcentagem entre o valor de fechamento do dia anterior e o valor atual"), 

	/**
	 * Volume Financeiro Total do Dia
	 */
	FVT("Volume Financeiro Total do Dia"), 

	/**
	 * Varia��o do valor entre o fechamento e o �ltimo pre�o do preg�o
	 */
	VAP("Varia��o do valor entre o fechamento e o �ltimo pre�o do preg�o"), 

	/**
	 * Volume financeiro do �ltimo neg�cio
	 */
	LFV("Volume financeiro do �ltimo neg�cio"), 

	/**
	 * Oferta de Compra no livro de ofertas
	 */
	BOPx("Oferta de Compra no livro de ofertas"), 

	/**
	 * Oferta de Venda no livro de ofertas
	 */
	SOPx("Oferta de Venda no livro de ofertas"), 

	/**
	 * Quantidade da Oferta de Compra para este determinado pre�o
	 */
	BOQTx("Quantidade da Oferta de Compra para este determinado pre�o"), 

	/**
	 * Quantidade da Oferta de Venda para este determinado pre�o
	 */
	SOQTx("Quantidade da Oferta de Venda para este determinado pre�o"), 

	/**
	 * N�mero de Ofertas de Compra para este determinado pre�o
	 */
	BONx("N�mero de Ofertas de Compra para este determinado pre�o"), 

	/**
	 * N�mero de Ofertas de Venda para este determinado pre�o
	 */
	SONx("N�mero de Ofertas de Venda para este determinado pre�o"), 

	/**
	 * Corretora de Venda
	 */
	SELL("Corretora de Venda"), 

	/**
	 * Corretora de Compra
	 */
	BUY("Corretora de Compra"), 

	/**
	 * C�digo ISIN
	 */
	ISI("C�digo ISIN"), 

	/**
	 * Bolsa
	 */
	MKT("Bolsa"), 

	/**
	 * Tipo
	 */
	TYPE("Tipo"), 

	/**
	 * C�digo da Corretora de Compra
	 */
	BBRK("C�digo da Corretora de Compra"), 

	/**
	 * C�digo da Corretora de Venda
	 */
	SBRK("C�digo da Corretora de Venda"),

	/**
	 * Quoted DateTime
	 */
	QDT("Quoted DateTime"), 

	/**
	 * Acquired DateTime 
	 */
	ADT("Acquired DateTime");

	String description;

	DataFields(String descr) {
		this.description = descr;
	}

}
