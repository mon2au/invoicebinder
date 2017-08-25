/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.commondb;

import java.util.List;

import com.invoicebinder.shared.model.CityInfo;
import com.invoicebinder.shared.model.CountryInfo;
import com.invoicebinder.shared.model.StateInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author mon2
 */
public interface CommonDBServiceAsync {
    public void loadCountryData(AsyncCallback<CountryInfo> asyncCallback);
    public void loadStateData(String countryCode, AsyncCallback<List<StateInfo>> asyncCallback);
    public void loadMatchingCityData(String match, String countryCode, String stateCode, int limit, AsyncCallback<List<CityInfo>> asyncCallback);
}
