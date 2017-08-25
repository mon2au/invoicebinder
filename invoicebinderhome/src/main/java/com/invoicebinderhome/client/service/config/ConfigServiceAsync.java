/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.client.service.config;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author mon2
 */
public interface ConfigServiceAsync {
    public void getDemoConfig(AsyncCallback<Void> asyncCallback);  
}
