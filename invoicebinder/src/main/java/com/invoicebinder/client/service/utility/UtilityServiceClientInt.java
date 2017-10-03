/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.utility;

import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.MailInfo;

/**
 *
 * @author mon2
 */
public interface UtilityServiceClientInt {
    void getNextAutoNum(String autoNumId);  
    void createPDFFile(String contentHtml, String downloadFileName);
    void sendInvoiceEmailWithPDF(String contentHtml, InvoiceInfo invoiceInfo, MailInfo mailInfo, Main main, Loading loading);
    void sendInvoiceEmail(InvoiceInfo invoiceInfo, MailInfo mailInfo, Main main, Loading loading);
}
