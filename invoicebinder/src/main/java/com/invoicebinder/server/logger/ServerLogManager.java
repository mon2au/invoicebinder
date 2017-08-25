/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.logger;

import org.apache.logging.log4j.LogManager;

/**
 *
 * @author mon2
 */
public class ServerLogManager {
    public static void writeInformationLog(Class<?> whichClass, String text) {
        LogManager.getLogger(whichClass).info(text);
    }
    
    public static void writeErrorLog(Class<?> whichClass, String text) {
        LogManager.getLogger(whichClass).error(text);
    }

    public static void writeDebugLog(Class<?> whichClass, String text) {
        LogManager.getLogger(whichClass).debug(text);
    }
    
    public static void writeWarnLog(Class<?> whichClass, String text) {
        LogManager.getLogger(whichClass).warn(text);
    }
}
