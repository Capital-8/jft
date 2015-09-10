/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmm.jft.ui.trading;

import com.cmm.jft.core.format.Formatter;
import com.cmm.jft.core.format.FormatterFactory;
import com.cmm.jft.core.format.FormatterTypes;
import com.cmm.jft.db.DBFacade;
import com.cmm.jft.db.exceptions.DataBaseException;
import com.cmm.jft.security.Security;
import com.cmm.jft.trading.OrderEvent;
import com.cmm.jft.trading.Orders;
import com.cmm.jft.trading.enums.MarketEvents;
import com.cmm.jft.trading.enums.OrderStatus;
import com.cmm.jft.trading.enums.OrderTypes;
import com.cmm.jft.trading.enums.Side;
import com.cmm.jft.trading.enums.TradeTypes;
import com.cmm.jft.trading.services.TradingService;
import com.cmm.jft.ui.forms.AbstractForm;
import com.cmm.jft.ui.forms.Events;
import com.cmm.jft.ui.forms.binding.FormBinder;
import com.cmm.jft.ui.utils.FormUtils;
import com.cmm.jft.ui.utils.GenericTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Level;

import com.cmm.logging.Logging;

/**
 *
 * @author Cristiano Martins
 */
public class OrderForm extends AbstractForm {

    /**
     * Creates new form OrderForm
     */
    public OrderForm() {
	initComponents();
	binder = new FormBinder();
	binder.register("orderSerial", txtSerial);
	binder.register("securityID.symbol", sbSecurity);
	binder.register("volume", ftxtVolume);
	binder.register("side", cmbSide);
	binder.register("orderStatus", cmbOrderStatus);
	binder.register("duration", dtDuration);
	binder.register("orderType", cmbOrderType);
	binder.register("stopGain", ftxtGain);
	binder.register("price", ftxtPrice);
	binder.register("startPrice", ftxtStart);
	binder.register("stopPrice", ftxtStop);

	binder.addFormatter("volume", (Formatter<Integer>) FormatterFactory.getFormatter(FormatterTypes.INT));
	binder.addFormatter("stopGain", (Formatter<BigDecimal>) FormatterFactory.getFormatter(FormatterTypes.BIGDECIMAL));
	binder.addFormatter("price", (Formatter<BigDecimal>) FormatterFactory.getFormatter(FormatterTypes.BIGDECIMAL));
	binder.addFormatter("startPrice", (Formatter<BigDecimal>) FormatterFactory.getFormatter(FormatterTypes.BIGDECIMAL));
	binder.addFormatter("stopPrice", (Formatter<BigDecimal>) FormatterFactory.getFormatter(FormatterTypes.BIGDECIMAL));
	binder.addFormatter("stopGain", (Formatter<BigDecimal>) FormatterFactory.getFormatter(FormatterTypes.BIGDECIMAL));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancel = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelDetails = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ftxtStop = new javax.swing.JFormattedTextField();
        ftxtPrice = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        ftxtStart = new javax.swing.JFormattedTextField();
        ftxtGain = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtAvgPrice = new javax.swing.JTextField();
        jPanelEvents = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEvents = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        cmbEvntEvent = new javax.swing.JComboBox();
        txtEvntMessage = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btnEvntSave = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        btnEvntRemove = new javax.swing.JButton();
        dtDuration = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        cmbSide = new javax.swing.JComboBox();
        cmbOrderType = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtSerial = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbOrderStatus = new javax.swing.JComboBox();
        ftxtVolume = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        sbSecurity = new com.cmm.searchbox.SearchBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setResizable(false);

        btnCancel.setText("Cancel");

        btnOK.setText("OK");

        jLabel12.setText("Price");

        jLabel8.setText("Stop Price");

        ftxtStop.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        ftxtPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        jLabel9.setText("Start Price");

        ftxtStart.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        ftxtGain.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        jLabel11.setText("Stop Gain");

        jLabel10.setText("Average Price");

        txtAvgPrice.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ftxtPrice)
                    .addComponent(ftxtStop, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 87, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAvgPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ftxtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ftxtGain, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(ftxtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(ftxtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ftxtGain, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(ftxtStop, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtAvgPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelDetailsLayout = new javax.swing.GroupLayout(jPanelDetails);
        jPanelDetails.setLayout(jPanelDetailsLayout);
        jPanelDetailsLayout.setHorizontalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDetailsLayout.setVerticalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 93, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Details", jPanelDetails);

        tblEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Event", "Message", "Date Time"
            }
        ));
        jScrollPane1.setViewportView(tblEvents);

        jLabel19.setText("Event");

        cmbEvntEvent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setText("Message");

        btnEvntSave.setText("Save");

        jLabel17.setText("Date Time");

        btnEvntRemove.setText("Remove");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEvntMessage)
                    .addComponent(cmbEvntEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnEvntSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEvntRemove))
                    .addComponent(dtDuration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtEvntMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbEvntEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEvntRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEvntSave, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelEventsLayout = new javax.swing.GroupLayout(jPanelEvents);
        jPanelEvents.setLayout(jPanelEventsLayout);
        jPanelEventsLayout.setHorizontalGroup(
            jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelEventsLayout.setVerticalGroup(
            jPanelEventsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEventsLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane.addTab("Events", jPanelEvents);

        cmbSide.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbOrderType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Serial");

        jLabel5.setText("Security");

        jLabel13.setText("Order Status");

        cmbOrderStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ftxtVolume.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLabel4.setText("Order Type");

        jLabel3.setText("Side");

        jLabel2.setText("Duration");

        jLabel1.setText("Volume");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ftxtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sbSecurity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(205, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel3)
                        .addComponent(jLabel1)
                        .addComponent(jLabel6))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSerial, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addComponent(cmbSide, 0, 100, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(jLabel2)
                        .addComponent(jLabel13))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cmbOrderType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(sbSecurity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ftxtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13)
                                .addComponent(cmbOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cmbOrderType, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(cmbSide, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnOK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOK))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEvntRemove;
    private javax.swing.JButton btnEvntSave;
    private javax.swing.JButton btnOK;
    private javax.swing.JComboBox cmbEvntEvent;
    private javax.swing.JComboBox cmbOrderStatus;
    private javax.swing.JComboBox cmbOrderType;
    private javax.swing.JComboBox cmbSide;
    private com.toedter.calendar.JDateChooser dtDuration;
    private javax.swing.JFormattedTextField ftxtGain;
    private javax.swing.JFormattedTextField ftxtPrice;
    private javax.swing.JFormattedTextField ftxtStart;
    private javax.swing.JFormattedTextField ftxtStop;
    private javax.swing.JFormattedTextField ftxtVolume;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelDetails;
    private javax.swing.JPanel jPanelEvents;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private com.cmm.searchbox.SearchBox sbSecurity;
    private javax.swing.JTable tblEvents;
    private javax.swing.JTextField txtAvgPrice;
    private javax.swing.JTextField txtEvntMessage;
    private javax.swing.JTextField txtSerial;
    // End of variables declaration//GEN-END:variables



    /* (non-Javadoc)
     * @see com.cmm.jft_ui.AbstractForm#addListeners()
     */
    @Override
    public void addListeners() {
	super.addListeners();
	this.addWindowListener(new GerEvents(this));
	btnEvntRemove.addActionListener(new GerEvents(this));
	btnEvntSave.addActionListener(new GerEvents(this));
	cmbOrderType.addItemListener(new GerEvents(this));
	cmbOrderStatus.addItemListener(new GerEvents(this));
	
    }

    /* (non-Javadoc)
     * @see com.cmm.jft_ui.forms.AbstractForm#loadData()
     */
    @Override
    public void loadData() {	

	FormUtils.getInstance().addItensToCombo(cmbEvntEvent, MarketEvents.values());
	FormUtils.getInstance().addItensToCombo(cmbOrderStatus, OrderStatus.values());
	FormUtils.getInstance().addItensToCombo(cmbOrderType, OrderTypes.values());
	FormUtils.getInstance().addItensToCombo(cmbSide, Side.values());

	if(this.data == null) {
	    data = new Orders();
	    Security s = null;
	    try {

		btnEvntRemove.setEnabled(false);
		btnEvntSave.setEnabled(false);

		TradeTypes tt = (TradeTypes) FormUtils.getInstance().getParameter("TradeType");
		String symbol = (String) FormUtils.getInstance().getParameter("Symbol");

		Object ob = FormUtils.getInstance().getParameter("Side");
		ob = ob==null?"BUY":ob;
		Side side = Side.valueOf(((String) ob).toUpperCase());

		ob = FormUtils.getInstance().getParameter("Volume");
		ob = ob==null?0:ob;
		int volume = (int) ob;

		if(tt!=null && symbol !=null) {
		    HashMap<String, Object> params = new HashMap<String, Object>();
		    params.put("symbol", symbol);

//		    List l = DBFacade.getInstance().queryNamed("Security.findBySymbol", params);
//		    if(l!= null && l.size()>0) {
//			s = (Security) l.get(0);
//			data = new Orders(volume, new Date(), side, OrderTypes.LIMIT, t, s);
//		    }

		}

//	    } catch (DataBaseException e) {
//		e.printStackTrace();
	    } catch(NullPointerException e) {
		e.printStackTrace();
	    }

	    FormUtils.getInstance().clearParameters();

	}

	//ajusta o model ta tabela de eventos
	GenericTableModel<OrderEvent> evntModel = new GenericTableModel<OrderEvent>(OrderEvent.class);
	evntModel.showColumnWithAlias("eventType", "Event");
	evntModel.showColumnWithAlias("message", "Message");
	evntModel.showColumnWithAlias("eventDateTime", "Date Time");
	evntModel.rebuildColumns();
	evntModel.setValues(((Orders)data).getEventsList());
	tblEvents.setModel(evntModel);

	super.loadData();

    }



    private class GerEvents extends Events{

	/**
	 * @param frame
	 */
	public GerEvents(AbstractForm frame) {
	    super(frame);
	}

	/* (non-Javadoc)
	 * @see com.cmm.jft_ui.forms.Events#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

	    //	    super.actionPerformed(e);
	    //	    frame.
	    //((JFrame)frame). actionPerformed(e);
	    if(e.getSource() == btnEvntRemove) {
		((GenericTableModel<OrderEvent>)tblEvents.getModel()).remove(tblEvents.getSelectedRow());
	    }
	    else if(e.getSource() == btnEvntSave) {

//		try {
//		    Date dt = dtEvntDateTime.getDate();
//		    String message = txtEvntMessage.getText();
//		    MarketEvents evnt = (MarketEvents) cmbEvntEvent.getSelectedItem();
//		    data = DBFacade.getInstance().attachToSession(data, ((Orders)data).getOrderID());
//		    OrderEvent oe = new OrderEvent(evnt, message, dt, (Orders)data).add();
//		    ((Orders)data).getEventsList().add(oe);
//
//		    ((GenericTableModel<OrderEvent>)tblEvents.getModel()).addValue(oe);
//		} catch(DataBaseException ex) {
//		    Logging.getInstance().log(OrderForm.class, ex, Level.ERROR);
//		} catch(Exception ex) {
//		    Logging.getInstance().log(OrderForm.class, ex, Level.ERROR);
//		}

	    }
	    
	}

	/* (non-Javadoc)
	 * @see com.cmm.jft_ui.forms.Events#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
	    if(e.getStateChange() == e.SELECTED) {

		if(e.getSource() == cmbOrderType) {
		    OrderTypes ot = OrderTypes.getByValue(e.getItem().toString().charAt(0));
		    if(ot==null) {
			ftxtGain.setEnabled(true);
			ftxtPrice.setEnabled(true);
			ftxtStart.setEnabled(true);
			ftxtStop.setEnabled(true);
		    }
		    else {
			switch(ot) {
			case Limit:
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(true);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(false);
			    break;
			case Market:
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(false);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(false);
			    break;
			case Stop:
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(false);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(true);
			    break;
			case StopLimit:
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(true);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(true);
			    break;
			}
		    }
		}


		if(e.getSource() == cmbOrderStatus) {
		    OrderStatus os = OrderStatus.getByValue(e.getItem().toString().charAt(0));
		    if(os!=null) {
			switch(os) {
			case CANCELED:
			    cmbOrderType.setEnabled(false);
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(false);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(false);
			    break;
			    
			case CREATED:
			    cmbOrderType.setEnabled(false);
			    break;
			    
			case EXPIRED:
			    cmbOrderType.setEnabled(false);
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(false);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(false);
			    break;
			    
			case FILLED:
			    cmbOrderType.setEnabled(false);
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(false);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(false);
			    break;
			    
			case PARTIALLY_FILLED:
			    cmbOrderType.setEnabled(false);
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(false);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(false);
			    break;
			    
			case REJECTED:
			    cmbOrderType.setEnabled(false);
			    ftxtGain.setEnabled(false);
			    ftxtPrice.setEnabled(false);
			    ftxtStart.setEnabled(false);
			    ftxtStop.setEnabled(false);
			    break;
			
			case REPLACED:
			    cmbOrderType.setEnabled(false);
			    break;
			    
			case SUBMITTED:
			    cmbOrderType.setEnabled(false);
			    break;
			    
			default:
			    break;
			}
		    }
		}
		

	    }
	}

    }

}
