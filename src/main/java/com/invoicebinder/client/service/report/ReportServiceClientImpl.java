/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.report;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.report.Report;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import java.util.List;

/**
 *
 * @author msushil
 */
public class ReportServiceClientImpl implements ReportServiceClientInt {
    private final ReportServiceAsync service;
    private final Main mainui;
    
    public ReportServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(ReportService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;        
    }
    
    @Override
    public void getIncomeAndExpense(int startMonth, int startYear, int totalMonths) {
        this.service.getIncomeAndExpense(startMonth, startYear, totalMonths, new GetIncomeAndExpenseCallback());
    }

    @Override
    public void getSalesAndPayment(int startMonth, int startYear, int totalMonths) {
        this.service.getSalesAndPayment(startMonth, startYear, totalMonths, new GetSalesAndPaymentCallback());
    }
   
    private class GetIncomeAndExpenseCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
             mainui.updateReportData((List)result, Report.INCOMEANDEXPENSE);
        }
    }  
    
    private class GetSalesAndPaymentCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
             mainui.updateReportData((List)result, Report.SALESANDPAYMENT);
        }
    }
}
