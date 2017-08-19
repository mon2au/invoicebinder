package com.invoicebinder.shared.suggestion;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import java.math.BigDecimal;

public class ProductSuggestion implements IsSerializable, Suggestion {
    private String suggestion;
    private Long id;
    private String desc;
    private BigDecimal unitPrice;
    
    public ProductSuggestion() {
        suggestion = null;
    }
    
    public ProductSuggestion(String suggestion, Long id, String desc, BigDecimal unitPrice) {
        this.suggestion = suggestion;
        this.id = id;
        this.desc = desc;
        this.unitPrice = unitPrice;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
