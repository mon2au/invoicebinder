/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.installation;

import com.invoicebinder.client.service.installation.InstallServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.InstallationInfo;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class InstallDBCreate extends Composite {
    
    private static final InstallDBCreateUiBinder uiBinder = GWT.create(InstallDBCreateUiBinder.class);
    private final InstallServiceClientImpl installService;
    private final TerminalPanel terminal;
    private final Main main;
    
    @UiField HTMLPanel consolePanel;   
    @UiField HTMLPanel progressPanel;
    @UiField Label title;
    @UiField Image logoImage; 
    @UiField TextBox firstName;
    @UiField TextBox lastName;
    @UiField TextBox userName;
    @UiField TextBox emailAddress;
    @UiField PasswordTextBox password;
    @UiField PasswordTextBox confirmPassword;
    @UiField Label lblInstallMessages;  
    @UiField Button btnSubmit;
    @UiField Button btnLogin; 
   
    interface InstallDBCreateUiBinder extends UiBinder<Widget, InstallDBCreate> {
    }

    public InstallDBCreate(Main main) {
        this.main = main;
        this.installService = new InstallServiceClientImpl(GWT.getModuleBaseURL() + "services/install", this.main);        
        initWidget(uiBinder.createAndBindUi(this));
        logoImage.setUrl("images/invoicebindersmall.png");
        title.setText(Constants.TITLE);
        title.setStyleName("installpage-producttitle");  
        btnSubmit.setText("Install");
        btnSubmit.addClickHandler(new DefaultClickHandler(this));  
        btnLogin.setText("Login");
        btnLogin.addClickHandler(new DefaultClickHandler(this));
        btnLogin.setVisible(false);
        this.terminal = new TerminalPanel();
        progressPanel.setVisible(false);
        setConsolePanel();
        setFields();
    }
    
    public void updateValidationStatus(ValidationResult result) {
        Element element;
        
        element = DOM.getElementById(result.getTagname());
        element.focus();
        ValidationPopup.Show(result.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }

    public void updateDBCreateResult(Boolean result) {
        if (result) {
            logMessage("database setup completed.");
            doCreateDefaultData();
        }
        else {
            logMessage("unable to setup database.");
            showSuccessOrFailMessage(false);
        }        
    }  
    
    public void updateDefaultDataCreateResult(Boolean result) {
        if (result) {
            logMessage("default data created.");
            doCreateDatabaseProcedures();
        }
        else {
            logMessage("unable to create default data.");
            showSuccessOrFailMessage(false);
        }  
    } 
    
    public void updateDBCreateProcedureResult(Boolean result) {
        if (result) {
            logMessage("database procedures created.");
            doCreateApplicationConfiguration();
        }
        else {
            logMessage("unable to create database procedures.");
            showSuccessOrFailMessage(false);
        }  
    }

    public void updateDBCreateConfigResult(Boolean result) {
        if (result) {
            logMessage("application configuration created.");
            logMessage("application context reloaded.");            
            logMessage("application installation successful.");
            showSuccessOrFailMessage(result);
            this.progressPanel.setVisible(false);  
            this.btnSubmit.setEnabled(false);
            this.btnSubmit.setStyleName("appbutton-default-disabled");
            this.btnLogin.setVisible(true); 
        }
        else {
            logMessage("error creating application configuration.");
            showSuccessOrFailMessage(false);
        }        

        
    }    

    // <editor-fold defaultstate="collapsed" desc="Event Handlers">  
    private class DefaultClickHandler implements ClickHandler {
        private final InstallDBCreate pageReference;
        private InstallationInfo info;
        
        public DefaultClickHandler(InstallDBCreate reference) {
            this.pageReference = reference;
        }
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSubmit) {
                this.pageReference.install();
            }
            else if (sender ==btnLogin) {             
                History.newItem(Views.login.toString());
                event.getNativeEvent().preventDefault(); 
            }
        }
    } 
    // </editor-fold>      
       
    // <editor-fold defaultstate="collapsed" desc="Install Process">  
    // Install Process Order.
    // 1. doCreateDatabase
    // 2. doCreateDefaultData
    // 3. doCreateDatabaseProcedures
    // 4. doCreateDefaultData
    // 5. doCreateApplication
    // 6. doCreateApplicationConfiguration
    
    private void install() {
        this.main.getInstallInfo().setFirstName(userName.getText());
        this.main.getInstallInfo().setLastName(password.getText());
        this.main.getInstallInfo().setUserName(userName.getText());
        this.main.getInstallInfo().setPassword(password.getText());
        this.main.getInstallInfo().setConfirmPassword(confirmPassword.getText());
        this.main.getInstallInfo().setEmailAddress(emailAddress.getText());
        
        if (this.installService.validateInstall(this.main.getInstallInfo())) {
            logMessage("Starting installation process.");
            this.progressPanel.setVisible(true);
            doInstallationProcess();
        }
    }
    private void doInstallationProcess(){
        setInstallationInProgessMessage();
        doStart();
    }
    private void doStart() {
        doCreateDatabase();
    }
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Install Actions">   
    private void doCreateDatabase() {
        installService.createDatabase(this.main.getInstallInfo().getDatabaseInfo());
    }
    private void doCreateDefaultData() {
        installService.createDefaultData(this.main.getInstallInfo().getDatabaseInfo());
    }
    private void doCreateDatabaseProcedures() {
        installService.createDatabaseProcedures(this.main.getInstallInfo().getDatabaseInfo());
    }    
    private void doCreateApplicationConfiguration() {        
        this.installService.createConfigAndFinalizeInstallation(this.main.getInstallInfo());
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="User Interface">    
    private void setConsolePanel() {
        consolePanel.clear();
        consolePanel.setStyleName("terminalPanel");
        consolePanel.add(terminal);
    }
    
    private void setFields() {
        firstName.getElement().setId("firstName");
        lastName.getElement().setId("lastName");
        userName.getElement().setId("userName");
        password.getElement().setId("password");
        emailAddress.getElement().setId("emailAddress");
        confirmPassword.getElement().setId("confirmPassword");
    }    
    
    private void setInstallationInProgessMessage() {
        lblInstallMessages.setText("Please wait while installing Invoice Binder. This process may take a while.");
    }
    private void showSuccessOrFailMessage(boolean isSuccess) {
        if (isSuccess) {
            this.lblInstallMessages.setStyleName("message-successvisible");
            this.lblInstallMessages.setText("Your application has been installed successfully. " +
                    "Please click the login button to " + 
                    "redirect to login page.");
        }
        else {
            this.lblInstallMessages.setStyleName("message-errorvisible");
            this.lblInstallMessages.setText("An error has occurred during the installation process. Please contact us for further assistance.");
        }
        Window.scrollTo(0, 0);        
    }
    private void logMessage(String message) {
        this.terminal.addMessage(message);
    }    
    // </editor-fold>        
}
