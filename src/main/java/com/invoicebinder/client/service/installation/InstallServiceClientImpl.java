/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.installation;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.misc.FieldVerifier;
import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.invoicebinder.shared.model.DBConnectionInfo;
import com.invoicebinder.shared.model.ServerValidationResult;
import com.invoicebinder.shared.model.InstallationInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author mon2
 */
public class InstallServiceClientImpl implements InstallServiceClientInt {
    private final InstallServiceAsync service;
    private final Main main;
    
    public InstallServiceClientImpl(String url, Main main) {
        System.out.print(url);
        this.service = GWT.create(InstallService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.main = main;
    }    

    @Override
    public void testDBConnection(DBConnectionInfo info, Loading loading) {
        ValidationResult result = new ValidationResult();
        //1. Hostname
        if (FieldVerifier.isBlankField(info.getHostName())) {
            result.setMessage("Host name must be supplied.");
            result.setTagname("mysqlHost");
            main.getContainer().doValidation(Views.installdbtest, result);
            return;
        }
        
        //2. Username
        if (FieldVerifier.isBlankField(info.getLogin())) {
            result.setMessage("Login name must be supplied.");
            result.setTagname("mysqlUser");
            main.getContainer().doValidation(Views.installdbtest, result);
            return;
        }
        
        //3. Password
        if (FieldVerifier.isBlankField(info.getPassword())) {
            result.setMessage("Passsword must be supplied.");
            result.setTagname("mysqlPassword");
            main.getContainer().doValidation(Views.installdbtest, result);
            return;
        }
        
        //4. Port
        if (FieldVerifier.isBlankField(info.getPort())) {
            result.setMessage("Port must be supplied.");
            result.setTagname("mysqlPort");
            main.getContainer().doValidation(Views.installdbtest, result);
            return;
        }
        loading.show();
        loading.runAnimation();          
        this.service.testDBConnection(info, new TestDBConnectionCallback());
    }
    
    @Override
    public void testEmailConnection(EmailPropertiesInfo info, Loading loading) {
        ValidationResult result = new ValidationResult();
        Views view = Views.installappemail;
        // Hostname
        if (FieldVerifier.isBlankField(info.getEmailHost())) {
            result.setMessage("Email host name must be supplied.");
            result.setTagname("emailHost");
            main.getContainer().doValidation(view, result);
            return;
        }
        // Port
        if (FieldVerifier.isBlankField(info.getEmailPort())) {
            result.setMessage("Port must be supplied.");
            result.setTagname("emailPort");
                        main.getContainer().doValidation(view, result);
            return;
        }        
        // Username
        if (FieldVerifier.isBlankField(info.getEmailUsername())) {
            result.setMessage("Username name must be supplied.");
            result.setTagname("emailUsername");
            main.getContainer().doValidation(view, result);
            return;
        }
        // Password
        if (FieldVerifier.isBlankField(info.getEmailPassword())) {
            result.setMessage("Passsword must be supplied.");
            result.setTagname("emailPassword");
            main.getContainer().doValidation(view, result);
            return;
        }
        // Email From
        if (!FieldVerifier.isValidEmailAddress(info.getEmailFromAddress())) {
            result.setMessage("Valid address must be supplied for from email address.");
            result.setTagname("emailFromAddress");
            main.getContainer().doValidation(view, result);
            return;
        }  
        // Email Contact
        if (!FieldVerifier.isValidEmailAddress(info.getEmailContactUsAddress())) {
            result.setMessage("Valid address must be supplied for contact us emails.");
            result.setTagname("emailContactAddress");
            main.getContainer().doValidation(view, result);
            return;
        }        
        // Email Auth Type
        if (FieldVerifier.isBlankField(info.getEmailPassword())) {
            result.setMessage("Email Authentication type must be supplied.");
            result.setTagname("emailAuthType");
            main.getContainer().doValidation(view, result);
            return;
        }  
        loading.show();
        loading.runAnimation();  
        this.service.testEmailConnection(info, new TestEmailConnectionCallback());
    }   
    
    @Override
    public boolean validateInstallAppConfig(InstallationInfo info) {
        ValidationResult result = new ValidationResult();
        boolean isValid = true;
        Views view = Views.installappconfig;
        
        //validations
        if (FieldVerifier.isBlankField(info.getHtmlToPdfAppLocation())) {
            result.setMessage("WKHTMLtoPDF installation location is required.");
            result.setTagname("txtHtmlToPdfAppLocation");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
        if (FieldVerifier.isBlankField(info.getCurrCode())) {
            result.setMessage("Please select the currency code.");
            result.setTagname("suggestCurrencyCode");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
       if (FieldVerifier.isBlankField(info.getBusinessName())) {
            result.setMessage("Please enter business name.");
            result.setTagname("txtBusinessName");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }   
        
        if (isValid) {
            this.service.validateInstallAppConfig(info, new ValidationInstallAppConfigCallback());            
        }
        
        return isValid;
    }    
    
    @Override
    public boolean validateInstall(InstallationInfo info) {
       ValidationResult result = new ValidationResult();
       boolean isValid = true;
       Views view = Views.installdbcreate;
        //validations
        if (FieldVerifier.isBlankField(info.getFirstName())) {
            result.setMessage("Please enter a first name.");
            result.setTagname("firstName");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
        if (FieldVerifier.isBlankField(info.getLastName())) {
            result.setMessage("Please enter a last name.");
            result.setTagname("lastName");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
       if (FieldVerifier.isBlankField(info.getUserName())) {
            result.setMessage("Please enter a user name.");
            result.setTagname("userName");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
        if (FieldVerifier.isBlankField(info.getPassword())) {
            result.setMessage("Please enter a password.");
            result.setTagname("password");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
        if (FieldVerifier.isBlankField(info.getConfirmPassword())) {
            result.setMessage("Please confirm password.");
            result.setTagname("confirmPassword");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
        if (!info.getConfirmPassword().equals(info.getPassword())) {
            result.setMessage("Password does not match.");
            result.setTagname("confirmPassword");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
        if (!FieldVerifier.isValidEmailAddress(info.getEmailAddress())) {
            result.setMessage("Please enter a valid email address.");
            result.setTagname("emailAddress");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }
        
        if (FieldVerifier.isBlankField(info.getBusinessName())) {
            result.setMessage("Business name cannot be blank.");
            result.setTagname("businessName");
            main.getContainer().doValidation(view, result);
            isValid = false;
            return isValid;
        }        
        
        return isValid;
    }    

    @Override
    public void createDatabase(DBConnectionInfo info) {
        this.service.createDatabase(info, new CreateDatabaseCallback());
    }

    @Override
    public void createDefaultData(DBConnectionInfo info) {
        this.service.createDefaultData(info, new CreateDefaultDataCallback());
    }

    @Override
    public void createDatabaseProcedures(DBConnectionInfo info) {
        this.service.createDatabaseProcedures(info, new CreateDBProceduresCallback());
    }

    @Override
    public void createConfigAndFinalizeInstallation(InstallationInfo info) {
        this.service.createConfigAndFinalizeInstallation(info, new CreateConfigAndFinalizeInstallCallback());
    }

    @Override
    public void checkWKHTMLtoPDFPath() {
        this.service.checkWKHTMLtoPDFPath(new CheckWKHTMLtoPDFPathCallback());
    }
   
    private class CreateDBProceduresCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
             Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(Object result) {
            main.updateDBCreateProceduresResult((Boolean)result);
        }
    }
    
    private class ValidationInstallAppConfigCallback implements AsyncCallback<ServerValidationResult> {
        @Override
        public void onFailure(Throwable caught) {
             Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(ServerValidationResult result) {
            main.getContainer().getInstallAppConfig().updateStatusResult(result);
        }
    }    
    
    private class CheckWKHTMLtoPDFPathCallback implements AsyncCallback<String> {

        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(String result) {
            main.getContainer().getInstallAppConfig().setWKHTMLtoPDFLocation(result);
        }
        
    }
    
    private class CreateConfigAndFinalizeInstallCallback implements AsyncCallback {

        @Override
        public void onFailure(Throwable caught) {
             Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(Object result) {
            main.updateDBCreateConfigResult((Boolean)result);
        }
        
    }
    
    private class TestDBConnectionCallback implements AsyncCallback<ServerValidationResult> {

        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(ServerValidationResult result) {
            main.updateDBTestResult(result);
        }
    }
    
    private class TestEmailConnectionCallback implements AsyncCallback<ServerValidationResult> {

        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(ServerValidationResult result) {
            main.updateEmailTestResult(result);
        }
    }    
    
    private class CreateDatabaseCallback implements AsyncCallback<Boolean> {

        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(Boolean result) {
            main.updateDBCreateResult(result);
        }
        
    }
    
    private class CreateDefaultDataCallback implements AsyncCallback<Boolean> {

        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(Boolean result) {
            main.updateDefaultDataCreateResult(result);
        }
        
    }    
}
