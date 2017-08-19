/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.suggestion;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

/**
 *
 * @author mon2
 */
public class CitySuggestion implements IsSerializable, Suggestion {
    private String suggestion;
    private Long id;
    private String postCode;
    
    public CitySuggestion() {
        suggestion = null;
    }
    
    public CitySuggestion(String suggestion, Long id) {
        this.suggestion = suggestion;
        this.id = id;
    }   

    public CitySuggestion(String suggestion, Long id, String postCode) {
        this.suggestion = suggestion;
        this.id = id;
        this.postCode = postCode;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
