/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.startup.startupcontext;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author mon2
 */
public interface StartupContextServiceAsync {
    public void isAppContextAvailable(AsyncCallback<Boolean> asyncCallback);     
}
