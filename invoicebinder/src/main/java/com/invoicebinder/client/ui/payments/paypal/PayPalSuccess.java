/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.payments.paypal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class PayPalSuccess extends Composite {
    
    private static PayPalSuccessUiBinder uiBinder = GWT.create(PayPalSuccessUiBinder.class);
    
    interface PayPalSuccessUiBinder extends UiBinder<Widget, PayPalSuccess> {
    }
    
    public PayPalSuccess() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
