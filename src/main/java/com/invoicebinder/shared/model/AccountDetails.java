/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;

/**
 *
 * @author mon2
 */
public class AccountDetails implements IsSerializable {
    private Long id;
    private String accountName;
    private String accountStartDate;
    private String planCode;
    private String planName;
    private String planStartDate;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private BigDecimal planCost;
    private Integer planClientCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public String getAccountStartDate() {
        return accountStartDate;
    }

    public void setAccountStartDate(String accountStartDate) {
        this.accountStartDate = accountStartDate;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getPlanCost() {
        return planCost;
    }

    public void setPlanCost(BigDecimal planCost) {
        this.planCost = planCost;
    }

    public Integer getPlanClientCount() {
        return planClientCount;
    }

    public void setPlanClientCount(Integer planClientCount) {
        this.planClientCount = planClientCount;
    }
}
