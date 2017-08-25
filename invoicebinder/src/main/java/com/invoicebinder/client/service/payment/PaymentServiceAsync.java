/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.payment;

import com.invoicebinder.shared.enums.payment.PaymentStatus;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.PaymentInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public interface PaymentServiceAsync {
    public void getAllPaymentsInfo(int start, int length, ArrayList<GridColSortInfo> sortList,
        String filter, PaymentStatus status,
        AsyncCallback<ArrayList<PaymentInfo>> asyncCallback);    
public void getPaymentsCount(String filter, PaymentStatus status, AsyncCallback<Integer>  callback);    
    public void savePayment(PaymentInfo info, AsyncCallback<Void> asyncCallback);    
}
