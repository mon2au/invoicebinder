/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.suggestion;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author msushil
 */
public interface SuggestionsServiceAsync {
    public void getCitySuggestions(CitySuggestOracle.Request req, String countryCode, String stateCode, AsyncCallback callback);
    public void getClientSuggestions(ClientSuggestOracle.Request req, AsyncCallback callback);
    public void getProductSuggestions(ProductSuggestOracle.Request req, AsyncCallback callback);
    public void getCurrencySuggestions(CurrencySuggestOracle.Request req, AsyncCallback callback);
}
