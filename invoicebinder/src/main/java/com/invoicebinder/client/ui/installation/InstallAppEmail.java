/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.installation;

import com.invoicebinder.client.service.installation.InstallServiceClientImpl;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.enums.ServiceResponseStatus;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.invoicebinder.shared.model.ServerValidationResult;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class InstallAppEmail extends Composite {
    private final Main main;
    private final InstallServiceClientImpl installService;
    private static final String TESTEMAIL_BTN_LABEL = "Send Test Email";
    private static final String NEXT_BTN_LABEL = "Next";
    private final Loading loading;
    @UiField TextBox emailHost;
    @UiField IntegerBox emailPort;
    @UiField TextBox emailUsername;
    @UiField PasswordTextBox emailPassword;
    @UiField TextBox emailFromAddress;
    @UiField TextBox emailContactAddress;
    @UiField TextBox emailAuthType;
    @UiField CheckBox chkUseSSLForEmail;
    @UiField Label title;
    @UiField Image logoImage;
    @UiField Button nextBtn;
    @UiField Label lblInstallMessages;
    
    private static final InstallAppConfigUiBinder uiBinder = GWT.create(InstallAppConfigUiBinder.class);
    
    interface InstallAppConfigUiBinder extends UiBinder<Widget, InstallAppEmail> {
    }
    
    public InstallAppEmail(Main main) {
        this.main = main;
        this.installService = new InstallServiceClientImpl(GWT.getModuleBaseURL() + "services/install", this.main);
        this.loading = new Loading(); 
        initWidget(uiBinder.createAndBindUi(this));
        logoImage.setUrl("images/invoicebindersmall.png");
        title.setText(Constants.TITLE);
        title.setStyleName("installpage-producttitle");
        emailHost.setText("smtp.gmail.com");
        emailPort.setText("465");
        emailAuthType.setText("SMTP");
        nextBtn.addClickHandler(new DefaultClickHandler(this));
        nextBtn.setFocus(true);
        setFields();
    }
    
    private void setFields() {
        emailHost.getElement().setId("emailHost");
        emailPort.getElement().setId("emailPort");
        emailUsername.getElement().setId("emailUsername");
        emailPassword.getElement().setId("emailPassword");
        emailFromAddress.getElement().setId("emailFromAddress");
        emailContactAddress.getElement().setId("emailContactAddress");
        emailAuthType.getElement().setId("emailAuthType");
    }       
    
    public void updateValidationStatus(ValidationResult result) {
        Element element;
        
        element = DOM.getElementById(result.getTagname());
        element.focus();
        ValidationPopup.Show(result.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }
    
    public void updateEmailTestResult(ServerValidationResult result) {
        this.loading.hide();
        this.lblInstallMessages.setText(result.getMessage());
        
        if (result.getStatus() == ServiceResponseStatus.SUCCESS) {
            nextBtn.setText(NEXT_BTN_LABEL);
            this.lblInstallMessages.setStyleName("message-successvisible");
            this.setFieldsReadOnly();
        }
        else {
            this.lblInstallMessages.setStyleName("message-errorvisible");
        }
        Window.scrollTo(0, 0);
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final InstallAppEmail pageReference;
        
        public DefaultClickHandler(InstallAppEmail reference) {
            this.pageReference = reference;
        }
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == nextBtn) {
                if (TESTEMAIL_BTN_LABEL.equals(nextBtn.getText())){
                    EmailPropertiesInfo info = new EmailPropertiesInfo();
                    
                    info.setEmailHost(emailHost.getText());
                    info.setEmailUsername(emailUsername.getText());
                    info.setEmailPassword(emailPassword.getText());
                    info.setEmailPort(emailPort.getText());
                    info.setEmailAuthType(emailAuthType.getText());
                    info.setEmailContactUsAddress(emailContactAddress.getText());
                    info.setEmailFromAddress(emailFromAddress.getText());
                    info.setEmailSSL(chkUseSSLForEmail.getValue());
                    
                    installService.testEmailConnection(info, loading);
                }
                
                if (NEXT_BTN_LABEL.equals(nextBtn.getText())) {
                    //set the required database connection values
                    this.pageReference.main.getInstallInfo().setEmailPropertiesInfo(new EmailPropertiesInfo(emailHost.getText(), emailPort.getText(), emailUsername.getText(),
                                    emailPassword.getText(), emailFromAddress.getText(), emailContactAddress.getText(), 
                                    emailAuthType.getText(), chkUseSSLForEmail.getValue()));
                    
                    History.newItem(Views.installdbtest.toString());
                    event.getNativeEvent().preventDefault();
                }
            }
        }
    }
    
    private void setFieldsReadOnly() {
        this.emailHost.setReadOnly(true);
        this.emailPassword.setReadOnly(true);
        this.emailUsername.setReadOnly(true);
        this.emailPort.setReadOnly(true);
        this.emailFromAddress.setReadOnly(true);
        this.emailContactAddress.setReadOnly(true);
        this.emailAuthType.setReadOnly(true);
        this.chkUseSSLForEmail.setEnabled(false);
    }
}
