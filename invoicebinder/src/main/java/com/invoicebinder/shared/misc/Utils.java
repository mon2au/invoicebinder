/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.shared.misc;

import com.invoicebinder.client.logger.ClientLogManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import com.google.gwt.http.client.URL;

/**
 *
 * @author mon2
 */
public class Utils {
    public static String convertNullToBlank(String value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }
    public static Timestamp getMaxDateTimeValue() {
        Timestamp ts = Timestamp.valueOf("9999-12-31 23:59:59");
        return ts;
    }    
    public static String getParamFromHref(String key) {
        Map<String, String> params = new HashMap<>();
        String value;
        String[] paramArray;
        String href = Window.Location.getHref();
        int idx = href.lastIndexOf("/");
        int length = href.length();
        value = URL.decode(href.substring(++idx, length));
        if ((!"".equals(value)) && (value.contains("&"))) {
            paramArray = value.split("&");
            for(final String s : paramArray) {
                if ((!"".equals(s)) && (s.contains("="))) {
                    final String split[] = s.split("=");
                    params.put(split[0], split[1]);            
                }
            }
        }
        else if((!"".equals(value)) && (value.contains("="))) {
            final String split[] = value.split("=");
            params.put(split[0], split[1]);              
        }
        return params.get(key);
    }
    public static String getAppName() {  
        String appPath;
        
        appPath = Window.Location.getPath();
        appPath = appPath.replaceAll("/", "");
        return appPath;
    }
    public static boolean isDemoApplication() {  
        return isDemoApplication(false);
    }
    public static boolean isDemoApplication(Boolean supressDemoMessage) {  
        String appPath;
        
        appPath = Window.Location.getPath();
        appPath = appPath.replaceAll("/", "");
        
        if (appPath.equals(Constants.DEMOAPP_BASE_URL)) {
            if (!supressDemoMessage) Window.alert(Constants.DEMOAPP_MESSAGE);
            return true;
        }    
        else {
            return false;
        }
    }
    public static boolean isBaseApplication() {  
        String appPath;
        Boolean isBase = false;
       
        appPath = Window.Location.getPath();
        appPath = appPath.replaceAll("/", "");
      
        //enable client logging if this is the debug base url.
        if (appPath.equals(Constants.BASEAPP_URL)) {
            isBase = true;
        }    
        
        ClientLogManager.writeLog("Application path: ".concat(appPath));
        ClientLogManager.writeLog("Is this the base (debugging) url: ".concat(Boolean.toString(isBase)));
        return isBase;
    }
    public static boolean isDevelopmentMode() {
        Boolean isDevMode;
        isDevMode = !GWT.isProdMode() && GWT.isClient();
        return isDevMode;
    }
    public static void setupClientLogging() {
        String appPath;
       
        appPath = Window.Location.getPath();
        appPath = appPath.replaceAll("/", "");
        
        //enable client logging if this is the demo url or if this is dev mode.
        if (appPath.equals(Constants.BASEAPP_URL) || appPath.equals(Constants.DEMOAPP_BASE_URL) || appPath.contains(Constants.HEROKUAPP_URL)) {
            ClientLogManager.EnableClientLogging = true;
        }     
    }
}
