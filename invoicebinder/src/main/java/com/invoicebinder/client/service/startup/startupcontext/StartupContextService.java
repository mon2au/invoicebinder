/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.startup.startupcontext;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/startupconfig")
public interface StartupContextService extends RemoteService {
    public Boolean isAppContextAvailable();
}
