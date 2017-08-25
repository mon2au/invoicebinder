/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.startup.startupconfig;

import com.invoicebinder.client.InvoiceBinder;
import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.model.AppSettingsInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author mon2
 */
public class StartupConfigServiceClientImpl implements StartupConfigServiceClientInt {
    private final StartupConfigServiceAsync service;
    private final Main mainui;

    public StartupConfigServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(StartupConfigService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }     

    @Override
    public void loadApplicationSettings(InvoiceBinder entryPoint) {
        this.service.loadApplicationSettings(new LoadApplicationSettingsCallback(entryPoint));
    }
    
    private class LoadApplicationSettingsCallback implements AsyncCallback<AppSettingsInfo> {
        InvoiceBinder entryPoint;

        public LoadApplicationSettingsCallback(InvoiceBinder entryPoint) {
            this.entryPoint = entryPoint;
        }      
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(AppSettingsInfo result) {
            entryPoint.setApplicationSettings(result);
        }
    }    
}
