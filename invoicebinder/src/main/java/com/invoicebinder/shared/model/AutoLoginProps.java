package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.math.BigDecimal;

/**
 * Created by mon2 on 8/07/17.
 */
public class AutoLoginProps implements IsSerializable {
    private String email;
    private String invoiceAuthToken;
    private String invoiceNumber;
    private BigDecimal invoiceAmount;

    public AutoLoginProps() {
    }

    public AutoLoginProps(String email, String invoiceAuthToken, String invoiceNumber) {
        this.email = email;
        this.invoiceAuthToken = invoiceAuthToken;
        this.invoiceNumber = invoiceNumber;
    }

    public AutoLoginProps(String invoiceAuthToken, String invoiceNumber, BigDecimal invoiceAmount) {
        this.invoiceAuthToken = invoiceAuthToken;
        this.invoiceNumber = invoiceNumber;
        this.invoiceAmount = invoiceAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvoiceAuthToken() {
        return invoiceAuthToken;
    }

    public void setInvoiceAuthToken(String invoiceAuthToken) {
        this.invoiceAuthToken = invoiceAuthToken;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}
