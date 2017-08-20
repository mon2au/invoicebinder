/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.server.service;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.misc.Constants;
import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.shared.UConsts;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author mon2
 */
public class FileUploadServiceImpl extends UploadAction {
    private static final long serialVersionUID = 1L;
    HashMap<String, String> receivedContentTypes = new HashMap();
    /**
     * Maintain a list with received files and their content types.
     */
    HashMap<String, File> receivedFiles = new HashMap();
    
    /**
     * Override executeAction to save the received files in a custom place
     * and delete this items from session.
     * @return
     */
    @Override
    public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
        String response = "";
        ServletContext servletContext = this.getServletContext();
        String appPath = servletContext.getRealPath("/");
        
        for (FileItem item : sessionFiles) {
            if (false == item.isFormField()) {
                try {
                    File tempFile =new File(appPath + "/logo/temp.png");
                    item.write(tempFile);
                    BufferedImage image = ImageIO.read(tempFile);
                    if ((image.getHeight() != Constants.LOGO_IMAGE_HEIGHT) &&
                            (image.getWidth() != Constants.LOGO_IMAGE_WIDTH) ) {
                        response = "Incorrect logo size";
                    }
                    else {
                        File file =new File(appPath + "/logo/logo.png");
                        file.delete();
                        tempFile.renameTo(file);
                        response = "Logo uploaded successfully";
                    }
                    tempFile.delete();
                } catch (Exception e) {
                    ServerLogManager.writeErrorLog(FileUploadServiceImpl.class, getFormattedExceptionMessage(ExceptionType.ServiceException, e));
                    throw new UploadActionException(e);
                }
            }
        }
        return response;
    }
    
    /**
     * Get the content of an uploaded file.
     * @throws java.io.IOException
     */
    @Override
    public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fieldName = request.getParameter(UConsts.PARAM_SHOW);
        File f = receivedFiles.get(fieldName);
        if (f != null) {
            response.setContentType(receivedContentTypes.get(fieldName));
            FileInputStream is = new FileInputStream(f);
            copyFromInputStreamToOutputStream(is, response.getOutputStream());
        } else {
            renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
        }
    }
    
    /**
     * Remove a file when the user sends a delete request.
     */
    @Override
    public void removeItem(HttpServletRequest request, String fieldName)  throws UploadActionException {
        File file = receivedFiles.get(fieldName);
        receivedFiles.remove(fieldName);
        receivedContentTypes.remove(fieldName);
        if (file != null) {
            file.delete();
        }
    }
}
