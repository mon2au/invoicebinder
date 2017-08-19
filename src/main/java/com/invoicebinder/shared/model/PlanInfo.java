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
public class PlanInfo implements IsSerializable {
    private String planCode;
    private String planName;
    private String planDesc;
    private Boolean isActive;
    private BigDecimal planCost;
    private Integer planInvoiceLimitCount;
    private Integer planClientLimitCount;
    private Boolean allowPayments;
    private Boolean allowInvoiceTemplates;
    private String planHeaderStyleName;
    
    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public BigDecimal getPlanCost() {
        return planCost;
    }

    public void setPlanCost(BigDecimal planCost) {
        this.planCost = planCost;
    }

    public Integer getPlanInvoiceLimitCount() {
        return planInvoiceLimitCount;
    }

    public void setPlanInvoiceLimitCount(Integer planInvoiceLimitCount) {
        this.planInvoiceLimitCount = planInvoiceLimitCount;
    }

    public Integer getPlanClientLimitCount() {
        return planClientLimitCount;
    }

    public void setPlanClientLimitCount(Integer planClientLimitCount) {
        this.planClientLimitCount = planClientLimitCount;
    }

    public Boolean isAllowPayments() {
        return allowPayments;
    }

    public void setAllowPayments(Boolean allowPayments) {
        this.allowPayments = allowPayments;
    }

    public Boolean isAllowInvoiceTemplates() {
        return allowInvoiceTemplates;
    }

    public void setAllowInvoiceTemplates(Boolean allowInvoiceTemplates) {
        this.allowInvoiceTemplates = allowInvoiceTemplates;
    }

    public String getPlanHeaderStyleName() {
        return planHeaderStyleName;
    }

    public void setPlanHeaderStyleName(String planHeaderStyleName) {
        this.planHeaderStyleName = planHeaderStyleName;
    }
}
