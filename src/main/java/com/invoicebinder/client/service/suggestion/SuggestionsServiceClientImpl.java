/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.suggestion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 *
 * @author msushil
 */
public class SuggestionsServiceClientImpl implements SuggestionsServiceClientInt {
    private final SuggestionsServiceAsync service;
    
    public SuggestionsServiceClientImpl(String url) {
        System.out.print(url);
        this.service = GWT.create(SuggestionsService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
    }

    @Override
    public void getCitySuggestions(CitySuggestOracle.Request req, String countryCode, String stateCode, SuggestionsCallback callback) {
        this.service.getCitySuggestions(req, countryCode, stateCode, callback);
    }
    @Override
    public void getClientSuggestions(ClientSuggestOracle.Request req, SuggestionsCallback callback) {
        this.service.getClientSuggestions(req, callback);
    }
    @Override
    public void getProductSuggestions(ProductSuggestOracle.Request req, SuggestionsCallback callback) {
        this.service.getProductSuggestions(req, callback);
    }
    @Override
    public void getCurrencySuggestions(SuggestOracle.Request req, SuggestionsCallback callback) {
        this.service.getCurrencySuggestions(req, callback);
    }
}