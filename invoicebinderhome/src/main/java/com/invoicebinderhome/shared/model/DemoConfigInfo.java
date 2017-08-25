/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinderhome.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author mon2
 */
public class DemoConfigInfo implements IsSerializable {
    private String demoLogin;
    private String demoPassword;
    private String demoURL;

    public String getDemoLogin() {
        return demoLogin;
    }

    public void setDemoLogin(String demoLogin) {
        this.demoLogin = demoLogin;
    }

    public String getDemoPassword() {
        return demoPassword;
    }

    public void setDemoPassword(String demoPassword) {
        this.demoPassword = demoPassword;
    }

    public String getDemoURL() {
        return demoURL;
    }

    public void setDemoURL(String demoURL) {
        this.demoURL = demoURL;
    }
}
