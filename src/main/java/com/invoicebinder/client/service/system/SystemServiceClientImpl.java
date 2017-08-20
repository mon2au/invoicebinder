/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.system;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.model.SystemInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author mon2
 */
public class SystemServiceClientImpl implements SystemServiceClientInt {
    private final SystemServiceAsync service;
    private final Main mainui;
    
    public SystemServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(SystemService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    } 
    
    @Override
    public void getSystemInfo() {
        this.service.getSystemInfo(new SystemInfoCallback());
    }
    
    private class SystemInfoCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateSystemInfo((SystemInfo)result);
        }        
    }    
    
}
