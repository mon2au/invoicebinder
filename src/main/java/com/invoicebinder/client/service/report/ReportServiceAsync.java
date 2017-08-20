/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.report;

import com.invoicebinder.shared.model.ReportInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

/**
 *
 * @author mon2
 */
public interface ReportServiceAsync {
    public void getIncomeAndExpense(int startMonth, int startYear, int totalMonths, AsyncCallback<List<ReportInfo>> asyncCallback);    
    public void getSalesAndPayment(int startMonth, int startYear, int totalMonths, AsyncCallback<List<ReportInfo>> asyncCallback);
}
