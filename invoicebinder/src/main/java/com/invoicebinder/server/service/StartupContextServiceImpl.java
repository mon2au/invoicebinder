/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.service;


import com.invoicebinder.client.service.startup.startupcontext.StartupContextService;
import com.invoicebinder.server.logger.ServerLogManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.io.InputStream;

import com.invoicebinder.shared.misc.Constants;
import org.springframework.stereotype.Service;


/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Service("startupcontext")
public class StartupContextServiceImpl extends RemoteServiceServlet implements
        StartupContextService {

    @Override
    public Boolean isAppContextAvailable() {
        return isContextConfigAvailable();
    }
    
    private Boolean isContextConfigAvailable() {
        Boolean result = false;
        InputStream in = StartupContextServiceImpl.class.getResourceAsStream("../../../../../" + Constants.CONTEXT_FILENAME);
        
        if (in != null) {
            result = true;
        }
        
         ServerLogManager.writeInformationLog(StartupContextServiceImpl.class, "Application context available: " + result.toString());
        return result;
    }    
    
}
