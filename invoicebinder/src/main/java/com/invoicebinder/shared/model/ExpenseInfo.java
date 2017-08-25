/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.model;

import com.invoicebinder.shared.enums.ExpenseType;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author mon2
 */
public class ExpenseInfo implements IsSerializable  {
    private long id;
    private BigDecimal amount;
    private String note;
    private ExpenseType expenseType;
    private Date expenseDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }
}
