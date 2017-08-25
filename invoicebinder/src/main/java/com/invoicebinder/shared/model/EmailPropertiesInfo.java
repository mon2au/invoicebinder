/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author mon2
 */
public class EmailPropertiesInfo implements IsSerializable {
    private String emailHost;
    private String emailPort;
    private String emailUsername;
    private String emailPassword;
    private String emailFromAddress;
    private String emailContactUsAddress;
    private String emailAuthType;
    private Boolean emailUseSSL;

    public EmailPropertiesInfo(String emailHost, String emailPort, String emailUsername, String emailPassword, 
            String emailFromAddress, String emailContactUsAddress, String emailAuthType, Boolean emailUseSSL) {
        this.emailHost = emailHost;
        this.emailPort = emailPort;
        this.emailUsername = emailUsername;
        this.emailPassword = emailPassword;
        this.emailFromAddress = emailFromAddress;
        this.emailContactUsAddress = emailContactUsAddress;
        this.emailAuthType = emailAuthType;
        this.emailUseSSL = emailUseSSL;
    }

    public EmailPropertiesInfo() {
    }
    
    

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public String getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }

    public String getEmailUsername() {
        return emailUsername;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getEmailFromAddress() {
        return emailFromAddress;
    }

    public void setEmailFromAddress(String emailFromAddress) {
        this.emailFromAddress = emailFromAddress;
    }

    public String getEmailContactUsAddress() {
        return emailContactUsAddress;
    }

    public void setEmailContactUsAddress(String emailContactUsAddress) {
        this.emailContactUsAddress = emailContactUsAddress;
    }

    public String getEmailAuthType() {
        return emailAuthType;
    }

    public void setEmailAuthType(String emailAuthType) {
        this.emailAuthType = emailAuthType;
    }

    public Boolean getEmailSSL() {
        return emailUseSSL;
    }

    public void setEmailSSL(Boolean emailUseSSL) {
        this.emailUseSSL = emailUseSSL;
    }
}
