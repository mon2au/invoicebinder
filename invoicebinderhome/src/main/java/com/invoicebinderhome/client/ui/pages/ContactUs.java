/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinderhome.client.ui.pages;

import com.invoicebinderhome.client.service.contactus.ContactUsServiceClientImpl;
import com.invoicebinderhome.client.ui.alert.Loading;
import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.shared.model.ContactUsInfo;
import com.invoicebinderhome.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ContactUs extends Composite {
    
    private static final ContactUsUiBinder uiBinder = GWT.create(ContactUsUiBinder.class);
    @UiField HTMLPanel contactUsPanel;
    @UiField TextBox firstName;
    @UiField TextBox lastName;
    @UiField TextBox emailAddress;
    @UiField TextArea contactMessage;
    @UiField Button btnSubmit;
    @UiField Label messageLabel;
    
    private final Index index;
    private final ContactUsServiceClientImpl service;
    private final Loading loading;

    public void updateMessageResponse(Boolean result) {
        this.loading.hide();
        if (result) {
            this.messageLabel.setStyleName("message-successvisible");
            this.messageLabel.setText("Your message has been sent successfully.");
        }
        else {
            this.messageLabel.setStyleName("message-errorvisible");
            this.messageLabel.setText("Unable to send the message.");
        }
    }

    public void updateValidations(ValidationResult result) {
        Element element;
        element = DOM.getElementById(result.getTagname());
        element.focus();
        this.messageLabel.setStyleName("message-visible");
        this.messageLabel.setText(result.getMessage());
    }
    
    interface ContactUsUiBinder extends UiBinder<Widget, ContactUs> {
    }
    
    public ContactUs(Index index) {
        this.index = index;
        this.service = new ContactUsServiceClientImpl(GWT.getModuleBaseURL() + "services/contactus", this.index);        
        initWidget(uiBinder.createAndBindUi(this));
        
        loading = new Loading();
        setupPage();
        Window.scrollTo(0, 0);        
    }
    
    private void setupPage() {
        messageLabel.setStyleName("message-hidden");
        btnSubmit.setStyleName("submitbutton");
        btnSubmit.getElement().setId("submit");
        btnSubmit.setText("SUBMIT");
        
        firstName.getElement().setId("firstName");
        lastName.getElement().setId("lastName");
        emailAddress.getElement().setId("emailAddress");
        
        firstName.setStyleName("textbox-fields");
        lastName.setStyleName("textbox-fields");
        emailAddress.setStyleName("textbox-fields");
        
        contactMessage.getElement().setId("contactMessage");
        contactMessage.setHeight("200px");
        contactMessage.setStyleName("textarea-fields");
        btnSubmit.addClickHandler(new DefaultClickHandler(this));
    }
    
    private void sendContactUsMessage() {
        ContactUsInfo info = new ContactUsInfo();

        info.setEmailAddress(emailAddress.getText());
        info.setFirstName(firstName.getText());
        info.setLastName(lastName.getText());
        info.setContactMessage(contactMessage.getText());
        this.service.sendContactUsMessage(info, loading);
    }    
    
    private class DefaultClickHandler implements ClickHandler {
        private final ContactUs pageReference;
        
        public DefaultClickHandler(ContactUs reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSubmit) {
                messageLabel.setStyleName("message-hidden");
                messageLabel.setText("");
                this.pageReference.sendContactUsMessage();
                event.getNativeEvent().preventDefault();
            }
        }
    }
}
