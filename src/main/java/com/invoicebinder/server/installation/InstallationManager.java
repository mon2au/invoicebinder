/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.server.installation;

import com.invoicebinder.core.dataaccess.DataAccess;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.server.serversettings.ServerSettingsManager;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.DBConnectionInfo;
import com.invoicebinder.shared.model.InstallationInfo;
import com.invoicebinder.shared.sql.SqlStatements;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.invoicebinder.server.systemtool.ServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.XmlWebApplicationContext;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;

/**
 *
 * @author mon2
 */
public class InstallationManager {
    
    // <editor-fold defaultstate="collapsed" desc="Prepare Database SQL Statements">
    private static List<String> prepareInstallationSqlStatements() throws URISyntaxException  {
        String scriptFolder;

        try {
            scriptFolder = InstallationManager.class.getResource("../../install/database/").toURI().getPath();
        }
        catch (URISyntaxException ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            throw ex;
        }

        List<String> sqlBuilder = new ArrayList<>();
        String line, statement = "";
        File[] files;
        String delimiter = ";";
        
        
        File sqlStatementsFolder = new File(scriptFolder);
        
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.lastIndexOf('.') > 0) {
                    int lastIndex = name.lastIndexOf('.');
                    String extension = name.substring(lastIndex);
                    if(extension.equals(".sql")) {
                        return true;
                    }
                }
                return false;
            }
        };
        
        files = sqlStatementsFolder.listFiles(filter);
        Arrays.sort(files);
        
        for (File child : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(child.getAbsolutePath())))
            {
                while ((line = br.readLine()) != null) {
                    //bypsss lines which match the ignored list
                    line = line.trim();
                    if (!ignoreLine(line)) {
                        statement += line;
                        if (line.endsWith(delimiter)) {
                            sqlBuilder.add(statement);
                            statement = "";
                        }
                    }
                }
                
            } catch (IOException e) {
                ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, e));
            }
        }
        return sqlBuilder;
    }
    
    private static List<String> prepareDefaultDataSqlStatements() throws URISyntaxException {
        String scriptFolder;
        List<String> sqlBuilder = new ArrayList<>();
        String line, statement = "";
        File[] files;
        String delimiter = ";";

        try {
            scriptFolder = InstallationManager.class.getResource("../../install/defaultdata/").toURI().getPath();
        }
        catch (URISyntaxException ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            throw ex;
        }
        
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
                    line = line.trim();
                    if (!ignoreLine(line)) {
                        statement += line;
                        if (line.endsWith(delimiter)) {
                            sqlBuilder.add(statement);
                            statement = "";
                        }
                    }
                }
                
            } catch (IOException e) {
                ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, e));
            }
        }
        return sqlBuilder;
    }
    
    private static List<String> prepareDBProceduresSqlStatements() throws URISyntaxException {
        String scriptFolder;
        List<String> sqlBuilder = new ArrayList<>();
        String line, statement = "";
        File[] files;
        String delimiter = "$$"; //the delimiter for procedures is different since the procedure itself needs to have semicolons

        try {
            scriptFolder = InstallationManager.class.getResource("../../install/procedures/").toURI().getPath();
        }
        catch (URISyntaxException ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            throw ex;
        }
        
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
                    line = line.trim();
                    //bypsss lines which match the ignored list
                    if (!ignoreLine(line)) {
                        statement += line;
                        if (line.endsWith(delimiter)) {
                            //this removes the trailing delimiter.
                            statement = statement.substring(0, statement.length() - 2);
                            sqlBuilder.add(statement);
                            statement = "";
                        }
                        else {
                            statement = statement + " ";
                        }
                    }
                }
                
            } catch (IOException e) {
                ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, e));
            }
        }
        return sqlBuilder;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Prepare Database Statements Helper Functions">
    private static List getSQLLinesToIgnore() {
        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("/*");
        ignoreList.add("--");
        return ignoreList;
    }
    
    private static boolean ignoreLine(String line) {
        List<String> ignoredList = getSQLLinesToIgnore();
        Boolean ignore = false;
        
        if (line.isEmpty()) {
            ignore = true;
            return ignore;
        }
        
        for (String ignoreText : ignoredList) {
            if (line.startsWith(ignoreText)) {
                ignore = true;
                break;
            }
        }
        return ignore;
    }
    
    public static boolean createDefaultData(DBConnectionInfo info) {
        Connection conn;
        List<String> statements;
        Boolean response = false;
        
        DataAccess data = new DataAccess(info.getLogin(), info.getPassword(), info.getHostName(), info.getPort());
        
        try {
            statements = prepareDefaultDataSqlStatements();
            conn = data.GetMySQLDatabaseConnection();
            for (String sql : statements) {
                data.createStatement(conn, sql);
            }
            data.CloseConnection(conn);
            response = true;
        }
        catch (SQLException | IOException | URISyntaxException ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
        }
        
        return response;
    }
    // </editor-fold>
    
    public static boolean createDatabase(DBConnectionInfo info) {
        Connection conn;
        Boolean response = false;
        DataAccess data = new DataAccess(info.getLogin(), info.getPassword(), info.getHostName(), info.getPort());
        
        try {
            List<String> statements = prepareInstallationSqlStatements();
            conn = data.GetMySQLDatabaseConnection();
            for (String sql : statements) {
                data.createStatement(conn, sql);
            }
            data.CloseConnection(conn);
            response = true;
        }
        catch (SQLException | IOException | URISyntaxException ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
        }
        
        return response;
    }
    
    public static boolean createDBProcedures(DBConnectionInfo info) {
        Connection conn;
        List<String> statements;
        Boolean response = false;
        
        DataAccess data = new DataAccess(info.getLogin(), info.getPassword(), info.getHostName(), info.getPort());
        
        try {
            statements = prepareDBProceduresSqlStatements();
            conn = data.GetMySQLDatabaseConnection();
            for (String sql : statements) {
                data.createStatement(conn, sql);
            }
            data.CloseConnection(conn);
            response = true;
        }
        catch (SQLException | IOException | URISyntaxException ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
        }
        
        return response;
    }
    
    public static boolean createApplicationConfig(InstallationInfo info) {
        Connection conn;
        List<String> statements;
        Boolean response;
        
        DataAccess data = new DataAccess(info.getDatabaseInfo().getLogin(), 
                info.getDatabaseInfo().getPassword(), 
                info.getDatabaseInfo().getHostName(), 
                info.getDatabaseInfo().getPort(),
                Constants.DEFAULT_DBNAME);
        
        try {
            statements = SqlStatements.prepareSQLStatements(info);
            conn = data.GetMySQLDatabaseConnection();
            for (String sql : statements) {
                data.executeUpdate(conn, sql);
            }
            data.CloseConnection(conn);
            response = true;
        }
        catch (SQLException | IOException | URISyntaxException ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            response = false;
        }

        if (response) {
            ServerLogManager.writeDebugLog(InstallationManager.class, "creating context settings");
            response = createContextSettings(info);
            ServerLogManager.writeDebugLog(InstallationManager.class, "context settings created successfully");
        }

        if (response) {
            ServerLogManager.writeDebugLog(InstallationManager.class, "writing context file");
            response = createContextFile();
            ServerLogManager.writeDebugLog(InstallationManager.class, "context file written successfully");

        }
        
        if (response) {
            ServerLogManager.writeDebugLog(InstallationManager.class, "creating application settings");
            response = createApplicationSettings(info);
            ServerLogManager.writeDebugLog(InstallationManager.class, "application settings created successfully");
        }
        
        if (response) {
            ServerLogManager.writeDebugLog(InstallationManager.class, "reloading spring application context");
            reloadApplicationContext();
            ServerLogManager.writeDebugLog(InstallationManager.class, "spring application context reloaded successfully");
        }
        
        return response;
    }
    
    private static boolean createContextFile() {
        Boolean result;
        String path = ServletRequest.getCurrentRequest().getServletContext().getRealPath("");
        File file = new File(path + "/WEB-INF/classes/" + Constants.CONTEXT_FILENAME);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            file.createNewFile();
            fos.write("context".getBytes("UTF-8"));
            result = true;
        } catch (IOException ex) {
            result = false;
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
        }
        
        return result;
    }
    
    private static boolean reloadApplicationContext() {
        Boolean result;

        try {
            //sleep a while to make sure the context has been set.
            ServerLogManager.writeDebugLog(InstallationManager.class, "waiting for context to set");
            TimeUnit.SECONDS.sleep(5);
            ServerLogManager.writeDebugLog(InstallationManager.class, "finish waiting for context to set");

            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) ContextLoader.getCurrentWebApplicationContext();
            ServerLogManager.writeDebugLog(InstallationManager.class, xmlWebApplicationContext.getApplicationName());
            xmlWebApplicationContext.refresh();

            result = true;
        } catch (BeansException | IllegalStateException | InterruptedException ex) {
            result = false;
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
        }
        
        return result;
         
    }
    
    private static boolean createContextSettings(InstallationInfo info) {
        Boolean result = true;
        
        try {
            ServerSettingsManager.ContextSettings.setContextDBHost(info.getDatabaseInfo().getHostName());
            ServerSettingsManager.ContextSettings.setContextDBName(Constants.DEFAULT_DBNAME);
            ServerSettingsManager.ContextSettings.setContextDBPassword(info.getDatabaseInfo().getPassword());
            ServerSettingsManager.ContextSettings.setContextDBUser(info.getDatabaseInfo().getLogin());
        }
        catch (Exception ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            result = false;
        }
        
        
        return result;
    }
    
private static boolean createApplicationSettings(InstallationInfo info) {
        Boolean result = true;
        
        try {
            ServerSettingsManager.EmailSettings.setEmailHostName(info.getEmailPropertiesInfo().getEmailHost());
            ServerSettingsManager.EmailSettings.setEmailPort(info.getEmailPropertiesInfo().getEmailPort());
            ServerSettingsManager.EmailSettings.setEmailAuthenticationType(info.getEmailPropertiesInfo().getEmailAuthType());
            ServerSettingsManager.EmailSettings.setEmailContactUsAddress(info.getEmailPropertiesInfo().getEmailContactUsAddress());
            ServerSettingsManager.EmailSettings.setEmailFromAddress(info.getEmailPropertiesInfo().getEmailFromAddress());
            ServerSettingsManager.EmailSettings.setEmailPassword(info.getEmailPropertiesInfo().getEmailPassword());
            ServerSettingsManager.EmailSettings.setEmailUseSecureTransport(info.getEmailPropertiesInfo().getEmailSSL());
            ServerSettingsManager.EmailSettings.setEmailUsername(info.getEmailPropertiesInfo().getEmailUsername());
            ServerSettingsManager.ApplicationSettings.setShowHomePage(false);
            ServerSettingsManager.ApplicationSettings.setIsDemoMode(false);
            ServerSettingsManager.ApplicationSettings.setWKHTMLtoPDFLocation(info.getHtmlToPdfAppLocation());
        }
        catch (Exception ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            result = false;
        }
        
        
        return result;
    }    
}
