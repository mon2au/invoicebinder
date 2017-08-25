/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.client.service.config;

import com.invoicebinderhome.client.ui.alert.Alert;
import com.invoicebinderhome.client.ui.alert.AlertLevel;
import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.shared.model.DemoConfigInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author mon2
 */
public class ConfigServiceClientImpl implements ConfigServiceClientInt {
    private final ConfigServiceAsync service;
    private final Index index;
    
    public ConfigServiceClientImpl(String url, Index index) {
        System.out.print(url);
        this.service = GWT.create(ConfigService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.index = index;        
    }    

    @Override
    public void getDemoConfig() {
        this.service.getDemoConfig(new GetDemoConfigCallback());
    }
    
    private class GetDemoConfigCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            index.updateDemoConfigDetails((DemoConfigInfo)result);
        }
    }
}
