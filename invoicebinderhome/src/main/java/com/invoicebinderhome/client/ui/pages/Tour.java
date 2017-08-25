/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinderhome.client.ui.pages;

import com.invoicebinderhome.shared.misc.Constants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class Tour extends Composite {
    
    private static final TourUiBinder uiBinder = GWT.create(TourUiBinder.class);
    @UiField InlineLabel t1;
    
    interface TourUiBinder extends UiBinder<Widget, Tour> {
    }
    
    public Tour() {
        initWidget(uiBinder.createAndBindUi(this));
        Window.scrollTo(0, 0); 
        t1.setText(Constants.TITLE);
    }
}
