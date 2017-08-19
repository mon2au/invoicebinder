/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.service.utility;

import com.invoicebinder.client.service.mail.MailServiceClientImpl;
import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.MailInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author msushil
 */
public class UtilityServiceClientImpl implements UtilityServiceClientInt {
    private final UtilityServiceAsync service;
    private final Main mainui;
    
    public UtilityServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(UtilityService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }
    
    @Override
    public void getNextAutoNum(String autoNumId) {
        this.service.getNextAutoNum(autoNumId, new LoadNextAutoNumCallback());
    }
    
    @Override
    public void createPDFFile(String contentHtml, String invoiceNumber) {
        this.service.createPDFFile(contentHtml, invoiceNumber,  new CreatePDFFileCallback());
    }
    
    @Override
    public void sendInvoiceEmailWithPDF(String contentHtml, InvoiceInfo invoiceInfo, MailInfo mailInfo, Main main, Loading loading) {
        this.service.createPDFFile(contentHtml, invoiceInfo.getInvoiceNumber(),
                new CreatePDFFileForEmailAttachmentCallback(mailInfo, invoiceInfo, main, loading));
    }
    
    // <editor-fold defaultstate="collapsed" desc="Callback Handlers"> 
    private class LoadNextAutoNumCallback implements AsyncCallback<String> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(String result) {
            mainui.getContainer().loadAutonumForInvoice(result);
        }
    }
    private class CreatePDFFileForEmailAttachmentCallback implements AsyncCallback<String> {
        private final MailInfo mailInfo;
        private final InvoiceInfo invoiceInfo;
        private final Loading loading;
        private final Main main;
        
        public CreatePDFFileForEmailAttachmentCallback(MailInfo mailInfo, InvoiceInfo invoiceInfo, Main main, Loading loading) {
            this.mailInfo = mailInfo;
            this.invoiceInfo = invoiceInfo;
            this.loading = loading;
            this.main = main;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(String result) {
            sendInvoiceEmailAndPDF(result,
                    this.mailInfo,
                    this.invoiceInfo,
                    this.main,
                    this.loading);
        }
        
        private void sendInvoiceEmailAndPDF(String fileName, MailInfo mailInfo, InvoiceInfo invoiceInfo, Main main, Loading loading) {

            String[] attachments = new String[1];
            attachments[0] = fileName;

            MailServiceClientImpl mailService = new MailServiceClientImpl(GWT.getModuleBaseURL() + "services/mail", main);
            mailInfo.setAttachments(attachments);
            mailService.sendInvoiceMail(mailInfo, invoiceInfo, loading);
        }
    }  
    private class CreatePDFFileCallback implements AsyncCallback {      
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Object result) {
            downloadPDFFile((String)result);
        }
        
        private void downloadPDFFile(String fileName) {
            String downloadServletUrl;
            downloadServletUrl = GWT.getHostPageBaseURL() + "invoicebinder/filedownload?filename=" + fileName;
            Window.open(downloadServletUrl, "Download PDF","");
        }
    }
// </editor-fold>

}
