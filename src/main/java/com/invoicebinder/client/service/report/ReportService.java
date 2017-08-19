/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.client.service.report;

import com.invoicebinder.shared.model.ReportInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 *
 * @author msushil
 */
@RemoteServiceRelativePath("/invoicebinder/services/report")
public interface ReportService extends RemoteService {
    public List<ReportInfo> getIncomeAndExpense(int startMonth, int startYear, int totalMonths);
    public List<ReportInfo> getSalesAndPayment(int startMonth, int startYear, int totalMonths);
}
