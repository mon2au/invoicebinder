/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.widget.miscui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class ReportMonthSelectorWidget extends Composite {
    
    private static ReportMonthSelectorWidgetUiBinder uiBinder = GWT.create(ReportMonthSelectorWidgetUiBinder.class);
    
    interface ReportMonthSelectorWidgetUiBinder extends UiBinder<Widget, ReportMonthSelectorWidget> {
    }
    
    public ReportMonthSelectorWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
