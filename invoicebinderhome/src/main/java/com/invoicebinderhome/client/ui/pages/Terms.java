/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client.ui.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class Terms extends Composite {
    
    private static final TermsUiBinder uiBinder = GWT.create(TermsUiBinder.class);
    
    interface TermsUiBinder extends UiBinder<Widget, Terms> {
    }
    
    public Terms() {
        initWidget(uiBinder.createAndBindUi(this));
        Window.scrollTo(0, 0);               
    }
}
