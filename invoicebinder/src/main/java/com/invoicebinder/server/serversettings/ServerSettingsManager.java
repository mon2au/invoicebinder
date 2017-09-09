/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.server.serversettings;

import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.misc.Constants;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.Properties;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;

/**
 *
 * @author mon2
 */
public class ServerSettingsManager {
    private static Properties getSettings(String propertyFileName) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String path;
        encryptor.setPassword(Constants.ENC_PASS);
        encryptor.setAlgorithm("PBEWITHMD5ANDDES");
        Properties prop = new EncryptableProperties(encryptor);

        try {
            path = ServerSettingsManager.class.getResource("../../../../" + propertyFileName).toURI().getPath();
            InputStream in = new FileInputStream(path);
            prop.load(in);
        } catch (IOException | URISyntaxException ex) {
            ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.PropertiesLoadException, ex));
        }
        
        return prop;
    }
    private static void setSettings(String key, String value, String propertyFileName) {
        Properties prop = new Properties();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();       
        String path;

        try {
            path = ServerSettingsManager.class.getResource("../../../../" + propertyFileName).toURI().getPath();
            ServerLogManager.writeDebugLog(ServerSettingsManager.class, String.format("saving property %s to %s%s with value %s.", key, path, propertyFileName, value));
            if (path.equals("")) {
                throw new IOException("Cannot determine context settings file path");
            }

            //first load the properties.
            try (InputStream in = new FileInputStream(path)) {
                ServerLogManager.writeDebugLog(ServerSettingsManager.class, String.format("loading property file %s", path));
                prop.load(in);
            } catch (IOException ex) {
                ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.PropertiesLoadException, ex));
            }

            encryptor.setPassword(Constants.ENC_PASS);
            encryptor.setAlgorithm("PBEWITHMD5ANDDES");
            value = "ENC(" + encryptor.encrypt(value) + ")";
            OutputStream out = new FileOutputStream(path);
            prop.setProperty(key, value);
            prop.store(out, String.format("#%s settings file.", propertyFileName));
        } catch (IOException |URISyntaxException ex) {
            ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.PropertiesSetException, ex));
        }    
    }    
    private static Properties getApplicationSettings() {
        return getSettings("appSettings.properties");
    }
    private static void setApplicationSettings(String key, String value) {
        setSettings(key, value, "appSettings.properties");   
    }    
    private static Properties getBuildSettings() {
        return getSettings("build.properties");
    }
    private static void setContextSettings(String key, String value) {
        setSettings(key, value, "contextSettings.properties");   
    }
     
    public static class EmailSettings {
        public static String getEmailHostName() {
            return getApplicationSettings().getProperty("email.host");
        }
        public static String getEmailPort() {
            return getApplicationSettings().getProperty("email.port");
        }
        public static String getEmailUsername() {
            return getApplicationSettings().getProperty("email.username");
        }
        public static String getEmailPassword() {
            return getApplicationSettings().getProperty("email.password");
        }
        public static String getEmailFromAddress() {
            return getApplicationSettings().getProperty("email.fromaddress");
        }
        public static String getEmailContactUsAddress() {
            return getApplicationSettings().getProperty("email.contactusaddress");
        }
        public static String getEmailAuthenticationType() {
            return getApplicationSettings().getProperty("email.authtype");
        }
        public static String getEmailUseSecureTransport() {
            return getApplicationSettings().getProperty("email.issecure");
        }
        public static void setEmailHostName(String hostName) {
            setApplicationSettings("email.host", hostName);
        }
        public static void setEmailPort(String port) {
            setApplicationSettings("email.port", port);
        }
        public static void setEmailUsername(String userName) {
            setApplicationSettings("email.username", userName);
        }
        public static void setEmailPassword(String password) {
            setApplicationSettings("email.password", password);
        }
        public static void setEmailFromAddress(String emailFromAddress) {
            setApplicationSettings("email.fromaddress", emailFromAddress);
        }
        public static void setEmailContactUsAddress(String emailContactUsAddress) {
            setApplicationSettings("email.contactusaddress", emailContactUsAddress);
        }
        public static void setEmailAuthenticationType(String authType) {
            setApplicationSettings("email.authtype", authType);
        }
        public static void setEmailUseSecureTransport(Boolean isSecure) {
            setApplicationSettings("email.issecure", isSecure.toString().toUpperCase());
        }        
    }
    public static class ApplicationSettings {
        public static String getUploadPath() {
            String uploadPath = null;

            try {
                uploadPath = ApplicationSettings.class.getResource("../../").toURI().getPath();
                File file;

                if (uploadPath != null) {
                    uploadPath += "temp/";

                    file = new File(uploadPath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    uploadPath = file.getAbsolutePath();
                }
            }
            catch(URISyntaxException ex) {
                ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
            }

            return uploadPath + "/";
        }
        public static void setShowHomePage(Boolean showHomePage) {
            setApplicationSettings("app.settings.showhomepage", showHomePage.toString());
        }
        public static void setIsDemoMode(Boolean isDemoMode) {
            setApplicationSettings("app.settings.isdemomode", isDemoMode.toString());
        }
        public static String getWKHTMLtoPDFLocation() {
           return getApplicationSettings().getProperty("app.settings.wkhtmltopdflocation");
        }
        public static void setWKHTMLtoPDFLocation(String htmlToPdfAppLocation) {
           setApplicationSettings("app.settings.wkhtmltopdflocation", htmlToPdfAppLocation);
        }
    }
    public static class BuildInformation {
        public static String getBuildVersion() {
            return getBuildSettings().getProperty("build.version");
        }
        public static String getBuildDate() {
            return getBuildSettings().getProperty("build.date");
        }
    }
    public static class ContextSettings {
        public static void setContextDBName(String value) {
            setContextSettings("invdb.dbname", value);
        }
        public static void setContextDBHost(String value) {
            setContextSettings("invdb.hostname", value);
        }
        public static void setContextDBUser(String value) {
            setContextSettings("invdb.user", value);
        }
        public static void setContextDBPassword(String value) {
            setContextSettings("invdb.password", value);
        }
    }
}
