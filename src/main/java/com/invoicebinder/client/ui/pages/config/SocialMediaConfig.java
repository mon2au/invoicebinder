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
import com.invoicebinder.shared.enums.config.SocialMediaConfigItems;
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mon2
 */
public class SocialMediaConfig extends Composite {
    
    private static final SocialMediaConfigUiBinder uiBinder = GWT.create(SocialMediaConfigUiBinder.class);
    @UiField HTMLPanel socialMediaConfigPanel;
    private final TextBox txtFacebook;
    private final TextBox txtTwitter;
    private final TextBox txtGooglePlus;
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
            lblSaveResult.setText("Social media configuration settings have been saved.");
        }
        else {
            lblSaveResult.addStyleName("error");
            lblSaveResult.setText("Error saving social media configuration settings.");
        }
    }
    
    interface SocialMediaConfigUiBinder extends UiBinder<Widget, SocialMediaConfig> {
    }
    
    public SocialMediaConfig(Object main) {
        this.main = (Main)main;
        this.configservice = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        txtFacebook = new TextBox();
        txtTwitter = new TextBox();
        txtGooglePlus = new TextBox();
        lblSaveResult = new Label();
        lblSaveResult.setVisible(false);
        socialMediaConfigPanel.add(getSocialMediaConfigTable());
        buttonPanel = new HorizontalPanel();
        
        //buttons
        btnSave = new Button();
        btnSave.setStyleName("appbutton-default");
        btnSave.setText("Save");
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSave);
        buttonPanel.add(lblSaveResult);
        socialMediaConfigPanel.add(buttonPanel);
        btnSave.addClickHandler(new DefaultClickHandler(this));
        configservice.loadConfigData(ConfigurationSection.SocialMedia);
        
    }
       
    public void setSocialMediaData(ArrayList<ConfigData> arrayList) {
        HashMap<String, String> socialMediaConfigData = new HashMap();
        ConfigData data;
        for (int i=0; i<arrayList.size(); i++) {
            data = arrayList.get(i);
            socialMediaConfigData.put(data.getKey(), data.getValue());
        }
        
        txtFacebook.setText(socialMediaConfigData.get(SocialMediaConfigItems.FACEBOOKURL.toString()));
        txtTwitter.setText(socialMediaConfigData.get(SocialMediaConfigItems.TWITTERURL.toString()));
        txtGooglePlus.setText(socialMediaConfigData.get(SocialMediaConfigItems.GPLUSURL.toString()));
    }
    
    private FlexTable getSocialMediaConfigTable() {
        FlexTable table = new FlexTable();
        Label lblSocialMediaConfigHeader = new Label();
        lblSocialMediaConfigHeader.getElement().setInnerHTML("<h6><span>Social Media Details</span></h6>");
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);          
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setWidget(0, 0, lblSocialMediaConfigHeader);
        //custom attributes
        table.setHTML(1, 0, "Facebook:");
        table.setWidget(1, 1, txtFacebook);
        table.setHTML(2, 0, "Twitter:");
        table.setWidget(2, 1, txtTwitter);
        table.setHTML(3, 0, "Google Plus:");
        table.setWidget(3, 1, txtGooglePlus);        
        return table;
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final SocialMediaConfig pageReference;
        
        public DefaultClickHandler(SocialMediaConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                this.pageReference.saveAttribConfig();
                event.getNativeEvent().preventDefault();
            }
        }
    }
    
    private void saveAttribConfig() {
        ArrayList<ConfigData> lstData = new ArrayList<ConfigData>();
        ConfigData data;
        
        //1. Facebook
        data = new ConfigData(SocialMediaConfigItems.FACEBOOKURL.toString(), ConfigurationSection.SocialMedia.toString(), txtFacebook.getText());
        lstData.add(data);
        //2. Twitter
        data = new ConfigData(SocialMediaConfigItems.TWITTERURL.toString(), ConfigurationSection.SocialMedia.toString(), txtTwitter.getText());
        lstData.add(data);
        //3. Google Plus
        data = new ConfigData(SocialMediaConfigItems.GPLUSURL.toString(), ConfigurationSection.SocialMedia.toString(), txtGooglePlus.getText());
        lstData.add(data);
        configservice.saveConfigData(lstData, ConfigurationSection.SocialMedia);
    }
}
