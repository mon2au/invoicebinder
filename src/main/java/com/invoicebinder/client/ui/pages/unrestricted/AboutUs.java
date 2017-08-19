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
public class AboutUs extends Composite {
    
    private static final AboutUsUiBinder uiBinder = GWT.create(AboutUsUiBinder.class);
    
    interface AboutUsUiBinder extends UiBinder<Widget, AboutUs> {
    }
    
    public AboutUs() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}