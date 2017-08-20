/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.commondb;

import com.invoicebinder.shared.model.CityInfo;
import com.invoicebinder.shared.model.CountryInfo;
import com.invoicebinder.shared.model.StateInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;


/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/commondb")
public interface CommonDBService extends RemoteService {
    public CountryInfo loadCountryData();
    public List<StateInfo> loadStateData(String countryCode);
    public List<CityInfo> loadMatchingCityData(String match, String countryCode, String stateCode, int limit);
}
