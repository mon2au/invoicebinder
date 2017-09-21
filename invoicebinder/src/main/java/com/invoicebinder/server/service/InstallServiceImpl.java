/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.server.service;

import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinder.client.service.installation.InstallService;
import com.invoicebinder.server.installation.InstallationManager;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.enums.ServiceResponseStatus;
import com.invoicebinder.shared.model.EmailPropertiesInfo;
import com.invoicebinder.shared.model.DBConnectionInfo;
import com.invoicebinder.shared.model.ServerValidationResult;
import com.invoicebinder.shared.model.InstallationInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Service("install")
public class InstallServiceImpl extends RemoteServiceServlet implements
        InstallService {
    
    @Override
    public ServerValidationResult testDBConnection(DBConnectionInfo info) {
        ServerValidationResult result = new ServerValidationResult();
        Connection conn;
        
        try {
            //jdbc:mysql://${invdb.hostname}:3306/${invdb.dbname}?useUnicode=true&amp;characterEncoding=UTF-8"
            String url = "jdbc:mysql://" + info.getHostName() + ":" + info.getPort();
            conn = DriverManager.getConnection(url, info.getLogin(), info.getPassword());
            result.setStatus(ServiceResponseStatus.SUCCESS);
            result.setMessage("Connection to database server successful.");
        }
        catch (SQLException e) {
            result.setStatus(ServiceResponseStatus.FAIL);
            result.setMessage(e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public Boolean createDatabase(DBConnectionInfo info) {
        return InstallationManager.createDatabase(info);
    }
    
    @Override
    public Boolean createDefaultData(DBConnectionInfo info) {
        return InstallationManager.createDefaultData(info);
    }
    
    @Override
    public Boolean createDatabaseProcedures(DBConnectionInfo info) {
        return InstallationManager.createDBProcedures(info);
    }
    
    @Override
    public Boolean createConfigAndFinalizeInstallation(InstallationInfo info) {
        return InstallationManager.createApplicationConfig(info);
    }
    
    @Override
    public ServerValidationResult testEmailConnection(EmailPropertiesInfo info) {
        ServerValidationResult result = new ServerValidationResult();
        MailServiceImpl mail = new MailServiceImpl();
        
        if (mail.sendTestEmail(info.getEmailContactUsAddress(), info)) {
            result.setStatus(ServiceResponseStatus.SUCCESS);
            result.setMessage("Email has been sent successfully to " + info.getEmailContactUsAddress() + ".");
        }
        else {
            result.setStatus(ServiceResponseStatus.FAIL);
            result.setMessage("Email sending failed. Please check your settings.");
        }
        
        return result;
    }
    
    @Override
    public String checkWKHTMLtoPDFPath() {
        String[] WKHTMLtoPDFPaths = new String[2];
        File file;
        String appName = "";
        String confirmedPath = "";
        
        String OS = System.getProperty("os.name").toLowerCase();
        
        ServerLogManager.writeInformationLog(InstallServiceImpl.class, "Checking wkhtmltopdf installation folder");  
        
        try {
            if ((OS.contains("win"))) {
                ServerLogManager.writeInformationLog(InstallServiceImpl.class, "Detected windows operating system");  
                appName = "wkhtmltopdf.exe";
                WKHTMLtoPDFPaths[0] = "C:\\Program Files\\wkhtmltopdf\\bin\\";
                WKHTMLtoPDFPaths[1] = "C:\\Program Files (x86)\\wkhtmltopdf\\bin\\";
            }
            else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                ServerLogManager.writeInformationLog(InstallServiceImpl.class, "Detected nix operating system");  
                appName = "wkhtmltopdf";
                WKHTMLtoPDFPaths[0] = "/usr/bin/";
                WKHTMLtoPDFPaths[1] = "/usr/bin/X11/";
            }
            else {
                ServerLogManager.writeInformationLog(InstallServiceImpl.class, "Undetected operating system");                 
            }
            
            
            for (String WKHTMLtoPDFPath : WKHTMLtoPDFPaths) {
                file = new File(WKHTMLtoPDFPath + appName);
                if (file.exists()) {
                    confirmedPath = file.getAbsolutePath();
                    ServerLogManager.writeInformationLog(InstallServiceImpl.class, "Found wkhtmltopdf installed at: " + confirmedPath);  
                    break;
                }
            }
        }
        catch(Exception ex) {
            ServerLogManager.writeErrorLog(InstallServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, ex));                  
            confirmedPath = "";
        }
        
        return confirmedPath;
    }

    @Override
    public ServerValidationResult validateInstallAppConfig(InstallationInfo info) {
        ServerValidationResult result = new ServerValidationResult();
        File file = new File(info.getHtmlToPdfAppLocation());
        
        if (!file.exists()) {
            result.setStatus(ServiceResponseStatus.FAIL);
            result.setMessage("WKHTMLtoPDF application path is incorrect.");
        } 
        else {
            result.setStatus(ServiceResponseStatus.SUCCESS);
        }
        
        return result;
    }
}
