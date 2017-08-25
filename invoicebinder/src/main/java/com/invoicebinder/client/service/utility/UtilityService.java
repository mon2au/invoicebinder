/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.utility;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/utility")
public interface UtilityService extends RemoteService {
    public String getNextAutoNum(String autoNumId);    
    public String createPDFFile(String contentHtml, String invoiceNumber);
}
