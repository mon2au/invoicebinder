/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.htmltemplates;

import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.installation.InstallationManager;
import com.invoicebinder.server.logger.ServerLogManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;


/**
 *
 * @author mon2
 */
public class EmailTemplateMgr {

    static String emailTemplateFolder;

    static {
        try {
            emailTemplateFolder = EmailTemplateMgr.class.getResource("../../emailtemplates/").toURI().getPath();
        } catch (URISyntaxException ex) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, ex));
        }
    }
    
    public static String getInvoiceEmailTemplate() {
        return loadTemplateFile("email-invoice.html");
    }
            
    public static String getTestEmailTemplate() {        
        return loadTemplateFile("email-test.html");
    }

    public static String getContactUsEmailTemplate() {
        return loadTemplateFile("email-contactus.html");
    }

    public static String getForgotPasswordEmailTemplate() {
        return loadTemplateFile("email-forgotpassword.html");
    }
    
    private static String loadTemplateFile(String fileName) {
        File file = new File(emailTemplateFolder + fileName);
        StringBuilder templateContents = new StringBuilder();
        String line;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath())))
        {
            while ((line = br.readLine()) != null) {
                //bypsss lines which match the ignored list
                line = line.trim();
                templateContents.append(line);
            }
            
        }
        catch (IOException e) {
            ServerLogManager.writeErrorLog(InstallationManager.class, getFormattedExceptionMessage(ExceptionType.SystemException, e));
        }
        
        return templateContents.toString();
    }
}
