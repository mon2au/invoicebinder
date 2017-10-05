/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.server.service;

import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinder.invoicebindercore.file.FileManager;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.server.serversettings.ServerSettingsManager;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/filedownload")
public class FileDownloadServiceImpl extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file;
        String fileName;
        String path;
        String[] queryParams;
        
        ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "doGet from FileDownloadServiceImpl has been called.");
        
        try {
            ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "Query String: " + req.getQueryString());             
            queryParams = req.getQueryString().split("=");
            if (queryParams.length == 2) {
                fileName = queryParams[1];
                ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "Filename: " + fileName);                  
                path = ServerSettingsManager.ApplicationSettings.getUploadPath();
                ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "Path: " + path);     
                file = FileManager.getFile(fileName, path);               
                ServletContext context = getServletConfig().getServletContext();
                String mimetype = context.getMimeType(file.getName());
                resp.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
                resp.setContentLength((int) file.length());
                
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
                try (java.io.OutputStream out = resp.getOutputStream()) {
                    byte b[] = org.apache.commons.io.FileUtils.readFileToByteArray(file);
                    out.write(b);
                }
            }
            else {
                resp.getOutputStream().print("Error downloading file. Unable to get download filename.");
            }
        }
        catch (IOException e) {
            System.out.println("Unable to download file: " + e);
            ServerLogManager.writeErrorLog(FileDownloadServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, e));            
        }
        
        ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "doGet from FileDownloadServiceImpl call completed.");
    }
}

