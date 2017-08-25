/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.utility;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author mon2
 */
public interface UtilityServiceAsync {
    public void getNextAutoNum(String autoNumId, AsyncCallback<String> asyncCallback);
    public void createPDFFile(String contentHtml, String invoiceNumber,  AsyncCallback<String> asyncCallback);
}
