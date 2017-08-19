/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.suggestion;

/**
 *
 * @author msushil
 */
public interface SuggestionsServiceClientInt {
    void getCitySuggestions(CitySuggestOracle.Request req, String countryCode, String stateCode, SuggestionsCallback callback);
    void getClientSuggestions(ClientSuggestOracle.Request req, SuggestionsCallback callback);
    void getProductSuggestions(ClientSuggestOracle.Request req, SuggestionsCallback callback);
    void getCurrencySuggestions(CurrencySuggestOracle.Request req, SuggestionsCallback callback);
}

