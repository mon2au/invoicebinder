/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinderhome.server.service;

import com.invoicebinderhome.client.service.config.ConfigService;
import com.invoicebinderhome.server.serversettings.ServerSettingsManager;
import com.invoicebinderhome.shared.model.DemoConfigInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
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
    
    @Override
    public DemoConfigInfo getDemoConfig() {
        DemoConfigInfo info = new DemoConfigInfo();
        info.setDemoLogin(ServerSettingsManager.DemoApplicationSettings.getDemoLogin());
        info.setDemoPassword(ServerSettingsManager.DemoApplicationSettings.getDemoPassword());
        info.setDemoURL(ServerSettingsManager.DemoApplicationSettings.getDemoURL());
        return info;
    }
}
