/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.client.service.contactus;

import com.invoicebinderhome.client.ui.alert.Alert;
import com.invoicebinderhome.client.ui.alert.AlertLevel;
import com.invoicebinderhome.client.ui.alert.Loading;
import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.shared.misc.FieldVerifier;
import com.invoicebinderhome.shared.model.ContactUsInfo;
import com.invoicebinderhome.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author mon2
 */
public class ContactUsServiceClientImpl implements ContactUsServiceClientInt {
    private final ContactUsServiceAsync service;
    private final Index index;   
    
    public ContactUsServiceClientImpl(String url, Index index) {
        System.out.print(url);
        this.service = GWT.create(ContactUsService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.index = index;        
    }     

    @Override
    public void sendContactUsMessage(ContactUsInfo info, Loading loading) {
        ValidationResult result = new ValidationResult();
        //validations
        if (FieldVerifier.isBlankField(info.getFirstName())) {
            result.setMessage("Please enter the first name.");
            result.setTagname("firstName");
            index.updateValidationsForContactUsPage(result);
            return;
        }
        
        if (FieldVerifier.isBlankField(info.getFirstName())) {
            result.setMessage("Please enter the first name.");
            result.setTagname("firstName");
            index.updateValidationsForContactUsPage(result);
            return;
        }
        
        if (FieldVerifier.isBlankField(info.getLastName())) {
            result.setMessage("Please enter the last name.");
            result.setTagname("lastName");
            index.updateValidationsForContactUsPage(result);
            return;
        }
        
        if (FieldVerifier.isBlankField(info.getEmailAddress())) {
            result.setMessage("Please enter the email address.");
            result.setTagname("emailName");
            index.updateValidationsForContactUsPage(result);
            return;
        }
        
        if (FieldVerifier.isBlankField(info.getContactMessage())) {
            result.setMessage("Please enter the message.");
            result.setTagname("contactMessage");
            index.updateValidationsForContactUsPage(result);
            return;
        }
        
        if (!FieldVerifier.isValidEmailAddress(info.getEmailAddress())) {
            result.setMessage("Email address is not correct.");
            result.setTagname("emailAddress");
            index.updateValidationsForContactUsPage(result);
            return;
        }
        
        loading.show();
        loading.runAnimation();
        this.service.sendContactUsMessage(info, new ContactUsMessageCallback());
    }
    
    private class ContactUsMessageCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            index.updateContactUsMessageResponse((Boolean)result);
        }
    }     
    
}
