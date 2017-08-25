/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinderhome.client.ui.pages;

import com.invoicebinderhome.client.service.download.DownloadServiceClientImpl;
import com.invoicebinderhome.client.ui.controller.Index;
import com.invoicebinderhome.shared.misc.Constants;
import com.invoicebinderhome.shared.model.DownloadInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class HomePage extends Composite {
    
    private static final HomePageUiBinder uiBinder = GWT.create(HomePageUiBinder.class);
    @UiField InlineLabel t0;
    @UiField InlineLabel t1;
    @UiField InlineLabel t2;
    @UiField InlineLabel t3;
    @UiField InlineLabel versionInfo;
    @UiField InlineLabel datePublished;
    @UiField InlineLabel downloadCount;
    private final DownloadServiceClientImpl downloadService;  
    
    public HomePage(Index index) {
        initWidget(uiBinder.createAndBindUi(this));
        this.downloadService = new DownloadServiceClientImpl(GWT.getModuleBaseURL() + "services/download", index);    
        t0.setText(Constants.TITLE);
        t1.setText(Constants.TITLE);
        t2.setText(Constants.TITLE);
        t3.setText(Constants.TITLE);
        downloadService.getDownloadInfo(this);
    }

    public void setDownloadInfo(DownloadInfo result) {
        versionInfo.getElement().setAttribute("itemprop","softwareVersion");
        datePublished.getElement().setAttribute("itemprop","datePublished");
        versionInfo.setText("Current Version: " + result.getDownloadVersion());
        datePublished.setText(" Build On: " + result.getBuildDate());
        downloadCount.setText(result.getDownloadCount());
    }
    
    interface HomePageUiBinder extends UiBinder<Widget, HomePage> {
    }    
}
