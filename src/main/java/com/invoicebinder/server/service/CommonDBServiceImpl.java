/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.server.service;

import com.invoicebinder.client.service.commondb.CommonDBService;
import com.invoicebinder.server.dataaccess.CountryDAO;
import com.invoicebinder.shared.entity.city.City;
import com.invoicebinder.shared.entity.country.Country;
import com.invoicebinder.shared.entity.state.State;
import com.invoicebinder.shared.model.CityInfo;
import com.invoicebinder.shared.model.CountryInfo;
import com.invoicebinder.shared.model.CountryItem;
import com.invoicebinder.shared.model.StateInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author msushil
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("commondb")
public class CommonDBServiceImpl extends RemoteServiceServlet implements
        CommonDBService {
    
    @Autowired
    private CountryDAO commonDB;    

    @Override
    public CountryInfo loadCountryData() {
        CountryInfo cInfo = new CountryInfo();
        CountryItem cItem;
        List<CountryItem> ctyList = new ArrayList<>();
        List<Country> countryList;
        
        countryList = commonDB.getAllCountries();
        
        for (Country cty : countryList) {
            cItem = new CountryItem();
            cItem.setId(cty.getId());
            cItem.setGeonameId(cty.getGeonameId());
            cItem.setContinentCode(cty.getContinentCode());
            cItem.setContinentName(cty.getContinentName());
            cItem.setCountryCode(cty.getCountryIsoCode());
            cItem.setCountryName(cty.getCountryName());
            
            ctyList.add(cItem);
        }
        
        cInfo.setCountryList(ctyList);
            
        return cInfo;            
    }

    @Override
    public List<StateInfo> loadStateData(String countryCode) {
        StateInfo sInfo;
        List<StateInfo> stateList = new ArrayList<>();
        List<State> stateLst;
        
        stateLst = commonDB.getAllStatesForCountry(countryCode);
        
        for (State state : stateLst) {
            sInfo = new StateInfo();
            sInfo.setId(state.getId());
            sInfo.setCountryCode(state.getCountryIsoCode());
            sInfo.setStateCode(state.getStateCode());
            sInfo.setStateName(state.getStateName());
            
            stateList.add(sInfo);
        }
                   
        return stateList;
    }

    @Override
    public List<CityInfo> loadMatchingCityData(String match, String countryCode, String stateCode, int limit) {
        CityInfo info;
        List<City> allCities;
        List<CityInfo> cityList = new ArrayList<>();
        
        allCities = commonDB.getAllMatchingCities(match, countryCode, stateCode, limit);
        
        for (City city : allCities) {
            info = new CityInfo();
            info.setId(city.getId());
            info.setCountryCode(city.getCountryIsoCode());
            info.setCityName(city.getCityName());
            info.setStateCode(city.getStateCode());
            cityList.add(info);
        }
                   
        return cityList;
    }
    
}
