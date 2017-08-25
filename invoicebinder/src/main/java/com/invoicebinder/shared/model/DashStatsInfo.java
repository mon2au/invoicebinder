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
public class DashStatsInfo  implements IsSerializable  {
    private BigDecimal incomeThisMonth;
    private BigDecimal incomeLastMonth;
    private BigDecimal incomeYTD;
    private BigDecimal expenseThisMonth;
    private BigDecimal expenseLastMonth;
    private BigDecimal expenseYTD;

    public BigDecimal getIncomeThisMonth() {
        return incomeThisMonth;
    }

    public void setIncomeThisMonth(BigDecimal incomeThisMonth) {
        this.incomeThisMonth = incomeThisMonth;
    }

    public BigDecimal getIncomeLastMonth() {
        return incomeLastMonth;
    }

    public void setIncomeLastMonth(BigDecimal incomeLastMonth) {
        this.incomeLastMonth = incomeLastMonth;
    }

    public BigDecimal getIncomeYTD() {
        return incomeYTD;
    }

    public void setIncomeYTD(BigDecimal incomeYTD) {
        this.incomeYTD = incomeYTD;
    }

    public BigDecimal getExpenseThisMonth() {
        return expenseThisMonth;
    }

    public void setExpenseThisMonth(BigDecimal expenseThisMonth) {
        this.expenseThisMonth = expenseThisMonth;
    }

    public BigDecimal getExpenseLastMonth() {
        return expenseLastMonth;
    }

    public void setExpenseLastMonth(BigDecimal expenseLastMonth) {
        this.expenseLastMonth = expenseLastMonth;
    }

    public BigDecimal getExpenseYTD() {
        return expenseYTD;
    }

    public void setExpenseYTD(BigDecimal expenseYTD) {
        this.expenseYTD = expenseYTD;
    }
}
