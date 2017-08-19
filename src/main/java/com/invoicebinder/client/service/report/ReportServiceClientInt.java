/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.client.service.report;


/**
 *
 * @author msushil
 */
public interface ReportServiceClientInt {
    void getIncomeAndExpense(int startMonth, int startYear, int totalMonths);
    void getSalesAndPayment(int startMonth, int startYear, int totalMonths);    
}
