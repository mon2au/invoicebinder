/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.payment;

import com.invoicebinder.shared.enums.payment.PaymentStatus;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.PaymentInfo;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public interface PaymentServiceClientInt {
    void getAllPayments(int start, int length, ArrayList<GridColSortInfo> sortList, String filter,
                        PaymentStatus status, AsyncDataProvider<PaymentInfo> provider);
    void getPaymentsCount(String filter, PaymentStatus status);    
    void savePayment(PaymentInfo info);   
}
