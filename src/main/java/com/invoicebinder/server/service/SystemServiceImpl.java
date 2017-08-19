/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.server.service;

import com.invoicebinder.client.service.system.SystemService;
import com.invoicebinder.server.serversettings.ServerSettingsManager;
import com.invoicebinder.shared.model.SystemInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author msushil
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = Exception.class)
@Service("system")
public class SystemServiceImpl extends RemoteServiceServlet implements SystemService {

    @Override
    public SystemInfo getSystemInfo() {
        SystemInfo info = new SystemInfo();
        info.setBuildVersion(ServerSettingsManager.BuildInformation.getBuildVersion());
        info.setBuildDate(ServerSettingsManager.BuildInformation.getBuildDate());
        
        return info;
    }
    
}
