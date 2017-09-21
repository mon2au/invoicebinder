/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.invoicebindercore.exception;

/**
 *
 * @author mon2
 */
public class ExceptionManager {
    public static String getFormattedExceptionMessage(ExceptionType exType, Exception ex) {
        String formattedMsg;
        String methodName = ex.getStackTrace()[1].getMethodName();
        
        formattedMsg = "Exception Type: " + exType.toString() + ", Method: " + methodName + ", Message: " + ex.getMessage();
        return formattedMsg;
    }
    
    public static String getFormattedExceptionMessage(ExceptionType exType, String message) {
        String formattedMsg;
        
        formattedMsg = "Exception Type: " + exType.toString() + ", Message: " + message;
        return formattedMsg;
    }
}
