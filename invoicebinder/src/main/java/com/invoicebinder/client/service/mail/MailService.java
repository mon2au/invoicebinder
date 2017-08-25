/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.mail;

import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.MailInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/mail")
public interface MailService extends RemoteService {
    public Boolean sendContactUsMail(MailInfo info);
    public Boolean sendInvoiceMail(MailInfo info, InvoiceInfo invoiceInfo, String baseUrl);
    public Boolean sendTestEmail(String recipientEmail, EmailPropertiesInfo emailConfig);  
    public Boolean saveEmailConfigData(EmailPropertiesInfo emailConfig);
    public EmailPropertiesInfo loadEmailConfigData();
}
