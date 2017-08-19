/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.suggestion;

import com.invoicebinder.client.ui.controller.Main;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 *
 * @author mon2
 */
public class ClientSuggestOracle extends SuggestOracle {
        private final SuggestionsServiceClientImpl suggestionsservice;
    
    public ClientSuggestOracle(Main main) {
        this.suggestionsservice = new SuggestionsServiceClientImpl(GWT.getModuleBaseURL() + "services/suggestions");
    }
    
    @Override
    public void requestSuggestions(SuggestOracle.Request request, SuggestOracle.Callback callback) {
        this.suggestionsservice.getClientSuggestions(request, new SuggestionsCallback(request, callback));
    }
    
}
