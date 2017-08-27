package com.invoicebinder.server.service;

import com.invoicebinder.server.dataaccess.ConfigDAO;
import com.invoicebinder.shared.enums.AutoLoginViews;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.config.PaymentConfigItems;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.server.serversettings.ServerSettingsManager;
import com.invoicebinder.shared.htmltemplates.EmailTemplateMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.invoicebinder.invoicebindercore.email.EmailManager;
import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinder.client.service.mail.MailService;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.model.MailInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.mail.MessagingException;
import org.springframework.transaction.annotation.Transactional;
import com.invoicebinder.shared.model.EmailPropertiesInfo;

import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("mail")
public class MailServiceImpl extends RemoteServiceServlet implements
        MailService {

    @Autowired
    private ConfigDAO configDAO;
    
    // <editor-fold defaultstate="collapsed" desc="Mail Service RPC Methods">
    @Override
    public Boolean sendContactUsMail(MailInfo info) {
        String template = EmailTemplateMgr.getContactUsEmailTemplate();
        
        info.setMessage(template);
        try {
            sendEmail(info);
            return true;
        }
        catch(MessagingException ex) {
            ServerLogManager.writeErrorLog(MailServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));
            return false;
        }
    }
    @Override
    public Boolean sendInvoiceMail(MailInfo info, InvoiceInfo invoiceInfo, String baseUrl) {
        ServerLogManager.writeDebugLog(MailServiceImpl.class, "sendInvoiceMail function start");
        int attachmentSize;
        ConfigData data;
        String payButtonDisplay;
        ArrayList<ConfigData> configItems;
        String template = EmailTemplateMgr.getInvoiceEmailTemplate();
        String logoPath = MailServiceImpl.class.getResource("../../../../../").getPath();
        HashMap<String, String> templateVariables = new HashMap<>();
        HashMap<String, String> dataMap = new HashMap();

        configItems = configDAO.getConfigData(ConfigurationSection.Payments);

        for (int i=0; i<configItems.size(); i++) {
            data = configItems.get(i);
            dataMap.put(data.getKey(), data.getValue());
        }

        if (Boolean.valueOf(dataMap.get(PaymentConfigItems.ALLOWONLINEPAYMENT.toString()))) {
            payButtonDisplay = "block";
        }
        else {
            payButtonDisplay = "none";
        }

        templateVariables.put("[MESSAGE_PREVIEW]", info.getSubject());
        templateVariables.put("[COMPANY_NAME]", info.getCompanyName());
        templateVariables.put("[INVOICE_NUMBER]", invoiceInfo.getInvoiceNumber());
        templateVariables.put("[INVOICE_MESSAGE]", info.getMessage());
        templateVariables.put("[AUTH_TOKEN]", invoiceInfo.getAuthToken());
        templateVariables.put("[AUTH_AMOUNT]", invoiceInfo.getAmount().toString());
        templateVariables.put("[INVOICE_ID]", Long.toString(invoiceInfo.getId()));
        templateVariables.put("[INVOICEBINDER_URL]", baseUrl + "index.html#autologin");
        templateVariables.put("[AUTOLOGIN_VIEW]", AutoLoginViews.invoice.toString());
        templateVariables.put("[PAY_BUTTON_DISPLAY]", payButtonDisplay);

        //find and replace variables in the template.
        for (Map.Entry<String, String> entry : templateVariables.entrySet()) {
            template = template.replace(entry.getKey(), entry.getValue());
        }   
        
        //add logo
        if (info.getAttachments() != null && info.getAttachments().length > 0) {
            attachmentSize = info.getAttachments().length;
            String[] attachments = new String[attachmentSize + 1];
            for (int i = 0; i<attachmentSize; i++) {
                attachments[i] = info.getAttachments()[i];
            }
            attachments[attachmentSize] = new File(logoPath).getAbsolutePath() + "/../../logo/logo.png";
            info.setAttachments(attachments);
        }

        //debug log attachments
        ServerLogManager.writeDebugLog(MailServiceImpl.class, "Iterating through sendInvoiceMail attachments");
        for (int i = 0; i<info.getAttachments().length; i++) {
            ServerLogManager.writeDebugLog(MailServiceImpl.class, String.format("attachment %d: %s", i, info.getAttachments()[i]));
            ServerLogManager.writeDebugLog(MailServiceImpl.class, String.format("attachment path exists: %s", new File(info.getAttachments()[i]).exists()));
        }
        ServerLogManager.writeDebugLog(MailServiceImpl.class, "Finished iterating through sendInvoiceMail attachments");
        
        try {
            info.setMessage(template);
            sendEmail(info);
            ServerLogManager.writeDebugLog(MailServiceImpl.class, "sendInvoiceMail function end");
            return true;
        }
        catch (MessagingException ex){
            ServerLogManager.writeErrorLog(MailServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));
            return false;
        }


    }
    @Override
    public Boolean sendTestEmail(String recipientEmail, EmailPropertiesInfo emailConfig) {
        String template = EmailTemplateMgr.getTestEmailTemplate();
        
        MailInfo info = new MailInfo();
        
        try {
            info.setRecipientEmail(recipientEmail);
            info.setSubject("Test email from InvoiceBinder");
            info.setMessage(template);
            sendEmail(info, emailConfig);
            return true;
        }
        catch (MessagingException ex){
            ServerLogManager.writeErrorLog(MailServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));
            return false;
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Mail Service sendEmail Methods">
    private void sendEmail(MailInfo info, EmailPropertiesInfo emailConfig) throws MessagingException {
        EmailManager.SendEmail(
                emailConfig.getEmailHost(),
                emailConfig.getEmailUsername(),
                emailConfig.getEmailPassword(),
                emailConfig.getEmailPort(),
                emailConfig.getEmailFromAddress(),
                info.getRecipientEmail(),
                info.getCcEmail(),
                info.getSubject(),
                info.getMessage(),
                info.getAttachments(),
                emailConfig.getEmailSSL(),
                true);
    }
    
    private void sendEmail(MailInfo info) throws MessagingException {
        EmailManager.SendEmail(ServerSettingsManager.EmailSettings.getEmailHostName(),
                ServerSettingsManager.EmailSettings.getEmailUsername(),
                ServerSettingsManager.EmailSettings.getEmailPassword(),
                ServerSettingsManager.EmailSettings.getEmailPort(),
                ServerSettingsManager.EmailSettings.getEmailFromAddress(),
                info.getRecipientEmail(),
                info.getCcEmail(),
                info.getSubject(),
                info.getMessage(),
                info.getAttachments(),
                Boolean.valueOf(ServerSettingsManager.EmailSettings.getEmailUseSecureTransport().toLowerCase()),
                true);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Mail Service Utility Methods">
    public boolean sendForgotPasswordMail(MailInfo info) {
        String template = EmailTemplateMgr.getForgotPasswordEmailTemplate();
        Map<String, String> templateVariables = new HashMap<>();
        
        templateVariables.put("[RESET_PASSWORD]", info.getNewPassword());  
        
        //find and replace variables in the template.
        for (Map.Entry<String, String> entry : templateVariables.entrySet()) {
            template = template.replace(entry.getKey(), entry.getValue());
	}        
        
        info.setMessage(template);
        try {
            sendEmail(info);
            return true;
        }
        catch(MessagingException ex) {
            ServerLogManager.writeErrorLog(MailServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));
            return false;
        }
    }
    // </editor-fold>
    
    @Override
    public Boolean saveEmailConfigData(EmailPropertiesInfo emailConfig) {
        Boolean result;
        
        try {
            ServerSettingsManager.EmailSettings.setEmailUsername(emailConfig.getEmailUsername());
            ServerSettingsManager.EmailSettings.setEmailPassword(emailConfig.getEmailPassword());
            ServerSettingsManager.EmailSettings.setEmailHostName(emailConfig.getEmailHost());
            ServerSettingsManager.EmailSettings.setEmailPort(emailConfig.getEmailPort());
            ServerSettingsManager.EmailSettings.setEmailUseSecureTransport(emailConfig.getEmailSSL());
            ServerSettingsManager.EmailSettings.setEmailFromAddress(emailConfig.getEmailFromAddress());
            result = true;
        }
        catch(Exception ex) {
            result = false;
        }
        
        return result;
    }
    
    @Override
    public EmailPropertiesInfo loadEmailConfigData() {
        EmailPropertiesInfo emailConfig = new EmailPropertiesInfo();
        
        emailConfig.setEmailAuthType(ServerSettingsManager.EmailSettings.getEmailUseSecureTransport());
        emailConfig.setEmailFromAddress(ServerSettingsManager.EmailSettings.getEmailFromAddress());
        emailConfig.setEmailHost(ServerSettingsManager.EmailSettings.getEmailHostName());
        emailConfig.setEmailPassword(ServerSettingsManager.EmailSettings.getEmailPassword());
        emailConfig.setEmailPort(ServerSettingsManager.EmailSettings.getEmailPort());
        emailConfig.setEmailSSL(Boolean.valueOf(ServerSettingsManager.EmailSettings.getEmailUseSecureTransport().toLowerCase()));
        emailConfig.setEmailUsername(ServerSettingsManager.EmailSettings.getEmailUsername());
        
        return emailConfig;
    }
}
