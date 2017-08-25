/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.suggestion;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/suggestions")
public interface SuggestionsService extends RemoteService {
    public CitySuggestOracle.Response getCitySuggestions(CitySuggestOracle.Request req, String countryCode, String stateCode);
    public ClientSuggestOracle.Response getClientSuggestions(ClientSuggestOracle.Request req);
    public ProductSuggestOracle.Response getProductSuggestions(ProductSuggestOracle.Request req);
    public CurrencySuggestOracle.Response getCurrencySuggestions(CurrencySuggestOracle.Request req);
}
