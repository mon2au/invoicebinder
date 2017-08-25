/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinderhome.server.serversettings;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinderhome.server.logger.ServerLogManager;
import com.invoicebinderhome.shared.misc.Constants;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.EncryptableProperties;

/**
 *
 * @author mon2
 */
public class ServerSettingsManager {
    private static String getPersistentAppConfigPath() {
        String OS = System.getProperty("os.name").toLowerCase();
        String persistentAppConfigFilePath = null;

        if ((OS.contains("win"))) {
            persistentAppConfigFilePath = "c:/etc/invoicebinder/persistentappconfig.properties";
        }
        else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            persistentAppConfigFilePath = "/etc/invoicebinder/persistentappconfig.properties";
        }

        return persistentAppConfigFilePath;
    }

    
    public static String getApplicationSettingsProperty(String key) {
        StandardPBEStringEncryptor encryptor;
        Properties prop;
        String propValue = null;
        String path;
        
        try {
            path = ServerSettingsManager.class.getResource("../../../../appSettings.properties").toURI().getPath();
            encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(Constants.ENC_PASS);
            encryptor.setAlgorithm("PBEWITHMD5ANDDES");
            prop = new EncryptableProperties(encryptor);
            InputStream in = new FileInputStream(path);
            ServerLogManager.writeInformationLog(ServerSettingsManager.class, "Loading appSettings.properties for key " + key);
            prop.load(in);
            propValue = prop.getProperty(key);
            ServerLogManager.writeInformationLog(ServerSettingsManager.class, "appSettings.properties load successful");
        } catch (IOException | EncryptionOperationNotPossibleException | URISyntaxException ex) {
            ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.PropertiesLoadException, ex));
        }
        
        return propValue;
    }
    public static Properties getBuildSettings() {
        String path;
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(Constants.ENC_PASS);
        encryptor.setAlgorithm("PBEWITHMD5ANDDES");
        Properties prop = new EncryptableProperties(encryptor);

        try {
            path = ServerSettingsManager.class.getResource("../../../../build.properties").toURI().getPath();
            InputStream in = new FileInputStream(path);
            prop.load(in);
        } catch (IOException | URISyntaxException ex) {
            ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.PropertiesLoadException, ex));
        }
        
        return prop;
    }
    public static Properties getPersistentAppConfiguration() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();       
        encryptor.setPassword(Constants.ENC_PASS);
        encryptor.setAlgorithm("PBEWITHMD5ANDDES");
        Properties prop = new EncryptableProperties(encryptor);

        try (InputStream in = new FileInputStream(getPersistentAppConfigPath())) {
            prop.load(in);
        } catch (IOException ex) {
            ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.PropertiesLoadException, ex));
        }
        
        return prop;
    }  
    private static void setPersistentAppConfiguration(String key, String value) {
        Properties prop = new Properties();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();       

        try {
            try (InputStream in = new FileInputStream(getPersistentAppConfigPath())) {
                prop.load(in);
            } catch (IOException ex) {
                ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.PropertiesLoadException, ex)); 
            }
            
            encryptor.setPassword(Constants.ENC_PASS);
            encryptor.setAlgorithm("PBEWITHMD5ANDDES");
            value = "ENC(" + encryptor.encrypt(value) + ")";
            OutputStream out = new FileOutputStream(getPersistentAppConfigPath());
            prop.setProperty(key, value);
            prop.store(out, "#invoicebinder persistent configuration file.");
        } catch (IOException ex) {
            ServerLogManager.writeErrorLog(ServerSettingsManager.class, getFormattedExceptionMessage(ExceptionType.PropertiesSetException, ex)); 
        }    
    }     
    public static class EmailSettings {
        public static String getEmailHostName() {
            return getApplicationSettingsProperty("email.host");
        }
        public static String getEmailPort() {
            return getApplicationSettingsProperty("email.port");
        }
        public static String getEmailUsername() {
            return getApplicationSettingsProperty("email.username");
        }
        public static String getEmailPassword() {
            return getApplicationSettingsProperty("email.password");
        }
        public static String getEmailFromAddress() {
            return getApplicationSettingsProperty("email.fromaddress");
        }
        public static String getEmailContactUsAddress() {
            return getApplicationSettingsProperty("email.contactusaddress");
        }
        public static Boolean getIsEmailTransportSecure() {
            return getApplicationSettingsProperty("email.issecure").equalsIgnoreCase("true");
        }
    }

    public static class DemoApplicationSettings {
        public static String getDemoLogin() {
            return getApplicationSettingsProperty("demoapp.login");
        }
        public static String getDemoPassword() {
            return getApplicationSettingsProperty("demoapp.password");
        }
        public static String getDemoURL() {
            return getApplicationSettingsProperty("url.demoapp");
        }
    }
    
    public static class URLs {
        public static String getAppDownloadPath() {
            return getApplicationSettingsProperty("url.appdownloadpath");
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
    
    public static class AppConfiguration {
        public static String getDownloadCount() {
            return getPersistentAppConfiguration().getProperty("app.downloadcount");
        }        
        public static void setDownloadCount(String value) {
            setPersistentAppConfiguration("app.downloadcount", value);
        }
    }
}
