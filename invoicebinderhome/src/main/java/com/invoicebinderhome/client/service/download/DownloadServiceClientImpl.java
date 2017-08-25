/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client.service.download;

import com.invoicebinderhome.client.ui.alert.Alert;
import com.invoicebinderhome.client.ui.alert.AlertLevel;
import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.client.ui.pages.DownloadPage;
import com.invoicebinderhome.client.ui.pages.HomePage;
import com.invoicebinderhome.shared.model.DownloadInfo;
import com.invoicebinderhome.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author mon2
 */
public class DownloadServiceClientImpl implements DownloadServiceClientInt {
    private final DownloadServiceAsync service;
    private final Index index;
    
    public DownloadServiceClientImpl(String url, Index index) {
        System.out.print(url);
        this.service = GWT.create(DownloadService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.index = index;        
    }   

    @Override
    public void getDownloadInfo(Object pageReference) {
        this.service.getDownloadInfo(new DownloadInfoCallback(pageReference));
    }

    @Override
    public void validateAndDownload(Boolean agreeToDownload) {
        ValidationResult result = new ValidationResult();
        //validations
        if (!agreeToDownload) {
            result.setMessage("Please agree to the terms and privacy policy before downloading.");
            result.setTagname("checkConditions");
            index.updateValidationsForDownloadPage(result);
            return;
        }
        
        Window.open(GWT.getHostPageBaseURL() + "/invoicebinderhome/filedownload", "_blank", "");
    }
    
    private class DownloadInfoCallback implements AsyncCallback<DownloadInfo> {
        Object pageReference;
        
        public DownloadInfoCallback(Object ref) {
            this.pageReference = ref;
        }

        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(DownloadInfo result) {
            
            if (this.pageReference.getClass().equals(HomePage.class)) {
                ((HomePage)pageReference).setDownloadInfo(result);
            }
            else if (this.pageReference.getClass().equals(DownloadPage.class)) {
                ((DownloadPage)pageReference).setDownloadInfo(result);
            }
        }
    }
}
