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
public class InstallationInfo implements IsSerializable {
    private DBConnectionInfo databaseInfo;
    private EmailPropertiesInfo propertiesInfo;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String confirmPassword;
    private String businessName;
    private String emailAddress;
    private String businessNumber;
    private String businessNumberLabel;
    private String htmlToPdfAppLocation;
    private String currCode;

    public InstallationInfo() {
    }

    public InstallationInfo(DBConnectionInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public DBConnectionInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DBConnectionInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public EmailPropertiesInfo getEmailPropertiesInfo() {
        return propertiesInfo;
    }

    public void setEmailPropertiesInfo(EmailPropertiesInfo propertiesInfo) {
        this.propertiesInfo = propertiesInfo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getBusinessNumberLabel() {
        return businessNumberLabel;
    }

    public void setBusinessNumberLabel(String businessNumberLabel) {
        this.businessNumberLabel = businessNumberLabel;
    }

    public String getHtmlToPdfAppLocation() {
        return htmlToPdfAppLocation;
    }

    public void setHtmlToPdfAppLocation(String htmlToPdfAppLocation) {
        this.htmlToPdfAppLocation = htmlToPdfAppLocation;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }    
}
