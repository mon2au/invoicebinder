/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.config;

import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.service.mail.MailServiceClientImpl;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.config.EmailConfigItems;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mon2
 */
public class EmailConfig extends Composite {
    
    private static final BusinessConfigUiBinder uiBinder = GWT.create(BusinessConfigUiBinder.class);
    private final Loading loading;
    @UiField HTMLPanel emailConfigPanel;
    private final TextBox txtEmailHost;
    private final TextBox txtEmailPort;
    private final TextBox txtEmailUsername;
    private final PasswordTextBox txtEmailPassword;
    private final TextBox txtEmailFromAddress;
    private final CheckBox chkUseSecureTransport;
    private final TextArea txtInvoiceEmailTemplate;
    private final CheckBox chkAttachInvoicePDF;
    private final Label lblTestEmailMessage;
    private final Label lblSaveResult;
    private final Label lblSaveResultTemplate;
    private final HorizontalPanel buttonPanel;
    private final Button btnSave;
    private final Button btnTest;
    private final HorizontalPanel noticePanel;
    private final Main main;
    private final ConfigServiceClientImpl configService;
    private final MailServiceClientImpl mailService;
    
    public void setEmailData(EmailPropertiesInfo emailConfig) {
        txtEmailHost.setText(emailConfig.getEmailHost());
        txtEmailUsername.setText(emailConfig.getEmailUsername());
        txtEmailPassword.setText(emailConfig.getEmailPassword());
        txtEmailPort.setText(emailConfig.getEmailPort());
        txtEmailFromAddress.setText(emailConfig.getEmailFromAddress());
        chkUseSecureTransport.setValue(emailConfig.getEmailSSL());     
    }
    
    public void setConfigData(ArrayList<ConfigData> arrayList) {
        if (arrayList != null) {
            HashMap<String, String> emailConfigData = new HashMap();
            arrayList.forEach((data) -> {
                emailConfigData.put(data.getKey(), data.getValue());
            });
            
            txtInvoiceEmailTemplate.setText(emailConfigData.get(EmailConfigItems.EMAILINVOICETEMPLATE.toString()));
            chkAttachInvoicePDF.setValue(Boolean.valueOf(emailConfigData.get(EmailConfigItems.EMAILINVOICEPDF.toString())));
        }
    }
    
    void updateTemplateStatus(Boolean result) {
        lblSaveResultTemplate.setStyleName("message-box");
        lblSaveResultTemplate.setWidth("190px");
        lblSaveResultTemplate.setVisible(true);
        
        if (result) {
            lblSaveResultTemplate.addStyleName("success");
            lblSaveResultTemplate.setText("Email template settings have been saved.");
        }
        else {
            lblSaveResultTemplate.addStyleName("error");
            lblSaveResultTemplate.setText("Error saving email template settings.");
        }
    }
    
    void updateStatus(Boolean result) {
        lblSaveResult.setStyleName("message-box");
        lblSaveResult.setWidth("190px");
        lblSaveResult.setVisible(true);
        
        if (result) {
            lblSaveResult.addStyleName("success");
            lblSaveResult.setText("Email configuration settings have been saved.");
        }
        else {
            lblSaveResult.addStyleName("error");
            lblSaveResult.setText("Error saving email configuration settings.");
        }
    }
    
    private void sendTestEmail() {
        EmailPropertiesInfo emailConfig = new EmailPropertiesInfo();
        
        lblTestEmailMessage.setText("");
        lblTestEmailMessage.setStyleName("");
        
        emailConfig.setEmailHost(txtEmailHost.getText());
        emailConfig.setEmailPort(txtEmailPort.getText());
        emailConfig.setEmailUsername(txtEmailUsername.getText());
        emailConfig.setEmailPassword(txtEmailPassword.getText());
        emailConfig.setEmailFromAddress(txtEmailFromAddress.getText());
        emailConfig.setEmailSSL(chkUseSecureTransport.getValue());
        
        mailService.sendTestEmail(main.getUserEmail(), emailConfig, loading);
    }
    
    void updateTestEmailResult(Boolean result) {
        this.loading.hide();
        lblTestEmailMessage.setStyleName("message-box");
        if (result) {
            lblTestEmailMessage.addStyleName("success");
            lblTestEmailMessage.setText("Test email sent successfully.");
        }
        else {
            lblTestEmailMessage.addStyleName("error");
            lblTestEmailMessage.setText("Failed to send test email.");
        }
    }
    
    interface BusinessConfigUiBinder extends UiBinder<Widget, EmailConfig> {
    }
    
    public EmailConfig(Object main) {
        this.main = (Main)main;
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        this.mailService = new MailServiceClientImpl(GWT.getModuleBaseURL() + "services/mail", this.main);
        this.loading = new Loading();
        initWidget(uiBinder.createAndBindUi(this));
        txtEmailHost = new TextBox();
        txtEmailPort = new TextBox();
        txtEmailUsername = new TextBox();
        txtEmailPassword = new PasswordTextBox();
        txtEmailFromAddress = new TextBox();
        txtInvoiceEmailTemplate = new TextArea();
        lblTestEmailMessage = new Label();
        lblSaveResult = new Label();
        lblSaveResult.setVisible(false);
        lblSaveResultTemplate = new Label();
        lblSaveResultTemplate.setVisible(false);
        chkUseSecureTransport = new CheckBox();
        chkAttachInvoicePDF = new CheckBox();
        noticePanel = new HorizontalPanel();
        buttonPanel = new HorizontalPanel();
        btnTest = new Button();
        
        btnTest.setStyleName("appbutton-default");
        btnTest.setText("Test");
        emailConfigPanel.add(getEmailConfigTable());
        emailConfigPanel.add(getEmailTemplateTable());
        chkUseSecureTransport.setStyleName("check-box");
        chkAttachInvoicePDF.setStyleName("check-box");
        //buttons
        btnSave = new Button();
        btnSave.setStyleName("appbutton-default");
        btnSave.setText("Save");
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSave);
        buttonPanel.add(lblSaveResult);
        buttonPanel.add(lblSaveResultTemplate);
        emailConfigPanel.add(buttonPanel);
        btnSave.addClickHandler(new DefaultClickHandler(this));
        btnTest.addClickHandler(new DefaultClickHandler(this));
        
        configService.loadConfigData(ConfigurationSection.Email);
        mailService.loadEmailConfigDataForEmailConfigPage();
    }
    
    private FlexTable getEmailConfigTable() {
        FlexTable table = new FlexTable();
        Label lblEmailConfigHeader = new Label();
        lblEmailConfigHeader.getElement().setInnerHTML("<h6><span>Email Details</span></h6>");
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);
        table.setWidget(0, 0, lblEmailConfigHeader);
        //client information
        table.setHTML(1, 0, "Email Host:");
        table.setWidget(1, 1, txtEmailHost);
        table.setHTML(2, 0, "Email Port:");
        table.setWidget(2, 1, txtEmailPort);
        table.setHTML(3, 0, "Username:");
        table.setWidget(3, 1, txtEmailUsername);
        table.setHTML(4, 0, "Password:");
        table.setWidget(4, 1, txtEmailPassword);
        table.setHTML(5, 0, "From Email:");
        table.setWidget(5, 1, txtEmailFromAddress);
        table.setHTML(6, 0, "");
        table.setHTML(6, 1, "");
        table.setHTML(7, 0, "Secure Connection:");
        table.setWidget(7, 1, chkUseSecureTransport);
        table.setHTML(8, 0, "");
        table.setHTML(8, 1, "");
        
        lblTestEmailMessage.setStyleName("message-box");
        lblTestEmailMessage.addStyleName("notice");
        lblTestEmailMessage.setText("Click test to send a test email using the above details.");
        lblTestEmailMessage.setWidth("190px");
        table.setWidget(9, 0, btnTest);
        table.setWidget(9, 1, lblTestEmailMessage);
        table.setHTML(10, 0,"");
        table.setHTML(10, 1,"");
        
        //setup email notice.
        table.getFlexCellFormatter().setColSpan(11, 0, 2);
        table.getFlexCellFormatter().setColSpan(11, 1, 2);
        noticePanel.add(new Label("The email server configuration entered above will be used by the application to send emails to your clients on your behalf."));
        noticePanel.setStyleName("message-box");
        noticePanel.addStyleName("notice");
        noticePanel.setWidth("440px");
        table.setWidget(11, 0, noticePanel);
        
        return table;
    }
    private FlexTable getEmailTemplateTable() {
        FlexTable table = new FlexTable();
        Label lblEmailTemplateHeader = new Label();
        lblEmailTemplateHeader.getElement().setInnerHTML("<h6><span>Email Templates</span></h6>");
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);
        table.setWidget(0, 0, lblEmailTemplateHeader);
        
        txtInvoiceEmailTemplate.setSize("320px","150px");
        txtInvoiceEmailTemplate.setStyleName("text-area");
        //template information
        table.setHTML(1, 0, "Invoice mail:");
        table.setWidget(1, 1, txtInvoiceEmailTemplate);
        table.setHTML(2,0,"Attach Invoice PDF:");
        table.setWidget(2,1,chkAttachInvoicePDF);
        return table;
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final EmailConfig pageReference;
        
        public DefaultClickHandler(EmailConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                if (isDemoApplication()) return;             
                this.pageReference.saveEmailConfig();
                event.getNativeEvent().preventDefault();
            }
            if (sender == btnTest) {
                if (isDemoApplication()) return;                
                this.pageReference.sendTestEmail();
                event.getNativeEvent().preventDefault();
            }
        }
    }
    
    private void saveEmailConfig() {
        EmailPropertiesInfo emailConfig = new EmailPropertiesInfo(); 
        ArrayList<ConfigData> lstConfig = new ArrayList();
        ConfigData data;
        
        emailConfig.setEmailHost(txtEmailHost.getText());
        emailConfig.setEmailPort(txtEmailPort.getText());
        emailConfig.setEmailUsername(txtEmailUsername.getText());
        emailConfig.setEmailPassword(txtEmailPassword.getText());
        emailConfig.setEmailFromAddress(txtEmailFromAddress.getText());
        emailConfig.setEmailSSL(chkUseSecureTransport.getValue());
        
        mailService.saveEmailConfigData(emailConfig);
        
        data = new ConfigData(EmailConfigItems.EMAILINVOICETEMPLATE.toString(), ConfigurationSection.Email.toString(), txtInvoiceEmailTemplate.getText());
        lstConfig.add(data);
        data = new ConfigData(EmailConfigItems.EMAILINVOICEPDF.toString(), ConfigurationSection.Email.toString(), chkAttachInvoicePDF.getValue().toString());
        lstConfig.add(data);
        configService.saveConfigData(lstConfig, ConfigurationSection.Email);
    }
}
