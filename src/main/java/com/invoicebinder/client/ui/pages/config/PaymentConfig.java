/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.config;

import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.config.PaymentConfigItems;
import com.invoicebinder.shared.model.ConfigData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mon2
 */
public class PaymentConfig extends Composite {
    
    private static final PaymentConfigUiBinder uiBinder = GWT.create(PaymentConfigUiBinder.class);
    @UiField HTMLPanel paymentConfigPanel;
    
    private final TextBox txtPaypalEmail;
    private final CheckBox chkPayOnlineInEmail;
    private final HorizontalPanel buttonPanel;
    private final Button btnSave;
    private final Label lblSaveResult;
    private final Main main;
    private final ConfigServiceClientImpl configservice;

    void updateStatus(Boolean status) {
        lblSaveResult.setStyleName("message-box");
        lblSaveResult.setWidth("190px");
        lblSaveResult.setVisible(true);
        
        if (status) {
            lblSaveResult.addStyleName("success");
            lblSaveResult.setText("Payment configuration settings have been saved.");
        }
        else {
            lblSaveResult.addStyleName("error");
            lblSaveResult.setText("Error saving payment configuration settings.");
        }
    }
    
    interface PaymentConfigUiBinder extends UiBinder<Widget, PaymentConfig> {
    }
    
    public PaymentConfig(Object main) {
        this.main = (Main)main;
        this.configservice = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        txtPaypalEmail = new TextBox();
        chkPayOnlineInEmail = new CheckBox();
        lblSaveResult = new Label();
        lblSaveResult.setVisible(false);
        paymentConfigPanel.add(getPaymentConfigTable());
                
        buttonPanel = new HorizontalPanel();
        
        //buttons
        btnSave = new Button();
        btnSave.setStyleName("appbutton-default");
        btnSave.setText("Save");
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSave);
        buttonPanel.add(lblSaveResult);
        paymentConfigPanel.add(buttonPanel);
        btnSave.addClickHandler(new DefaultClickHandler(this));
        configservice.loadConfigData(ConfigurationSection.Payments);
    }
       
    public void setPaymentConfigData(ArrayList<ConfigData> arrayList) {
        HashMap<String, String> dataMap = new HashMap();
        ConfigData data;
        for (int i=0; i<arrayList.size(); i++) {
            data = arrayList.get(i);
            dataMap.put(data.getKey(), data.getValue());
        }
        
        txtPaypalEmail.setText(dataMap.get(PaymentConfigItems.PAYPALEMAIL.toString()));
        chkPayOnlineInEmail.setValue(Boolean.valueOf(dataMap.get(PaymentConfigItems.ALLOWONLINEPAYMENT.toString())));
    }
    
    private FlexTable getPaymentConfigTable() {
        FlexTable table = new FlexTable();
        Label lblHeader = new Label();
        lblHeader.getElement().setInnerHTML("<h6><span>Payment Details</span></h6>");
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);          
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setWidget(0, 0, lblHeader);
        //attributes
        table.setHTML(1, 0, "Paypal Email:");
        table.setWidget(1, 1, txtPaypalEmail);    
        table.setHTML(2, 0, "Send Online Payment Link in Invoice Emails:");
        table.setWidget(2, 1, chkPayOnlineInEmail);    
        return table;
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final PaymentConfig pageReference;
        
        public DefaultClickHandler(PaymentConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                this.pageReference.saveConfig();
                event.getNativeEvent().preventDefault();
            }
        }
    }
    
    private void saveConfig() {
        ArrayList<ConfigData> lstData = new ArrayList<>();
        ConfigData data;
        
        //1. Paypal Email
        data = new ConfigData(PaymentConfigItems.PAYPALEMAIL.toString(), ConfigurationSection.Payments.toString(), txtPaypalEmail.getText());
        lstData.add(data);
        //1. Online Payment Flag
        data = new ConfigData(PaymentConfigItems.ALLOWONLINEPAYMENT.toString(), ConfigurationSection.Payments.toString(), chkPayOnlineInEmail.getValue().toString());
        lstData.add(data);        
        configservice.saveConfigData(lstData, ConfigurationSection.Payments);
    }
}
