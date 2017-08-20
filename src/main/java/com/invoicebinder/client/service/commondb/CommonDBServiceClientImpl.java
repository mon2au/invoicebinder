/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.commondb;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.widget.globaladdress.GlobalAddressPanel;
import com.invoicebinder.shared.model.CountryInfo;
import com.invoicebinder.shared.model.StateInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import java.util.List;

/**
 *
 * @author mon2
 */
public class CommonDBServiceClientImpl implements CommonDBServiceClientInt {
    private final CommonDBServiceAsync service;
    
    public CommonDBServiceClientImpl(String url) {
        System.out.print(url);
        this.service = GWT.create(CommonDBService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
    }     

    @Override
    public void loadCountryData(GlobalAddressPanel addressPanel) {
        this.service.loadCountryData(new LoadCountryDataCallback(addressPanel));
    }

    @Override
    public void loadStateData(GlobalAddressPanel addressPanel, String countryCode) {
        this.service.loadStateData(countryCode, new LoadStateDataCallback(addressPanel));
    }

    @Override
    public void loadCityData(GlobalAddressPanel addressPanel, String match, String countryCode, String stateCode, int limit) {
        this.service.loadMatchingCityData(match, countryCode, stateCode, limit, new LoadCityDataCallback(addressPanel));
    }
    
    private class LoadStateDataCallback  implements AsyncCallback<List<StateInfo>> {
        private final GlobalAddressPanel addressPanel;
        public LoadStateDataCallback(GlobalAddressPanel addressPanel) {
            this.addressPanel = addressPanel;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(List<StateInfo> result) {
            addressPanel.updateStateList(result);
        }        
    }
    private class LoadCountryDataCallback  implements AsyncCallback<CountryInfo> {
        private final GlobalAddressPanel addressPanel;
        public LoadCountryDataCallback(GlobalAddressPanel addressPanel) {
            this.addressPanel = addressPanel;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(CountryInfo result) {
            addressPanel.updateCountryList(result);
        }        
    }
    private class LoadCityDataCallback  implements AsyncCallback {
        private final GlobalAddressPanel addressPanel;
        public LoadCityDataCallback(GlobalAddressPanel addressPanel) {
            this.addressPanel = addressPanel;
        }
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(Object result) {
            //addressPanel.updateCityList((List<CityInfo>)result);
        }        
    }   
}
