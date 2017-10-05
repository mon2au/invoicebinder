/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.server.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinder.invoicebindercore.file.FileManager;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.server.serversettings.ServerSettingsManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/paypalnotify")
public class PaypalNotifyServiceImpl extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        ServerLogManager.writeDebugLog(PaypalNotifyServiceImpl.class, "doGet from PaypalNotifyServiceImpl has been called.");
        
        try {
            ServerLogManager.writeInformationLog(PaypalNotifyServiceImpl.class, "Query String: " + req.getQueryString());
        }
        catch (Exception e) {
            ServerLogManager.writeErrorLog(PaypalNotifyServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, e));
        }
        
        ServerLogManager.writeDebugLog(PaypalNotifyServiceImpl.class, "doGet from PaypalNotifyServiceImpl call completed.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServerLogManager.writeDebugLog(PaypalNotifyServiceImpl.class, "doPost from PaypalNotifyServiceImpl has been called.");

        try {
            ServerLogManager.writeInformationLog(PaypalNotifyServiceImpl.class, "Query String: " + req.getQueryString());
        }
        catch (Exception e) {
            ServerLogManager.writeErrorLog(PaypalNotifyServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, e));
        }

        ServerLogManager.writeDebugLog(PaypalNotifyServiceImpl.class, "doPost from PaypalNotifyServiceImpl call completed.");
    }
}

