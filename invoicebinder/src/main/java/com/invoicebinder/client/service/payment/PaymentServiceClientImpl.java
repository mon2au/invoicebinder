/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.service.payment;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.payment.PaymentStatus;
import com.invoicebinder.shared.misc.FieldVerifier;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.PaymentInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public class PaymentServiceClientImpl implements PaymentServiceClientInt {
    private final PaymentServiceAsync service;
    private final Main mainui;
    
    public PaymentServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(PaymentService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }   
    
    @Override
    public void getAllPayments(int start, int length, ArrayList<GridColSortInfo> sortList, String filter, PaymentStatus status, AsyncDataProvider<PaymentInfo> provider) {
        this.service.getAllPaymentsInfo(start, length, sortList, filter, status,
                new GetPaymentListCallback(provider, start));
    }
    
    @Override
    public void getPaymentsCount(String filter, PaymentStatus status) {
        this.service.getPaymentsCount(filter, status, new GetPaymentsCountCallback());
    }
    
    @Override
    public void savePayment(PaymentInfo info) {
        ValidationResult result = new ValidationResult();
        
        if (info.getClientInfo() == null) {
            result.setMessage("Client must be selected.");
            result.setTagname("clientSuggestBox");
            mainui.getContainer().doValidation(Views.newpayment, result);
            return;
        }
        
        if (FieldVerifier.isBlankBigDecimal(info.getAmount())) {
            result.setMessage("Payment amount must be supplied.");
            result.setTagname("txtPaymentAmount");
            mainui.getContainer().doValidation(Views.newpayment, result);
            return;
        }
        
        if (FieldVerifier.isBlankField(info.getCurrencyCode())) {
            result.setMessage("Currency code must be selected.");
            result.setTagname("currencySuggestBox");
            mainui.getContainer().doValidation(Views.newpayment, result);
            return;
        }
   
        if (info.getPaymentDate() == null) {
            result.setMessage("Payment date must be supplied.");
            result.setTagname("txtPaymentDate");
            mainui.getContainer().doValidation(Views.newpayment, result);
            return;
        }        
        
        this.service.savePayment(info, new SavePaymentCallback());
    }    
   
    public class GetPaymentListCallback implements AsyncCallback<ArrayList<PaymentInfo>> {
        private final AsyncDataProvider<PaymentInfo> dataProvider;
        private final int start;
        
        public GetPaymentListCallback(AsyncDataProvider<PaymentInfo> provider, int start){
            this.dataProvider = provider;
            this.start = start;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(ArrayList<PaymentInfo> result) {
            this.dataProvider.updateRowData(start, result);
        }
    }    
    
    private class GetPaymentsCountCallback implements AsyncCallback<Integer> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Integer result) {
            mainui.updatePaymentsCount(result);
        }
    }    
    
    private class SavePaymentCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            History.newItem(Views.payments.toString());
        }
    }      
}
