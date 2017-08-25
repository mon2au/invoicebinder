/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.config;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class SiteTemplateConfig extends Composite {
    
    private static SiteTemplateConfigUiBinder uiBinder = GWT.create(SiteTemplateConfigUiBinder.class);
    
    interface SiteTemplateConfigUiBinder extends UiBinder<Widget, SiteTemplateConfig> {
    }
    
    public SiteTemplateConfig() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
