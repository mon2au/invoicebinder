/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinderhome.server.service;

import com.invoicebinder.invoicebindercore.email.EmailManager;
import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinderhome.client.service.contactus.ContactUsService;
import com.invoicebinderhome.server.logger.ServerLogManager;
import com.invoicebinderhome.server.serversettings.ServerSettingsManager;
import com.invoicebinderhome.shared.misc.Constants;
import com.invoicebinderhome.shared.model.ContactUsInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.mail.MessagingException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("contactus")
public class ContactUsServiceImpl extends RemoteServiceServlet implements
        ContactUsService {
    
    @Override
    public boolean sendContactUsMessage(ContactUsInfo info) {
        try {
            ServerLogManager.writeInformationLog(ContactUsServiceImpl.class, "Sending contact message from invoice-binder "
                    + "(Name: " + info.getFirstName() + " " + info.getLastName() + " , "
                    + "Email: " +  info.getEmailAddress() + " "
                    + "Message: " + info.getContactMessage() + ")");
            sendContactUsEmail(info);
            return true;
        }
        catch(MessagingException ex) {
            ServerLogManager.writeErrorLog(ContactUsServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));             
            return false;
        }
    }
    
    private void sendContactUsEmail(ContactUsInfo info) throws MessagingException {
        EmailManager.SendEmail(
                ServerSettingsManager.EmailSettings.getEmailHostName(),
                ServerSettingsManager.EmailSettings.getEmailUsername(),
                ServerSettingsManager.EmailSettings.getEmailPassword(),
                ServerSettingsManager.EmailSettings.getEmailPort(),
                ServerSettingsManager.EmailSettings.getEmailFromAddress(),
                ServerSettingsManager.EmailSettings.getEmailContactUsAddress(),
                "",
                "Contact us email from " + Constants.TITLE,
                getContactUsEmailMessage(info), null,
                ServerSettingsManager.EmailSettings.getIsEmailTransportSecure(), false);
    }
    
    private String getContactUsEmailMessage(ContactUsInfo info) {
        return "User sent email from the contact us page: " + 
                "Name = " + info.getFirstName() + " " + info.getLastName() + ", " +
                "Email = (" + info.getEmailAddress() + "), " + 
                "Message = (" + info.getContactMessage() + ")";
    }
}
