package com.invoicebinder.client.ui.installation;

import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.misc.Constants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Install extends Composite {
    
    private static final InstallUiBinder uiBinder = GWT.create(InstallUiBinder.class);
    @UiField Label title;
    @UiField Image logoImage;
    @UiField Button nextBtn;
    
    interface InstallUiBinder extends UiBinder<Widget, Install> {
    }
    
    public Install() {    
        initWidget(uiBinder.createAndBindUi(this));
        logoImage.setUrl("images/invoicebindersmall.png");
        title.setText(Constants.TITLE);
        title.setStyleName("installpage-producttitle");
        nextBtn.addClickHandler(new DefaultClickHandler(this));   
        nextBtn.setFocus(true);
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final Install pageReference;
        
        public DefaultClickHandler(Install reference) {
            this.pageReference = reference;
        }
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == nextBtn) {
                History.newItem(Views.installappemail.toString());
                event.getNativeEvent().preventDefault();
            }
        }
    }    
}
