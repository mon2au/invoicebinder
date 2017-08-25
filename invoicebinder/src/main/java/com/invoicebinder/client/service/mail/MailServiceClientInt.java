/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.mail;

import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.MailInfo;
import com.invoicebinder.shared.model.EmailPropertiesInfo;

/**
 *
 * @author mon2
 */
public interface MailServiceClientInt {
    void sendContactUsMail(MailInfo info);   
    void sendInvoiceMail(MailInfo info, InvoiceInfo invoiceInfo, Loading loading);
    void sendTestEmail(String recipientEmail, EmailPropertiesInfo emailConfig, Loading loading);    
    void saveEmailConfigData(EmailPropertiesInfo emailConfig);
    void loadEmailConfigDataForEmailConfigPage();
}
