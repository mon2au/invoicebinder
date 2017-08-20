/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.server.core.email;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author mon2
 */
public class EmailManager {
    public static void SendEmail (
            String hostName,
            String username,
            String password,
            String port,
            String fromEmail,
            String recipientEmail,
            String ccEmail,
            String subject,
            String message,
            String[] attachments,
            Boolean userSecureTransport) throws MessagingException{
        SendEmail(hostName,
                username,
                password,
                port,
                fromEmail,
                recipientEmail,
                ccEmail,
                subject,
                message,
                attachments,
                userSecureTransport,
                false);
    }
    
    public static void SendEmail (
            String hostName,
            String username,
            String password,
            String port,
            String fromEmail,
            String recipientEmail,
            String ccEmail,
            String subject,
            String message,
            String[] attachments) throws MessagingException{
        SendEmail(hostName,
                username,
                password,
                port,
                fromEmail,
                recipientEmail,
                ccEmail,
                subject,
                message,
                attachments,
                false,
                false);
    }
    
    public static void SendEmail(
            String hostName,
            String username,
            String password,
            String port,
            String fromEmail,
            String recipientEmail,
            String ccEmail,
            String subject,
            String message,
            String[] attachments,
            Boolean useSecureTransport,
            Boolean isHtmlContentType) throws MessagingException {

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Multipart multipart = new MimeMultipart("mixed");
        MimeBodyPart messageBodyPart;
        SMTPTransport t;      
        
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", hostName);
        if (useSecureTransport) {
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.socketFactory.port", port);
            props.setProperty("mail.smtp.auth", "true");
            props.put("mail.smtp.quitwait", "false");
        } else {
            props.setProperty("mail.smtps.port", port);
            props.setProperty("mail.smtps.socketFactory.port", port);
            props.setProperty("mail.smtps.auth", "true");
            props.put("mail.smtps.quitwait", "false");
        }
        
        Session session = Session.getInstance(props, null);
        
        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
            if ((ccEmail != null) && (!ccEmail.isEmpty())) { msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false)); }
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            
            //add attachments
            if (attachments != null) {
                for (String attachment : attachments) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(source.getName());
                    messageBodyPart.setContentID(source.getName());
                    messageBodyPart.setHeader("Content-ID","<" +source.getName() + ">");
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message,"text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);
            
            if (isHtmlContentType) {
                msg.setContent(multipart, "text/html; charset=utf-8");
            }
            else {
                msg.setContent(multipart);
            }
            
            if (useSecureTransport) {
                t = (SMTPTransport)session.getTransport("smtps");
            } else {
                t = (SMTPTransport)session.getTransport("smtp");
            }
            
            t.connect(hostName, username, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        }
        catch (MessagingException ex) {
            throw ex;
        }
    }
}
