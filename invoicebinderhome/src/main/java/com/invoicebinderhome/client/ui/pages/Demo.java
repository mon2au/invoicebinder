/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client.ui.pages;

import com.invoicebinderhome.client.service.config.ConfigServiceClientImpl;
import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.shared.model.DemoConfigInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class Demo extends Composite {
    
    private static final DemoUiBinder uiBinder = GWT.create(DemoUiBinder.class);
    @UiField Label login;
    @UiField Label password;    
    @UiField Anchor demo;
    private final ConfigServiceClientImpl service;   
    private final Index index;    

    public void updateConfigDetails(DemoConfigInfo demoConfigInfo) {
        this.login.setText("Login: " + demoConfigInfo.getDemoLogin());
        this.password.setText("Password: " + demoConfigInfo.getDemoPassword());
        this.demo.setHref(demoConfigInfo.getDemoURL());
    }
    
    interface DemoUiBinder extends UiBinder<Widget, Demo> {
    }
    
    public Demo(Index index) {
        this.index = index;
        this.service = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.index);        
        initWidget(uiBinder.createAndBindUi(this));
        this.demo.setStyleName("demogreenbutton");
        this.demo.setText("Start Demo");        
        Window.scrollTo(0, 0);               
        this.service.getDemoConfig();
    }
}
