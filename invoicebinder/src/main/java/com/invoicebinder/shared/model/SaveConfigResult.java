/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.shared.model;

import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author mon2
 */
public class SaveConfigResult implements IsSerializable {
    private ConfigurationSection section;
    private Boolean status;

    public ConfigurationSection getSection() {
        return section;
    }

    public void setSection(ConfigurationSection section) {
        this.section = section;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
