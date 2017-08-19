/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.payment;

import com.invoicebinder.shared.enums.payment.PaymentStatus;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.PaymentInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/payment")
public interface PaymentService extends RemoteService {
    public ArrayList<PaymentInfo> getAllPaymentsInfo(int start, int length,
                                                     ArrayList<GridColSortInfo> sortList, String filter,
                                                     PaymentStatus paymentStatusFilter);
    public int getPaymentsCount(String filter, PaymentStatus status); 
    public void savePayment(PaymentInfo info);    
}
