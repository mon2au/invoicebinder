/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.config;

import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.SaveConfigResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

@RemoteServiceRelativePath("/invoicebinder/services/config")
public interface ConfigService extends RemoteService {
        public ArrayList<ConfigData> loadConfigData(ConfigurationSection section);
        public SaveConfigResult saveConfigData(ArrayList<ConfigData> data, ConfigurationSection section); 
}
