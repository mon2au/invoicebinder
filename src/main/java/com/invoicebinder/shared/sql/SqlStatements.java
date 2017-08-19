/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.sql;

import static com.invoicebinder.server.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.server.core.exception.ExceptionType;
import com.invoicebinder.server.installation.InstallationManager;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.InstallationInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author mon2
 */
public class SqlStatements {
    private static HashMap<String, String> getAppConfigData(InstallationInfo info) {
        HashMap<String, String> appSettingsData = new HashMap();
        
        try {
            appSettingsData.put("_APPLANDINGPAGE_", "LOGINPAGE");
            appSettingsData.put("_BUSINESSNAME_", info.getBusinessName());
            appSettingsData.put("_BUSINESSLOGOURL_", "/images/logo.png");       
            appSettingsData.put("_APPTITLE_", info.getBusinessName());
            appSettingsData.put("_APPSLOGAN_", info.getBusinessName());
            appSettingsData.put("_CURRENCY_", info.getCurrCode());
            appSettingsData.put("_TAXLABEL_", Constants.DEFAULT_TAX_LABEL);
            appSettingsData.put("_EMAILADDRESS_", info.getEmailAddress());
            appSettingsData.put("_FIRSTNAME_", info.getFirstName());
            appSettingsData.put("_LASTNAME_", info.getLastName());            
            appSettingsData.put("_PASSWORD_", info.getPassword());   
            appSettingsData.put("_USERSTATUS_", "ACTIVE");   
            appSettingsData.put("_USERNAME_", info.getUserName());  
            appSettingsData.put("_INVOICETEMPLATE_", Constants.DEFAULT_INVOICETEMPLATE);
        }
        catch (Exception ex) {
            ServerLogManager.writeErrorLog(SqlStatements.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
        }
        
        return appSettingsData;
    }    
    
    public static List<String> prepareSQLStatements(InstallationInfo info) throws URISyntaxException {
        String scriptFolder = InstallationManager.class.getResource("../../install/configdata/").toURI().getPath();
        List<String> sqlBuilder = new ArrayList<>();
        String line;
        File[] files;
        HashMap<String, String> appSettingsData;

        
        appSettingsData = getAppConfigData(info);
        File sqlStatementsFolder = new File(scriptFolder);
        
        FilenameFilter filter = (dir, name) -> {
            if(name.lastIndexOf('.') > 0) {
                int lastIndex = name.lastIndexOf('.');
                String extension = name.substring(lastIndex);
                if(extension.equals(".sql")) {
                    return true;
                }
            }
            return false;
        };
        
        files = sqlStatementsFolder.listFiles(filter);
        Arrays.sort(files);
        
        for (File child : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(child.getAbsolutePath())))
            {
                while ((line = br.readLine()) != null) {
                    //bypsss lines which match the ignored list
                    line = populateSqlStatementWithData(line, appSettingsData);
                    line = line.trim();
                     if (!"".equals(line)) {
                        sqlBuilder.add(line);
                    }
                }
                
            } catch (IOException e) {
                ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, e));
            }
        }
        return sqlBuilder;
    }    
    
    private static String populateSqlStatementWithData(String sql, HashMap<String, String> appSettingsData) {
        String delimiter = "_";
        String token;
        String value;

        int startIndex = 0, endIndex = 0;
        
        if (sql.startsWith("--")) {
            return "";
        }
        
        while (endIndex < sql.lastIndexOf(delimiter)) {
            startIndex = sql.indexOf(delimiter, startIndex);
            endIndex = sql.indexOf(delimiter, startIndex + 1); 
            
            token = sql.substring(startIndex, endIndex + 1);
            value = appSettingsData.get(token);
            
            if (value != null) {
                sql = sql.replace(token, value);
            }   
            else {
                sql = "";
            }
        }
        
        return sql;
    }    
}
