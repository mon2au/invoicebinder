/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.service.mail;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.misc.FieldVerifier;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.MailInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.invoicebinder.shared.model.EmailPropertiesInfo;

/**
 *
 * @author mon2
 */
public class MailServiceClientImpl implements MailServiceClientInt {
    private final MailServiceAsync service;
    private final Main mainui;
    
    public MailServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(MailService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }
    
    @Override
    public void sendContactUsMail(MailInfo info)   {
        ValidationResult result = new ValidationResult();  
        //1. name
        if (FieldVerifier.isBlankField(info.getName())) {
            result.setMessage("Please enter your name.");
            result.setTagname("txtName");
            mainui.getContainer().doValidation(Views.contactus, result);
            return;
        }
        
        //2. email
        if (FieldVerifier.isBlankField(info.getClientEmail())) {
            result.setMessage("Please enter your email.");
            result.setTagname("txtEmail");
            mainui.getContainer().doValidation(Views.contactus, result);
            return;
        }
        
        if (!FieldVerifier.isValidEmailAddress(info.getClientEmail())) {
            result.setMessage("Email address is incorrect.");
            result.setTagname("txtEmail");
            mainui.getContainer().doValidation(Views.contactus, result);
            return;
        }
        
       //3. subject
        if (FieldVerifier.isBlankField(info.getSubject())) {
            result.setMessage("Please enter subject.");
            result.setTagname("txtSubject");
            mainui.getContainer().doValidation(Views.contactus, result);
            return;
        }
        
       //4. message
        if (FieldVerifier.isBlankField(info.getMessage())) {
            result.setMessage("Please enter your message.");
            result.setTagname("txtMessage");
            mainui.getContainer().doValidation(Views.contactus, result);
            return;
        }        
        this.service.sendContactUsMail(info, new SendContactUsMailCallback());
    }

    @Override
    public void sendInvoiceMail(MailInfo info, InvoiceInfo invoiceInfo, Loading loading) {
        ValidationResult result = new ValidationResult();  
        
        //email
        if (FieldVerifier.isBlankField(info.getRecipientEmail())) {
            result.setMessage("Please enter your email.");
            result.setTagname("txtEmail");
            mainui.getContainer().doValidation(Views.showinvoice, result);
            return;
        }
        
        if (!FieldVerifier.isValidEmailAddress(info.getRecipientEmail())) {
            result.setMessage("Email address is incorrect.");
            result.setTagname("txtEmail");
            mainui.getContainer().doValidation(Views.showinvoice, result);
            return;
        }

        loading.show();
        loading.runAnimation();   
        this.service.sendInvoiceMail(info, invoiceInfo, GWT.getHostPageBaseURL(), new SendInvoiceMailCallback());
    }

    @Override
    public void sendTestEmail(String recipientEmail, EmailPropertiesInfo emailConfig, Loading loading) {
        loading.show();
        loading.runAnimation();          
        this.service.sendTestEmail(recipientEmail, emailConfig, new SendTestEmailCallback());
    }

    @Override
    public void loadEmailConfigDataForEmailConfigPage() {
        this.service.loadEmailConfigData(new LoadEmailConfigData());
    }

    @Override
    public void saveEmailConfigData(EmailPropertiesInfo emailConfig) {
        this.service.saveEmailConfigData(emailConfig, new SaveEmailConfigDataCallback());
    }
    
    private class SaveEmailConfigDataCallback implements AsyncCallback<Boolean> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Boolean result) {
            mainui.getContainer().getConfig().updateEmailTemplateStatus(result);
        }
    }
    
    private class LoadEmailConfigData implements AsyncCallback<EmailPropertiesInfo> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(EmailPropertiesInfo result) {
            mainui.getContainer().getConfig().getEmailConfigPanel().setEmailData(result);
        }        
    }
    
    private class SendTestEmailCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Object result) {
            mainui.updateTestEmail((Boolean)result);
        }
    }
    
    private class SendContactUsMailCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Object result) {
            mainui.updateContactUsMail((Boolean)result);
        }
    }
    
    private class SendInvoiceMailCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Object result) {
            mainui.updateInvoiceMail((Boolean)result);
        }
    }
}
