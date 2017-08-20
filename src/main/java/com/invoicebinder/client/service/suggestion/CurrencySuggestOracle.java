/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.suggestion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 *
 * @author mon2
 */
public class CurrencySuggestOracle extends SuggestOracle {
        private final SuggestionsServiceClientImpl suggestionsservice;
        
    public CurrencySuggestOracle() {
        this.suggestionsservice = new SuggestionsServiceClientImpl(GWT.getModuleBaseURL() + "services/suggestions");
    }        

    @Override
    public void requestSuggestions(Request request, Callback callback) {
        this.suggestionsservice.getCurrencySuggestions(request, new SuggestionsCallback(request, callback));
    }
}
