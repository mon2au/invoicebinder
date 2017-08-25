/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author mon2
 */
public class ConfigData  implements IsSerializable {
    private long id;
    private String key;
    private String section;
    private String value;
    private String dataType;
    private int maxLength;

    public ConfigData() {
        this.id = 0;
        this.key = null;
        this.section = null;
        this.value = null;
        this.dataType = null;
        this.maxLength = 0;
    }
    
    public ConfigData(String key, String section, String value) {
        this.key = key;
        this.section = section;
        this.value = value;
    }

    public ConfigData(long id, String key, String section, String value, String dataType, int maxLength) {
        this.id = id;
        this.key = key;
        this.section = section;
        this.value = value;
        this.dataType = dataType;
        this.maxLength = maxLength;
    }

    public ConfigData(String key, String section, String value, String dataType, int maxLength) {
        this.key = key;
        this.section = section;
        this.value = value;
        this.dataType = dataType;
        this.maxLength = maxLength;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
