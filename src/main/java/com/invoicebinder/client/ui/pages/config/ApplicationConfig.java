/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.config;

import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.service.suggestion.CurrencySuggestOracle;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.config.ApplicationSettingsItems;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.suggestion.CurrencySuggestion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author msushil
 */
public class ApplicationConfig extends Composite {
    
    private static final ApplicationConfigUiBinder uiBinder = GWT.create(ApplicationConfigUiBinder.class);
    private final Main main;
    private final ConfigServiceClientImpl configService;
    private final FlexTable tblApplicationConfig;
    private final HorizontalPanel mainPanel;  
    private final HorizontalPanel buttonPanel;    
    private TextBox txtAppTitle;
    private TextBox txtAppSlogan;
    private TextBox txtTaxLabel;
    private final Label lblSaveResult;
    private final Button btnSave;
    private CurrencySuggestOracle oracle;
    private SuggestBox suggestBox;
    private CurrencySuggestion suggestion;    
    
    @UiField HTMLPanel applicationConfigPanel;
    
    
    ApplicationConfig(Main main) {
        this.main = (Main)main;
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        mainPanel = new HorizontalPanel();
        buttonPanel = new HorizontalPanel();   
        lblSaveResult = new Label();
        lblSaveResult.setVisible(false);
        this.tblApplicationConfig = getApplicationConfigTable();
        mainPanel.setWidth("100%");
        mainPanel.add(this.tblApplicationConfig);
        applicationConfigPanel.add(mainPanel);    
        
        //buttons
        btnSave = new Button();
        btnSave.setStyleName("appbutton-default");
        btnSave.setText("Save");
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSave);
        buttonPanel.add(lblSaveResult);
        applicationConfigPanel.add(buttonPanel);
        btnSave.addClickHandler(new DefaultClickHandler(this)); 
        
        configService.loadConfigData(ConfigurationSection.ApplicationSettings);
    }

    void setAppSettingsData(ArrayList<ConfigData> arrayList) {
        HashMap<String, String> appSettingsData = new HashMap();
        for (ConfigData data : arrayList) {
            appSettingsData.put(data.getKey(), data.getValue());
        }
        
        txtAppTitle.setText(appSettingsData.get(ApplicationSettingsItems.APPTITLE.toString()));
        txtAppSlogan.setText(appSettingsData.get(ApplicationSettingsItems.APPSLOGAN.toString()));
        suggestBox.setText(appSettingsData.get(ApplicationSettingsItems.CURRENCY.toString()));
        txtTaxLabel.setText(appSettingsData.get(ApplicationSettingsItems.TAXLABEL.toString()));
    }

    void updateStatus(Boolean status) {
        lblSaveResult.setStyleName("message-box");
        lblSaveResult.setWidth("190px");
        lblSaveResult.setVisible(true);
        
        if (status) {
            lblSaveResult.addStyleName("success");
            lblSaveResult.setText("Application configuration settings have been saved.");
        }
        else {
            lblSaveResult.addStyleName("error");
            lblSaveResult.setText("Error saving application configuration settings.");
        }
    }
        
    interface ApplicationConfigUiBinder extends UiBinder<Widget, ApplicationConfig> {
    }
    
    private FlexTable getApplicationConfigTable() {
        FlexTable table = new FlexTable();
        VerticalPanel suggestPanel = new VerticalPanel();
        Label lblApplicationConfigHeader = new Label();
        
        txtAppTitle = new TextBox();
        txtAppSlogan = new TextBox();
        txtTaxLabel = new TextBox();
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        
        oracle = new CurrencySuggestOracle();
        suggestBox = new SuggestBox(oracle);
        suggestBox.addSelectionHandler(new CurrencySelectionHandler());
        suggestion = null;
        suggestPanel.add(suggestBox);        
        
        lblApplicationConfigHeader.getElement().setInnerHTML("<h6><span>Application Details</span></h6>");
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);
        table.setWidget(0, 0, lblApplicationConfigHeader);
        //business settings  information
        table.setHTML(1, 0, "Application Title:");
        table.setWidget(1, 1, txtAppTitle);
        table.setHTML(2, 0, "Slogan:");
        table.setWidget(2, 1, txtAppSlogan);
        table.setHTML(3, 0, "Currency:");
        table.setWidget(3, 1, suggestPanel);               
        table.setHTML(4, 0, "Tax Label:");
        table.setWidget(4, 1, txtTaxLabel);  
        return table;
    }
    
    private void saveAppConfig() {
        ArrayList<ConfigData> lstData = new ArrayList<>();
        ConfigData data;
        
        //Save app data.
        //1. App Name
        data = new ConfigData(ApplicationSettingsItems.APPTITLE.toString(), ConfigurationSection.ApplicationSettings.toString(), txtAppTitle.getText());
        lstData.add(data);
        //2. App Slogan
        data = new ConfigData(ApplicationSettingsItems.APPSLOGAN.toString(), ConfigurationSection.ApplicationSettings.toString(), txtAppSlogan.getText());
        lstData.add(data);
        //3. Currency
        if (suggestion != null) {
            data = new ConfigData(ApplicationSettingsItems.CURRENCY.toString(), ConfigurationSection.ApplicationSettings.toString(), suggestion.getCode());
            lstData.add(data);
        }
        //4. Tax Label
        data = new ConfigData(ApplicationSettingsItems.TAXLABEL.toString(), ConfigurationSection.ApplicationSettings.toString(), txtTaxLabel.getText());
        lstData.add(data);
        
        //Default configuration.
        data = new ConfigData(ApplicationSettingsItems.APPLANDINGPAGE.toString(), ConfigurationSection.ApplicationSettings.toString(), "LOGINPAGE");
        lstData.add(data);               
        
        configService.saveConfigData(lstData, ConfigurationSection.ApplicationSettings);
    }    
    
    private class DefaultClickHandler implements ClickHandler {
        private final ApplicationConfig pageReference;
        
        public DefaultClickHandler(ApplicationConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                if (isDemoApplication()) return;         
                this.pageReference.saveAppConfig();
                event.getNativeEvent().preventDefault();
            }
        }
    } 
    
    private class CurrencySelectionHandler implements SelectionHandler {
        
        @Override
        public void onSelection(SelectionEvent event) {
            suggestion = ((CurrencySuggestion) event.getSelectedItem());
        }
    }    
}
