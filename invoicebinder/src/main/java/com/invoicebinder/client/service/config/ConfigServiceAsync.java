/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.config;

import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.SaveConfigResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;

public interface ConfigServiceAsync {
    public void loadConfigData(ConfigurationSection section, AsyncCallback<ArrayList<ConfigData>> asyncCallback); 
    public void saveConfigData(ArrayList<ConfigData> data, ConfigurationSection section, AsyncCallback<SaveConfigResult> asyncCallback);  
}
