/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.shared.misc;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;


/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> package because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is not translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {
    
    /**
     * Verifies that the specified name is valid for our service.
     *
     * In this example, we only require that the name is at least four
     * characters. In your application, you can use more complex checks to ensure
     * that usernames, passwords, email addresses, URLs, and other fields have the
     * proper syntax.
     *
     * @param username
     * @return true if valid, false if invalid
     */
    public static boolean isValidUserName(String username) {
        boolean isValid = true;
        
        if (username == null) {
            isValid = false;
        }
        
        if (username.isEmpty()) {
            isValid = false;
        }
        
        if (username.length() <= 3) {
            isValid = false;
        }
        return isValid;
    }
    
    public static boolean isBlankUserName(String username) {
        boolean isBlank = false;
        
        if (username.equals(Constants.EMPTY_STRING)) {
            isBlank = true;
        }
        
        return isBlank;
    }
    
    public static boolean isBlankPassword(String password) {
        boolean isBlank = false;
        
        if (password.equals(Constants.EMPTY_STRING)) {
            isBlank = true;
        }
        
        return isBlank;
    }
    
    public static boolean isBlankField(String field) {
        boolean isBlank = false;
        
        if (field == null) {
            isBlank = true;
            return isBlank;
        }
        
        if (field.equals(Constants.EMPTY_STRING)) {
            isBlank = true;
            return isBlank;
        }
        
        return isBlank;
    }
    
    public static boolean isValidEmailAddress(String email) {
        RegExp pattern;
	MatchResult result;
 	String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
       pattern = RegExp.compile(EMAIL_PATTERN); 
       
        result = pattern.exec(email);
	return (result.getGroupCount() > 0);       
    }
}

