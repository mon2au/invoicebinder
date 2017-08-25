/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.dashboard;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.model.DashStatsInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import java.util.List;

/**
 *
 * @author mon2
 */
public class DashboardServiceClientImpl implements DashboardServiceClientInt {
    private final DashboardServiceAsync service;
    private final Main mainui;
    
    public DashboardServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(DashboardService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;        
    }    
    
    @Override
    public void getIncomeAndExpense(int startMonth, int startYear, int totalMonths) {
        this.service.getIncomeAndExpense(startMonth, startYear, totalMonths, new GetIncomeAndExpenseCallback());
    }

    @Override
    public void getDashboardStats() {
        this.service.getDashboardStats(new GetDashboardStatsCallback());
    }
    
    private class GetIncomeAndExpenseCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
             mainui.updateDashboardIncomeAndExpenseReport((List)result);
        }
    }      
    
    private class GetDashboardStatsCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
             mainui.updateDashboardStats((DashStatsInfo)result);
        }
    }                
}
