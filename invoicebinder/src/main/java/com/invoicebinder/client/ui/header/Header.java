package com.invoicebinder.client.ui.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class Header extends Composite {
    
    private static final HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);
    @UiField HTMLPanel mainPanel;
    
    interface HeaderUiBinder extends UiBinder<Widget, Header> {
    }

    public Header(Boolean visible) {
        initWidget(uiBinder.createAndBindUi(this));
        mainPanel.setVisible(visible);
    }
        
    public Header() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
