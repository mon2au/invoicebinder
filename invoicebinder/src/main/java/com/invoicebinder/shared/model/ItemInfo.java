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
public class ItemInfo implements IsSerializable {
    private long id;
    private String name;
    private String desc;
    private BigDecimal unitPrice;
    private int itemQty;

    public ItemInfo() {
        id = 0;
        name = "";
        desc = "";
        unitPrice = new BigDecimal("0");
        itemQty = 0;        
    }
    
    public ItemInfo(long id, String name, String desc, BigDecimal unitPrice, int itemQty) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.unitPrice = unitPrice;
        this.itemQty = itemQty;
    }

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

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }
}
