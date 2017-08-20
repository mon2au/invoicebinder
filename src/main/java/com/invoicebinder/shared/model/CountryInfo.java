/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.List;

/**
 *
 * @author mon2
 */
public class CountryInfo implements IsSerializable {
    private List<CountryItem> countryList;
    private int defaultItemIndex; 

    public List<CountryItem> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryItem> countryList) {
        this.countryList = countryList;
    }

    public int getDefaultItemIndex() {
        return defaultItemIndex;
    }

    public void setDefaultItemIndex(int defaultItemIndex) {
        this.defaultItemIndex = defaultItemIndex;
    }
}
