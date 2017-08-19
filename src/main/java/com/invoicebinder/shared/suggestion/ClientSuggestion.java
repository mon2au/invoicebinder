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
 * @author mon2
 */
public class ClientSuggestion implements IsSerializable, Suggestion {
    private String suggestion;
    private String firstName;
    private String lastName;
    private Long id;
    
    public ClientSuggestion() {
        suggestion = null;
    }
    
    public ClientSuggestion(String suggestion, Long id) {
        this.suggestion = suggestion;
        this.id = id;
    }  
    
    public ClientSuggestion(String suggestion, Long id, String firstName, String lastName) {
        this.suggestion = suggestion;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
