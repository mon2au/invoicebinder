/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.mail;

import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.MailInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author mon2
 */
public interface MailServiceAsync {
    void sendContactUsMail(MailInfo info, AsyncCallback<Boolean> asyncCallback);
    void sendInvoiceMail(MailInfo info, InvoiceInfo invoiceInfo, String baseUrl, AsyncCallback<Boolean> asyncCallback);
    void sendTestEmail(String recipientEmail, EmailPropertiesInfo emailConfig, AsyncCallback<Boolean> asyncCallback);
    void saveEmailConfigData(EmailPropertiesInfo emailConfig, AsyncCallback<Boolean> asyncCallback);
    void loadEmailConfigData(AsyncCallback<EmailPropertiesInfo> asyncCallback);
}
