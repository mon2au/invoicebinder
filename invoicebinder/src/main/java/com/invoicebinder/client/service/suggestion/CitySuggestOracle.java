/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.suggestion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 *
 * @author mon2
 */
public class CitySuggestOracle extends SuggestOracle {
    
    private final SuggestionsServiceClientImpl suggestionsservice;
    private String countryCode;
    private String stateCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
    
    public CitySuggestOracle() {
        this.suggestionsservice = new SuggestionsServiceClientImpl(GWT.getModuleBaseURL() + "services/suggestions");
    }
    
    @Override
    public void requestSuggestions(SuggestOracle.Request request, SuggestOracle.Callback callback) {
        this.suggestionsservice.getCitySuggestions(request, this.getCountryCode(), this.getStateCode(), new SuggestionsCallback(request, callback));
    }
}
