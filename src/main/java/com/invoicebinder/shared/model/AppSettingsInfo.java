/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.shared.model;

import com.invoicebinder.shared.enums.startup.HomePageSettings;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author msushil
 */
public class AppSettingsInfo implements IsSerializable {   
    private String appTitle;
    private String appSlogan;
    private HomePageSettings appLandingPage;
    private Boolean isAppInstalled;

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppSlogan() {
        return appSlogan;
    }

    public void setAppSlogan(String appSlogan) {
        this.appSlogan = appSlogan;
    }

    public HomePageSettings getAppLandingPage() {
        return appLandingPage;
    }

    public void setAppLandingPage(HomePageSettings appLandingPage) {
        this.appLandingPage = appLandingPage;
    }

    public Boolean getIsAppInstalled() {
        return isAppInstalled;
    }

    public void setIsAppInstalled(Boolean isAppInstalled) {
        this.isAppInstalled = isAppInstalled;
    }
}
