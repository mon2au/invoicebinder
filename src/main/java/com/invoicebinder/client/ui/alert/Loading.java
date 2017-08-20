/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.alert;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class Loading extends DialogBox {
    
    private static final LoadingUiBinder uiBinder = GWT.create(LoadingUiBinder.class);
    @UiField UListElement progress;    
    Timer timer;
    
    interface LoadingUiBinder extends UiBinder<Widget, Loading> {
    }
    
    public Loading() {
        this.add(uiBinder.createAndBindUi(this));
        this.setModal(true);
        this.setGlassEnabled(true);  
        this.setStyleName("loadingDialog");
        this.progress.setId("progress");
        this.hide();
    }
    
     public void runAnimation() {
        timer = new Timer() {
            int interval;
            @Override
            public void run() {
                if (progress.hasAttribute("class")) {
                    interval = 10;
                    progress.removeClassName("running");
                    progress.removeAttribute("class");
                }
                else {
                    progress.addClassName("running");
                    interval = 4000;
                }
                
                timer.schedule(interval);
            }
        };
        timer.schedule(10);
    }
}
