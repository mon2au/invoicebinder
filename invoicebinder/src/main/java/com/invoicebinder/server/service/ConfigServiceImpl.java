/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.server.service;

import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinder.client.service.config.ConfigService;
import com.invoicebinder.server.dataaccess.ConfigDAO;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.config.Configuration;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.SaveConfigResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("config")
public class ConfigServiceImpl extends RemoteServiceServlet implements
        ConfigService {
    
    @Autowired
    private ConfigDAO configDAO;
    
    @Override
    public ArrayList<ConfigData> loadConfigData(ConfigurationSection section) {
        ArrayList<ConfigData> lstConfig = null;
        
        try
        {
            lstConfig = configDAO.getConfigData(section);
        }
        catch (Exception ex) {
            ServerLogManager.writeErrorLog(ConfigServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));
        }
        return lstConfig;
    }
    
    @Override
    public SaveConfigResult saveConfigData(ArrayList<ConfigData> data, ConfigurationSection section) {
        List<Configuration> configList = new ArrayList<Configuration>();
        Configuration config;
        SaveConfigResult result = new SaveConfigResult();
        result.setSection(section);
        
        try {
            for (ConfigData item: data){
                config = new Configuration();
                config.setConfigKey(item.getKey());
                config.setConfigSection(item.getSection());
                config.setDataType(item.getDataType());
                config.setMaxLength(item.getMaxLength());
                config.setValue(item.getValue());
                configList.add(config);
            }
            configDAO.setConfigData(configList, section);
            result.setStatus(true);
        }
        catch (Exception ex) {
            ServerLogManager.writeErrorLog(ConfigServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));
            result.setStatus(false);
        }
        
        return result;
    }
}
