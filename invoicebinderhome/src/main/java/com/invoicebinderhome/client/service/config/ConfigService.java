/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.client.service.config;

import com.invoicebinderhome.shared.model.DemoConfigInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinderhome/services/config")
public interface ConfigService extends RemoteService {
    public DemoConfigInfo getDemoConfig();
}
