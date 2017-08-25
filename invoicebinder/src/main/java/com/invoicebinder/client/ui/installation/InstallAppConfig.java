/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.installation;

import com.invoicebinder.client.service.installation.InstallServiceClientImpl;
import com.invoicebinder.client.service.suggestion.CurrencySuggestOracle;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.ServerValidationResult;
import com.invoicebinder.shared.model.ValidationResult;
import com.invoicebinder.shared.suggestion.CurrencySuggestion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.invoicebinder.shared.enums.ServiceResponseStatus;

/**
 *
 * @author mon2
 */
public class InstallAppConfig extends Composite {
    
    private static final InstallAppConfigUiBinder uiBinder = GWT.create(InstallAppConfigUiBinder.class);
    private final Main main;
    private final InstallServiceClientImpl installService;
    private final CurrencySuggestOracle oracle;
    @UiField VerticalPanel suggestPanel;
    private final SuggestBox suggestCurrencyCode;
    private CurrencySuggestion suggestion;       
    @UiField TextBox txtHtmlToPdfAppLocation;
    @UiField TextBox txtBusinessName;
    @UiField TextBox txtBusinessNumber;
    @UiField TextBox txtBusinessNumberLabel;        
    @UiField Label title;
    @UiField Image logoImage;
    @UiField Button nextBtn;
    @UiField Label lblInstallMessages;       

    public void setWKHTMLtoPDFLocation(String result) {
        this.txtHtmlToPdfAppLocation.setText(result);
    }
    
    
    interface InstallAppConfigUiBinder extends UiBinder<Widget, InstallAppConfig> {
    }
    
    public void updateStatusResult(ServerValidationResult result) {
        if (result.getStatus() == ServiceResponseStatus.SUCCESS) {
            History.newItem(Views.installdbcreate.toString());
        }
        else {
            
        }
    }
    
    public void updateValidationStatus(ValidationResult result) {
        Element element;          
        
        element = DOM.getElementById(result.getTagname());
        element.focus();
        ValidationPopup.Show(result.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }    
    
    public InstallAppConfig(Main main) {
        this.main = main;
        this.installService = new InstallServiceClientImpl(GWT.getModuleBaseURL() + "services/install", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        logoImage.setUrl("images/invoicebindersmall.png");
        title.setText(Constants.TITLE);
        title.setStyleName("installpage-producttitle");
        nextBtn.addClickHandler(new DefaultClickHandler(this));
        nextBtn.setFocus(true);
        oracle = new CurrencySuggestOracle();
        suggestCurrencyCode = new SuggestBox(oracle);
        suggestCurrencyCode.addSelectionHandler(new CurrencySelectionHandler());
        suggestPanel.add(suggestCurrencyCode);
        suggestion = null;     
        setFields();
        installService.checkWKHTMLtoPDFPath();
    }    
    
    private class DefaultClickHandler implements ClickHandler {
        private final InstallAppConfig pageReference;
        
        public DefaultClickHandler(InstallAppConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == nextBtn) {     
                pageReference.setInstallInfo();
            }
        }
    }   
    
    private boolean setInstallInfo(){
        this.main.getInstallInfo().setBusinessName(txtBusinessName.getText());  
        this.main.getInstallInfo().setCurrCode(suggestCurrencyCode.getText()); 
        this.main.getInstallInfo().setBusinessNumber(txtBusinessNumber.getText()); 
        this.main.getInstallInfo().setBusinessNumberLabel(txtBusinessNumberLabel.getText()); 
        this.main.getInstallInfo().setHtmlToPdfAppLocation(txtHtmlToPdfAppLocation.getText()); 

        return installService.validateInstallAppConfig(this.main.getInstallInfo());
    }
    
    private void setFields() {
        suggestCurrencyCode.getElement().setId("suggestCurrencyCode");
        txtBusinessNumberLabel.getElement().setId("txtBusinessNumberLabel");
        txtBusinessNumber.getElement().setId("txtBusinessNumber");
        txtHtmlToPdfAppLocation.getElement().setId("txtHtmlToPdfAppLocation");
        txtBusinessName.getElement().setId("txtBusinessName");
    }   
    
    private void checkWKHTMLtoPDFPath() {
        String[] wkhtmlPaths = {"",""}; 
        
        
        
        
        
    }
    
    private class CurrencySelectionHandler implements SelectionHandler {
        
        @Override
        public void onSelection(SelectionEvent event) {
            suggestion = ((CurrencySuggestion) event.getSelectedItem());
        }
    }       
}
