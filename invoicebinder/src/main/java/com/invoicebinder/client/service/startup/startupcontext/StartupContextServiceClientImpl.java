/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.startup.startupcontext;

import com.invoicebinder.client.InvoiceBinder;
import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author mon2
 */
public class StartupContextServiceClientImpl implements StartupContextServiceClientInt {
    private final StartupContextServiceAsync service;
    private final Main mainui;    

    public StartupContextServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(StartupContextService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }

    @Override
    public void isAppContextAvailable(InvoiceBinder entryPoint) {
        this.service.isAppContextAvailable(new CheckAppContextCallback(entryPoint));
    }
    
   
    private class CheckAppContextCallback implements AsyncCallback<Boolean> {
        InvoiceBinder entryPoint;

        public CheckAppContextCallback(InvoiceBinder entryPoint) {
            this.entryPoint = entryPoint;
        }      
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(Boolean result) {
            entryPoint.setAppContextAvailable(result);
        }
    }       
}