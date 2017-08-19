/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.suggestion;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

/**
 *
 * @author mon2
 */
public class SuggestionsCallback implements AsyncCallback {
    
    private final SuggestOracle.Request request;
    private final SuggestOracle.Callback callback;
    
    public SuggestionsCallback(SuggestOracle.Request request, SuggestOracle.Callback callback) {
        this.request = request;
        this.callback = callback;
    }
    
    @Override
    public void onFailure(Throwable error) {
        callback.onSuggestionsReady(this.request, new Response());
    }
    
    @Override
    public void onSuccess(Object result) {       
        callback.onSuggestionsReady(this.request, (Response)result);
    }
}
