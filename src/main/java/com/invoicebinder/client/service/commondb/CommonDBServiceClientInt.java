/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.commondb;

import com.invoicebinder.client.ui.widget.globaladdress.GlobalAddressPanel;

/**
 *
 * @author msushil
 */
public interface CommonDBServiceClientInt {
    void loadCountryData(GlobalAddressPanel addressPanel);
    void loadStateData(GlobalAddressPanel addressPanel, String countryCode);   
    void loadCityData(GlobalAddressPanel addressPanel, String match, String countryCode, String stateCode, int limit);
}
