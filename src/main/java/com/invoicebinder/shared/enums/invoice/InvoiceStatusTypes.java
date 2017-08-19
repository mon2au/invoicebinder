/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.shared.enums.invoice;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public class InvoiceStatusTypes {
    public static ArrayList<String> getUnpaidStatuses() {
        ArrayList<String> viewList = new ArrayList<String>();
        viewList.add(InvoiceStatus.DRAFT.toString());
        viewList.add(InvoiceStatus.SENT.toString());
        return viewList;
    }    
}
