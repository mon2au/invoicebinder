/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.unrestricted;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class Services extends Composite {
    
    private static ServicesUiBinder uiBinder = GWT.create(ServicesUiBinder.class);
    
    interface ServicesUiBinder extends UiBinder<Widget, Services> {
    }
    
    public Services() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}