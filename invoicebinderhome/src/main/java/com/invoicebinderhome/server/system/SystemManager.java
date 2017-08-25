/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinderhome.server.system;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.core.shell.ProcessExec;
import com.invoicebinder.core.shell.ShellExecute;
import com.invoicebinder.core.shell.ShellExecuteResult;
import com.invoicebinderhome.server.logger.ServerLogManager;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author mon2
 */
public class SystemManager {
    private static final String scriptFolder = SystemManager.class.getResource("../../scripts/").getPath();
    
    public static boolean createSystemUser(String rootPassword, String userName) {
        try {
            return ProcessExec.executeScript("run_user_create.sh",scriptFolder, new String[]{ rootPassword, userName });
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    public static boolean createMySqlDBSchema(String rootPassword, String dbName) {
        try {
            return ProcessExec.executeScript("run_dbschema_create.sh",scriptFolder, new String[]{ rootPassword, dbName });
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    /**
     * @param dbHostName - hostName of the database server.
     * @param dbUserName - userName of the database server.
     * @param dbPassword - password of the database server.
     * @param dbName - database name.
     * @param scriptName - name of the script to run relative to the scripts folder.
     * @return
     */    
    public static boolean createMySqlDBObjects(String dbHostName, String dbUserName, String dbPassword, String dbName, String scriptName) {
        try {
            return ProcessExec.executeScript("run_dbobjects_create.sh",scriptFolder, new String[]{ dbHostName, dbUserName, dbPassword, dbName, scriptName });
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    public static boolean executeMySqlDBStatements(String dbHostName, String dbUserName, String dbPassword, String database, String statement) {
        try {
            return ProcessExec.executeScript("run_dbsql_statement.sh",scriptFolder, new String[]{ dbHostName, dbUserName, dbPassword, database, statement });
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    public static boolean createApplication(String appName, String tomcatBaseDirectory, String rootPassword, String physicalApplicationPath, 
            String buildType, String encryptedAppName, String rootUser) {
        ShellExecuteResult result;
        
        ServerLogManager.writeInformationLog(SystemManager.class, "AppName: " + appName + ", TomcatBase: " + tomcatBaseDirectory + ", RootPass: " + 
                rootPassword + ", PhysicalPath: " + physicalApplicationPath + ", BuildType: " + buildType + ", EncryptedAppName: " +
                encryptedAppName + ", RootUser: " + rootUser);
            
        try {
            result = ShellExecute.executeCommand(new String[] {"/bin/bash", "-c", "echo " + rootPassword + " | sudo -S " +
                    scriptFolder + "run_app_create.sh " + appName + " " + tomcatBaseDirectory + " " +
                    rootPassword + " " + physicalApplicationPath + " " + buildType + " " + encryptedAppName + " " + rootUser});
            
            logShellResult(result);
            return result.isSuccess();
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    public static boolean createSymLink(String path, String linkTo, String rootPassword, String runAsUser) {
        try {
            return ProcessExec.executeScript("run_symlink_create.sh",scriptFolder, new String[]{ path, linkTo, rootPassword, runAsUser });
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    public static boolean runTomcatCommand(String appPath, String tomcatUser, String tomcatPasswd, String rootPassword, String rootUser) {
        ShellExecuteResult result;
        
        try {
            result = ShellExecute.executeCommand(new String[] {"/bin/bash", "-c", "echo " + rootPassword + " | sudo -S " +
                    scriptFolder + "run_tomcat_command.sh " + tomcatUser + " " + tomcatPasswd + " " + appPath + " " + rootUser});
            
            logShellResult(result);
            return result.isSuccess();
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    public static boolean createWhoAmIFile() {
        
        try {
         ProcessExec.whoAmI();
         return true;
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    public static boolean prepareSystemScripts(String rootPassword) {
        try {
            File dir = new File(scriptFolder);
            for (File child : dir.listFiles()) {
                child.setExecutable(true); 
            }
            return true;
        }
        catch (Exception ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }
    }
    
    public static boolean runCurlCommand(String rootUser, String rootPassword, String url) {
        ShellExecuteResult result;
        
        try {
            result = ShellExecute.executeCommand(new String[] {"/bin/bash", "-c", "echo " + rootPassword + " | sudo -S " +
                    scriptFolder + "run_curl_command.sh " + rootUser + " " + url});
            logShellResult(result);
            return result.isSuccess();
        }
        catch (IOException | InterruptedException ex) {
            ServerLogManager.writeErrorLog(SystemManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            return false;
        }        
    }
    
    private static void logShellResult(ShellExecuteResult result) {
        if (result.isSuccess()) {
            ServerLogManager.writeInformationLog(SystemManager.class, result.getStandardOutput());
        }
        else {
            ServerLogManager.writeErrorLog(SystemManager.class, result.getErrorOutput());
        }
    }
}
