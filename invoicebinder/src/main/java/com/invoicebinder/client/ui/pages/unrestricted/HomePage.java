package com.invoicebinder.client.ui.pages.unrestricted;

import com.invoicebinder.client.service.config.ConfigServiceCallbacks;
import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.config.BusinessConfigItems;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.config.SocialMediaConfigItems;
import com.invoicebinder.shared.model.ConfigData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.HashMap;

public class HomePage extends Composite {
    
    private static final HomePageUiBinder uiBinder = GWT
            .create(HomePageUiBinder.class);
    
    @UiField Anchor facebookUrl;
    @UiField Anchor twitterUrl;
    @UiField Anchor googlePlusUrl;
    @UiField Label lblAddress1;
    @UiField Label lblAddress2;
    @UiField Label lblSuburb;
    @UiField Label lblPhone;
    @UiField Label lblFax;
    @UiField Label lblEmail;
    @UiField Label lblABN;
    @UiField DivElement divMap;
    
    private final ConfigServiceClientImpl configService;
    private final Main main;
    private String mapUrl;
    
    public void setConfigData(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        HashMap<String, String> configData = new HashMap();
        ConfigData data;
        
        if (!arrayList.isEmpty()) {
            for (int i=0; i<arrayList.size(); i++) {
                data = arrayList.get(i);
                configData.put(data.getKey(), data.getValue());
            }
            
            switch (section){
                case SocialMedia: {
                    facebookUrl.setHref(configData.get(SocialMediaConfigItems.FACEBOOKURL.toString()));
                    twitterUrl.setHref(configData.get(SocialMediaConfigItems.TWITTERURL.toString()));
                    googlePlusUrl.setHref(configData.get(SocialMediaConfigItems.GPLUSURL.toString()));
                    break;
                }
                
                case Business: {
                    lblAddress1.setText(configData.get(BusinessConfigItems.BUSINESSADDRESS1.toString()));
                    lblAddress2.setText(configData.get(BusinessConfigItems.BUSINESSADDRESS2.toString()));
                    lblSuburb.setText(configData.get(BusinessConfigItems.BUSINESSCITY.toString()));
                    lblPhone.setText(configData.get(BusinessConfigItems.BUSINESSPHONE.toString()));
                    lblFax.setText(configData.get(BusinessConfigItems.BUSINESSFAX.toString()));
                    lblEmail.setText(configData.get(BusinessConfigItems.BUSINESSEMAIL.toString()));
                    lblABN.setText("Business Number: " + configData.get(BusinessConfigItems.BUSINESSABN.toString()));
                    
                    mapUrl = "<iframe width=\"285\" height=\"175\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" "
                            + "src=\"https://www.google.com.au/maps?t=m&amp;"
                            + "q=" + configData.get(BusinessConfigItems.BUSINESSADDRESS1.toString()) + " " + configData.get(BusinessConfigItems.BUSINESSCITY.toString()) + "&amp;"
                            + "ie=UTF8&amp;hq=&amp;"
                            + "hnear=" + configData.get(BusinessConfigItems.BUSINESSADDRESS1.toString()) + " " + configData.get(BusinessConfigItems.BUSINESSCITY.toString()) + "&amp;"
                            + "output=embed\"></iframe>";
                    divMap.setInnerHTML(mapUrl);
                }
            }
        }
    }
    
    interface HomePageUiBinder extends UiBinder<Widget, HomePage> {
    }
    
    
    public HomePage(Object main) {
        this.main = (Main)main;
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        
        mapUrl = "<iframe width=\"285\" height=\"175\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" "
                + "src=\"https://www.google.com.au/maps?t=m&amp;"
                + "q=Australia&amp;"
                + "ie=UTF8&amp;hq=&amp;"
                + "hnear=Australia&amp;"
                + "output=embed\"></iframe>";
        divMap.setInnerHTML(mapUrl);
        this.configService.loadSocialMediaConfigForHomePage();
        this.configService.loadBusinessConfigData(ConfigServiceCallbacks.BusinessConfigTargetPage.HomePage);
    }
}
