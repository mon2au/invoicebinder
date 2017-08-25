/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.logger;

//import com.google.gwt.logging.client.FirebugLogHandler;
import com.google.gwt.core.shared.GWT;

/**
 *
 * @author mon2
 */
public class ClientLogManager {
    //default logging to false. set true in ~/shared/misc/Utils.java when
    //it detects the base demo application.    
    //or if it is running in dev mode
    public static Boolean EnableClientLogging = false;

    public ClientLogManager() {
    }
   

    public static void writeLog(String text) {
        if (EnableClientLogging) { GWT.log(text);}
    }   
}
