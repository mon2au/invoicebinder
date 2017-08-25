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
import com.invoicebinder.shared.enums.config.CustomAttribConfigItems;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.ConfigData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.invoicebinder.shared.misc.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mon2
 */
public class CustomAttribConfig extends Composite {
    
    private static final CustomAttribConfigUiBinder uiBinder = GWT.create(CustomAttribConfigUiBinder.class);
    @UiField HTMLPanel attribConfigPanel;
    private final TextBox txtAttrib1;
    private final TextBox txtAttrib2;
    private final TextBox txtAttrib3;
    private final TextBox txtAttrib4;
    private final TextBox txtAttrib5;
    private final TextBox txtAttrib6;
    private final TextBox txtAttrib7;
    private final TextBox txtAttrib8;
    private final TextBox txtAttrib9;
    private final TextBox txtAttrib10;    
    private final HorizontalPanel buttonPanel;
    private final Button btnSave;
    private final Main main;
    private final Label lblSaveResult;
    private final ConfigServiceClientImpl configService;

    void updateStatus(Boolean status) {
        lblSaveResult.setStyleName("message-box");
        lblSaveResult.setWidth("190px");
        lblSaveResult.setVisible(true);
        
        if (status) {
            lblSaveResult.addStyleName("success");
            lblSaveResult.setText("Custom attributes configuration settings have been saved.");
        }
        else {
            lblSaveResult.addStyleName("error");
            lblSaveResult.setText("Error saving custom attributes configuration settings.");
        }
    }
    
    interface CustomAttribConfigUiBinder extends UiBinder<Widget, CustomAttribConfig> {
    }
    
    public CustomAttribConfig(Object main) {
        this.main = (Main)main;
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        initWidget(uiBinder.createAndBindUi(this));      
        txtAttrib1 = new TextBox();
        txtAttrib2 = new TextBox();
        txtAttrib3 = new TextBox();
        txtAttrib4 = new TextBox();
        txtAttrib5 = new TextBox();
        txtAttrib6 = new TextBox();
        txtAttrib7 = new TextBox();
        txtAttrib8 = new TextBox();
        txtAttrib9 = new TextBox();
        txtAttrib10 = new TextBox();   
        attribConfigPanel.add(getCustomAttrConfigTable());
        buttonPanel = new HorizontalPanel();
        lblSaveResult = new Label();
        lblSaveResult.setVisible(false);
        
        //buttons
        btnSave = new Button();
        btnSave.setStyleName("appbutton-default");
        btnSave.setText("Save");    
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSave);
        buttonPanel.add(lblSaveResult);
        attribConfigPanel.add(buttonPanel);
        btnSave.addClickHandler(new DefaultClickHandler(this));
        configService.loadConfigData(ConfigurationSection.CustomAttributes);
    }
    
    public void setCustomAttrData(ArrayList<ConfigData> arrayList) {
            HashMap<String, String> customAttrConfigData = new HashMap();
            ConfigData data;
            for (int i=0; i<arrayList.size(); i++) {
                data = arrayList.get(i);
                customAttrConfigData.put(data.getKey(), data.getValue());
            }
            
        txtAttrib1.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR1.toString()));
        txtAttrib2.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR2.toString()));
        txtAttrib3.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR3.toString()));
        txtAttrib4.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR4.toString()));        
        txtAttrib5.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR5.toString()));
        txtAttrib6.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR6.toString()));
        txtAttrib7.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR7.toString()));
        txtAttrib8.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR8.toString()));  
        txtAttrib9.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR9.toString()));
        txtAttrib10.setText(customAttrConfigData.get(CustomAttribConfigItems.ATTR10.toString()));                   
    }
    
    private FlexTable getCustomAttrConfigTable() {
        FlexTable table = new FlexTable();
        Label lblCustomAttrConfigHeader = new Label();
        lblCustomAttrConfigHeader.getElement().setInnerHTML("<h6><span>Custom Attribute Details</span></h6>");
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);  
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setWidget(0, 0, lblCustomAttrConfigHeader);
        //custom attributes
        table.setHTML(1, 0, "Attribute 1:");
        table.setWidget(1, 1, txtAttrib1);
        table.setHTML(2, 0, "Attribute 2:");
        table.setWidget(2, 1, txtAttrib2);
        table.setHTML(3, 0, "Attribute 3:");
        table.setWidget(3, 1, txtAttrib3);
        table.setHTML(4, 0, "Attribute 4:");
        table.setWidget(4, 1, txtAttrib4);
        table.setHTML(5, 0, "Attribute 5:");
        table.setWidget(5, 1, txtAttrib5);
        table.setHTML(6, 0, "Attribute 6:");
        table.setWidget(6, 1, txtAttrib6);        
        table.setHTML(7, 0, "Attribute 7:");
        table.setWidget(7, 1, txtAttrib7);        
        table.setHTML(8, 0, "Attribute 8:");
        table.setWidget(8, 1, txtAttrib8);        
        table.setHTML(9, 0, "Attribute 9:");
        table.setWidget(9, 1, txtAttrib9);        
        table.setHTML(10, 0, "Attribute 10:");
        table.setWidget(10, 1, txtAttrib10);        
                
        return table;
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final CustomAttribConfig pageReference;
        
        public DefaultClickHandler(CustomAttribConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                if (Utils.isDemoApplication()) return;
                this.pageReference.saveAttribConfig();
                event.getNativeEvent().preventDefault();
            }
        }
    }
    
    private void saveAttribConfig() {
        ArrayList<ConfigData> lstData = new ArrayList<>();
        ConfigData data;
           
        //Attribs
        data = new ConfigData(CustomAttribConfigItems.ATTR1.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib1.getText());
        lstData.add(data);
        data = new ConfigData(CustomAttribConfigItems.ATTR2.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib2.getText());
        lstData.add(data);       
        data = new ConfigData(CustomAttribConfigItems.ATTR3.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib3.getText());  
        lstData.add(data);  
        data = new ConfigData(CustomAttribConfigItems.ATTR4.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib4.getText());  
        lstData.add(data);  
        data = new ConfigData(CustomAttribConfigItems.ATTR5.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib5.getText());  
        lstData.add(data);  
        data = new ConfigData(CustomAttribConfigItems.ATTR6.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib6.getText());  
        lstData.add(data);  
        data = new ConfigData(CustomAttribConfigItems.ATTR7.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib7.getText());  
        lstData.add(data);  
        data = new ConfigData(CustomAttribConfigItems.ATTR8.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib8.getText());  
        lstData.add(data);  
        data = new ConfigData(CustomAttribConfigItems.ATTR9.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib9.getText());  
        lstData.add(data);  
        data = new ConfigData(CustomAttribConfigItems.ATTR10.toString(), ConfigurationSection.CustomAttributes.toString(), txtAttrib10.getText());  
        lstData.add(data);          

        configService.saveConfigData(lstData, ConfigurationSection.CustomAttributes);
    }    
}
