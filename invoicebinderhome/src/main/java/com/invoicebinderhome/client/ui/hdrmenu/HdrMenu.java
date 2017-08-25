/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinderhome.client.ui.hdrmenu;

import com.invoicebinderhome.client.ui.controller.Views;
import com.invoicebinderhome.shared.enums.Menu;
import com.invoicebinderhome.shared.misc.Constants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HdrMenu extends Composite {
    
    private static final HdrMenuUiBinder uiBinder = GWT.create(HdrMenuUiBinder.class);
    @UiField Anchor home;
    @UiField Anchor tour;
    @UiField Anchor download;
    @UiField Anchor demo;
    @UiField Anchor contactus;
    @UiField Label title;
    @UiField Image logoImage;
    
    public void setActiveMenuItem() {
            removeActiveMenuStyle();         
    }
    
    public void setActiveMenuItem(Menu menu) {
            removeActiveMenuStyle();
            
            switch (menu) {
                case HOME:
                    home.addStyleName("menu-currentitem");            
                    break;
                case TOUR:
                    tour.addStyleName("menu-currentitem");            
                    break;
                case DOWNLOAD:
                    download.addStyleName("menu-currentitem");            
                    break;
                case DEMO:
                    demo.addStyleName("menu-currentitem");
                    break;
                case CONTACTUS:
                    contactus.addStyleName("menu-currentitem");            
                    break;                    
            }            
    }
    
    interface HdrMenuUiBinder extends UiBinder<Widget, HdrMenu> {
    }
    
    public HdrMenu() {
        initWidget(uiBinder.createAndBindUi(this));
        logoImage.setUrl("images/invoicebindersmall.png");
        logoImage.getElement().setAttribute("itemprop","image");
        title.setText(Constants.TITLE);
        title.setStyleName("title");
        title.getElement().setAttribute("itemprop","name");
        home.addStyleName("menu-currentitem");
        createMenu();
    }
    
    private void createMenu() {
        home.setHTML("<a href=\"#\">home</a>");
        tour.setHTML("<a href=\"#\">take a tour</a>");
        download.setHTML("<a href=\"#\">download</a>");
        contactus.setHTML("<a href=\"#\">contact us</a>");
        demo.setHTML("<a href=\"#\">demo</a>");
        
        home.addClickHandler(new DefaultClickHandler());
        tour.addClickHandler(new DefaultClickHandler());
        download.addClickHandler(new DefaultClickHandler());
        contactus.addClickHandler(new DefaultClickHandler());
        demo.addClickHandler(new DefaultClickHandler());
    }
    
    private class DefaultClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == home) {
                History.newItem(Views.home.toString());
                event.getNativeEvent().preventDefault();
            }
            else if(sender == tour) {
                tour.addStyleName("menu-currentitem");
                History.newItem(Views.tour.toString());
                event.getNativeEvent().preventDefault();
            }
            else if(sender == download) {
                download.addStyleName("menu-currentitem");
                History.newItem(Views.download.toString());
                event.getNativeEvent().preventDefault();
            }
            else if(sender == demo) {
                demo.addStyleName("menu-currentitem");
                History.newItem(Views.demo.toString());
                event.getNativeEvent().preventDefault();
            }
            else if(sender == contactus) {
                contactus.addStyleName("menu-currentitem");
                History.newItem(Views.contactus.toString());
                event.getNativeEvent().preventDefault();
            }
        }       
    }
    
    private void removeActiveMenuStyle() {
        home.removeStyleName("menu-currentitem");
        tour.removeStyleName("menu-currentitem");
        download.removeStyleName("menu-currentitem");
        contactus.removeStyleName("menu-currentitem");
        demo.removeStyleName("menu-currentitem");
    }
}

