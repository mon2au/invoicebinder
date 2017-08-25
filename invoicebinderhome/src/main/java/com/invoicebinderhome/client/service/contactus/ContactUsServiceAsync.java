/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.client.service.contactus;

import com.invoicebinderhome.shared.model.ContactUsInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author mon2
 */
public interface ContactUsServiceAsync {
    public void sendContactUsMessage(ContactUsInfo info, AsyncCallback<Void> asyncCallback);
}
