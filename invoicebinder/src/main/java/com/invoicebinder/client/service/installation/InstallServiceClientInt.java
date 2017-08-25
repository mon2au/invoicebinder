/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.installation;

import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.invoicebinder.shared.model.DBConnectionInfo;
import com.invoicebinder.shared.model.InstallationInfo;

/**
 *
 * @author mon2
 */
public interface InstallServiceClientInt {
    void testDBConnection(DBConnectionInfo info, Loading loading);
    void testEmailConnection(EmailPropertiesInfo info, Loading loading);
    boolean validateInstall(InstallationInfo info);
    boolean validateInstallAppConfig(InstallationInfo info);
    void createDatabase(DBConnectionInfo info);        
    void createDefaultData(DBConnectionInfo info);
    void createDatabaseProcedures(DBConnectionInfo info);
    void createConfigAndFinalizeInstallation(InstallationInfo info);  
    void checkWKHTMLtoPDFPath();
}
