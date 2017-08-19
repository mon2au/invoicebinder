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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/install")
public interface InstallService extends RemoteService {
    public ServerValidationResult testDBConnection(DBConnectionInfo info);
    public ServerValidationResult testEmailConnection(EmailPropertiesInfo info);
    public Boolean createDatabase(DBConnectionInfo info);           
    public Boolean createDefaultData(DBConnectionInfo info);        
    public Boolean createDatabaseProcedures(DBConnectionInfo info);   
    public Boolean createConfigAndFinalizeInstallation(InstallationInfo info);    
    public String checkWKHTMLtoPDFPath();
    public ServerValidationResult validateInstallAppConfig(InstallationInfo info);
}
