/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.unrestricted;

import com.invoicebinder.client.service.mail.MailServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.MailInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class ContactUs extends Composite {
    
    private static final ContactUsUiBinder uiBinder = GWT.create(ContactUsUiBinder.class);

    public void setValidationResult(ValidationResult validation) {
        Element element;
        element = DOM.getElementById(validation.getTagname());
        element.focus();
        ValidationPopup.Show(validation.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }

    public void updateMailResponse(boolean result) {
        lblContactUsResult.setStyleName("message-box");
        lblContactUsResult.setWidth("190px");
        lblContactUsResult.setVisible(true);
        
        if (result) {
            txtName.setText("");
            txtEmail.setText("");
            txtSubject.setText("");
            txtMessage.setText("");
            lblContactUsResult.addStyleName("success");
            lblContactUsResult.setText("Email has been sent successfully.");
        }
        else {
            lblContactUsResult.addStyleName("error");
            lblContactUsResult.setText("Error sending email.");
        } 
    }
   
    interface ContactUsUiBinder extends UiBinder<Widget, ContactUs> {
    }
    
    @UiField HTMLPanel contactUsPanel;
    private TextBox txtName;
    private TextBox txtEmail;
    private TextBox txtSubject;
    private TextArea txtMessage;
    private final Label lblHeader;
    private final HorizontalPanel buttonPanel;
    private final Button btnSend;
    private final MailServiceClientImpl mailService;
    private final Main main;
    private final Label lblContactUsResult;
    
    public ContactUs(Object main) {
        this.main = (Main)main;        
        initWidget(uiBinder.createAndBindUi(this));
        this.mailService = new MailServiceClientImpl(GWT.getModuleBaseURL() + "services/mail", this.main);        
        lblHeader = new Label();

        lblHeader.getElement().setInnerHTML("<h5><span>Get in touch with us</span></h5>");
        contactUsPanel.add(lblHeader);
        contactUsPanel.add(getContactTable());
        buttonPanel = new HorizontalPanel();
        lblContactUsResult = new Label();
        lblContactUsResult.setVisible(false);
        
        //buttons
        btnSend = new Button();
        btnSend.setStyleName("appbutton-default");
        btnSend.setText("Send");    
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSend);
        contactUsPanel.add(buttonPanel);
        contactUsPanel.setStyleName("container");
        btnSend.addClickHandler(new DefaultClickHandler(this));
    }
    
    private FlexTable getContactTable() {
        FlexTable table = new FlexTable();   
        txtName = new TextBox();
        txtName.getElement().setId("txtName");
        txtEmail = new TextBox();
        txtEmail.getElement().setId("txtEmail");
        txtSubject = new TextBox();
        txtSubject.getElement().setId("txtSubject");        
        txtMessage = new TextArea();       
        txtMessage.getElement().setId("txtMessage");        
        txtMessage.setSize("650px", "200px");
        txtMessage.setStyleName("text-area");
        
        table.setHTML(0, 0, "");
        //client information
        table.setHTML(1, 0, "Name:");
        table.setWidget(1, 1, txtName);
        table.setHTML(2, 0, "Email:");
        table.setWidget(2, 1, txtEmail);
        table.setHTML(3, 0, "Subject:");
        table.setWidget(3, 1, txtSubject);
        table.setHTML(4, 0, "Message:");
        table.setWidget(4, 1, txtMessage);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);       
               
        return table;
    }
    
    private void sendEmail() {
        MailInfo info = new MailInfo();
        
        info.setName(txtName.getText());
        info.setSubject(txtSubject.getText());
        info.setMessage(txtMessage.getText());
        info.setClientEmail(txtEmail.getText());
        this.mailService.sendContactUsMail(info);
    }
    
    private String getContactUsMessage() {
        String message;
        
        message = "Client Name: " + txtName.getText() + "\n";
        message += "Client Email: " + txtEmail.getText() + "\n";
        message += "Subject: " + txtSubject.getText() + "\n";        
        message += "Message: " + txtMessage.getText() + "\n";   
        
        return message;        
    }
        
    private class DefaultClickHandler implements ClickHandler {
        private final ContactUs pageReference;
        
        public DefaultClickHandler(ContactUs reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSend) {
                this.pageReference.sendEmail();
                event.getNativeEvent().preventDefault();
            }
        }
    }
}