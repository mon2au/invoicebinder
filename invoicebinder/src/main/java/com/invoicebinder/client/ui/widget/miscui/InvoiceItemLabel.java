/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.ui.widget.miscui;

import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author mon2
 */
public class InvoiceItemLabel extends Label {
    private int rowNumber;
    
    public InvoiceItemLabel(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
