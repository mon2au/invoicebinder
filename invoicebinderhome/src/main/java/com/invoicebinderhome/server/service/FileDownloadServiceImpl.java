package com.invoicebinderhome.server.service;

import com.invoicebinder.core.email.EmailManager;
import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.core.file.FileManager;
import com.invoicebinderhome.server.logger.ServerLogManager;
import com.invoicebinderhome.server.serversettings.ServerSettingsManager;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinderhome/filedownload")
public class FileDownloadServiceImpl extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file;
        String requestIpAddr;
        String fileName = "invoicebinder.war";
        String path;
        String downloadFileName;
        FileInputStream inputStream = null;
        
        ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "doGet from FileDiwnloadServiceImpl has been called.");
        
        try {
            requestIpAddr = req.getHeader("X-FORWARDED-FOR");
            if (requestIpAddr == null) {
                requestIpAddr = req.getRemoteAddr();
            }

            path = new File(req.getSession().getServletContext().getRealPath("")).getParent() + ServerSettingsManager.URLs.getAppDownloadPath();
            ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "Path: " + path);
            file = FileManager.getFile(fileName, path);
            
            EmailManager.SendEmail(
            ServerSettingsManager.EmailSettings.getEmailHostName(),
            ServerSettingsManager.EmailSettings.getEmailUsername(),
            ServerSettingsManager.EmailSettings.getEmailPassword(),
            ServerSettingsManager.EmailSettings.getEmailPort(),
            ServerSettingsManager.EmailSettings.getEmailFromAddress(),
            ServerSettingsManager.EmailSettings.getEmailContactUsAddress(),
            "", "Downloading Invoice Binder WAR", "Download of Invoice Binder triggered from IP: " + requestIpAddr,
            null, null, ServerSettingsManager.EmailSettings.getIsEmailTransportSecure());  
                    
            if (file != null) {
                ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "File to download is: " + file.getName());
                ServletContext context = getServletConfig().getServletContext();
                String mimetype = context.getMimeType(file.getName());
                resp.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
                resp.setContentLength((int) file.length());
                
                downloadFileName = "invoicebinder" + ServerSettingsManager.BuildInformation.getBuildVersion().replaceAll("\\.", "_") + ".war";
                ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "Downloadable filename is: " + downloadFileName);
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFileName + "\"");
                resp.setHeader("Content-Length", String.valueOf(file.length()));
                ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "File length in bytes is: " + String.valueOf(file.length()));
                
                try
                {
                    ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "Reading file and setting up output stream");
                    inputStream = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        resp.getOutputStream().write(buffer, 0, bytesRead);
                    }
                    
                    resp.getOutputStream().flush();
                }
                finally
                {
                    if(inputStream != null)
                        inputStream.close();
                }
                incrementDownloadCount();
            }
            else {
                ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "Cannot find the file to download.");
            }          
        }
        catch (IOException | MessagingException e) {
            ServerLogManager.writeErrorLog(FileDownloadServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, e));
        }
        
        ServerLogManager.writeInformationLog(FileDownloadServiceImpl.class, "doGet from FileDiwnloadServiceImpl call completed.");
    }
    
    private void incrementDownloadCount() {
        int count;
        
        String countText = ServerSettingsManager.AppConfiguration.getDownloadCount();
        count = Integer.parseInt(countText);
        count++;              
        ServerSettingsManager.AppConfiguration.setDownloadCount(String.valueOf(count));
    }
}

