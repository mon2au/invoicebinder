/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.client.service.contactus;

import com.invoicebinderhome.client.ui.alert.Loading;
import com.invoicebinderhome.shared.model.ContactUsInfo;

/**
 *
 * @author mon2
 */
public interface ContactUsServiceClientInt {
    void sendContactUsMessage(ContactUsInfo info, Loading loading);
}
