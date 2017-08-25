/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client.ui.footer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Footer extends Composite {
    
    private static final FooterUiBinder uiBinder = GWT.create(FooterUiBinder.class);
    
    interface FooterUiBinder extends UiBinder<Widget, Footer> {
    }
    
    public Footer() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
