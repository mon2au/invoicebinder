/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client.ui.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class InstallationSteps extends Composite {
    
    private static InstallationStepsUiBinder uiBinder = GWT.create(InstallationStepsUiBinder.class);
    
    interface InstallationStepsUiBinder extends UiBinder<Widget, InstallationSteps> {
    }
    
    public InstallationSteps() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
