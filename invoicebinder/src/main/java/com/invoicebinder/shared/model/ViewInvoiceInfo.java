/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.invoicebinder.shared.enums.invoice.InvoiceTemplate;

/**
 *
 * @author mon2
 */
public class ViewInvoiceInfo implements IsSerializable {
    private InvoiceInfo invoiceInfo;
    private InvoiceTemplate invoiceTemplate;   
    private BusinessInfo businessInfo;
    private CustomAttribInfo customAttributes;
    private String currencyCode;
    private String taxLabel;
    private String invoiceEmailMessage;
    
    public ViewInvoiceInfo() {
        this.invoiceInfo = new InvoiceInfo();
        this.businessInfo = new BusinessInfo();   
        this.customAttributes = new CustomAttribInfo();
    }

    public InvoiceInfo getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public InvoiceTemplate getInvoiceTemplate() {
        return invoiceTemplate;
    }

    public void setInvoiceTemplate(InvoiceTemplate invoiceTemplate) {
        this.invoiceTemplate = invoiceTemplate;
    }

    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTaxLabel() {
        return taxLabel;
    }

    public void setTaxLabel(String taxLabel) {
        this.taxLabel = taxLabel;
    }

    public CustomAttribInfo getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(CustomAttribInfo customAttributes) {
        this.customAttributes = customAttributes;
    }

    public String getInvoiceEmailMessage() {
        return invoiceEmailMessage;
    }

    public void setInvoiceEmailMessage(String invoiceEmailMessage) {
        this.invoiceEmailMessage = invoiceEmailMessage;
    }
}
