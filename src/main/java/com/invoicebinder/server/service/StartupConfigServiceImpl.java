/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.server.service;

import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.client.service.startup.startupconfig.StartupConfigService;
import com.invoicebinder.server.dataaccess.ConfigDAO;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.enums.config.ApplicationSettingsItems;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.startup.HomePageSettings;
import com.invoicebinder.shared.model.AppSettingsInfo;
import com.invoicebinder.shared.model.ConfigData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;

/**
 *
 * @author mon2
 */

@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("startupconfig")
public class StartupConfigServiceImpl extends RemoteServiceServlet implements
        StartupConfigService {
    
    @Autowired
    private ConfigDAO configDAO;
    
    @Override
    public AppSettingsInfo loadApplicationSettings() {
        HashMap<String, String> configData = new HashMap();
        List<ConfigData> lstConfig;
        AppSettingsInfo settings = new AppSettingsInfo();

        try {

            lstConfig = configDAO.getConfigData(ConfigurationSection.ApplicationSettings);

            for (ConfigData data : lstConfig) {
                configData.put(data.getKey(), data.getValue());
            }

            //context is available so go ahead and populate application settings from database.
            settings.setAppSlogan(configData.get(ApplicationSettingsItems.APPSLOGAN.toString()));
            settings.setAppTitle(configData.get(ApplicationSettingsItems.APPTITLE.toString()));
            if (configData.get(ApplicationSettingsItems.APPLANDINGPAGE.toString()) != null)
                settings.setAppLandingPage(HomePageSettings.valueOf(configData.get(ApplicationSettingsItems.APPLANDINGPAGE.toString())));

            //config is getting loded because app context is available so set the app as installed and direct to login page.
            if ((settings.getIsAppInstalled() == null) || (!settings.getIsAppInstalled())) {
                settings.setAppLandingPage(HomePageSettings.LOGINPAGE);
            }
        }
        catch (Exception ex) {
            ServerLogManager.writeErrorLog(StartupConfigServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));
        }
        
        return settings;
    }
}
