/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.shared.suggestion;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

/**
 *
 * @author msushil
 */
public class CurrencySuggestion implements IsSerializable, Suggestion {
    private String suggestion;
    private Long id;
    private String country;
    private String name;
    private String code;
    
    public CurrencySuggestion() {
        suggestion = null;
    }
    
    public CurrencySuggestion(String suggestion, String code) {
        this.suggestion = suggestion;
        this.code = code;
    } 
    
    public CurrencySuggestion(String suggestion, Long id, String name, String code) {
        this.suggestion = suggestion;
        this.id = id;
        this.name = name;
        this.code = code;
    } 
    
    public CurrencySuggestion(String suggestion, Long id, String country, String name, String code) {
        this.suggestion = suggestion;
        this.id = id;
        this.name = name;
        this.code = code;
        this.country = country;
    }   
    
    @Override
    public String getDisplayString() {
        return suggestion;
    }
    
    @Override
    public String getReplacementString() {
        return suggestion;
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
