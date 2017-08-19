/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.installation;

import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.invoicebinder.shared.model.DBConnectionInfo;
import com.invoicebinder.shared.model.ServerValidationResult;
import com.invoicebinder.shared.model.InstallationInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author mon2
 */
public interface InstallServiceAsync {
    public void testDBConnection(DBConnectionInfo info, AsyncCallback<ServerValidationResult> callback);   
    public void testEmailConnection(EmailPropertiesInfo info, AsyncCallback<ServerValidationResult> callback);  
    public void createDatabase(DBConnectionInfo info, AsyncCallback<Boolean> asyncCallback);    
    public void createDefaultData(DBConnectionInfo info, AsyncCallback<Boolean> asyncCallback);            
    public void createDatabaseProcedures(DBConnectionInfo info, AsyncCallback<Boolean> asyncCallback);           
    public void createConfigAndFinalizeInstallation(InstallationInfo info, AsyncCallback<Boolean> asyncCallback);    
    public void checkWKHTMLtoPDFPath(AsyncCallback<String> asyncCallback);
    public void validateInstallAppConfig(InstallationInfo info, AsyncCallback<ServerValidationResult> asyncCallback);    
}
