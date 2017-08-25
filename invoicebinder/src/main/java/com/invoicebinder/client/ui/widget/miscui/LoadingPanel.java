/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.widget.miscui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class LoadingPanel extends Composite {
    
    private static final LoadingPanelUiBinder uiBinder = GWT.create(LoadingPanelUiBinder.class);
    Timer timer;
    @UiField UListElement progress;
    
    interface LoadingPanelUiBinder extends UiBinder<Widget, LoadingPanel> {
    }
    
    public LoadingPanel() {    
        initWidget(uiBinder.createAndBindUi(this));
        progress.setId("progress");
        Run();
    }
    
    private void Run() {
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
