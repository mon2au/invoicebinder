/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;

/**
 *
 * @author mon2
 */
public class ProductInfo implements IsSerializable {
    private long id;
    private String name;
    private String description;
    private BigDecimal unitprice;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }
}
