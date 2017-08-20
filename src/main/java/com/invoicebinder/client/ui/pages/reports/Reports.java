/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.reports;

import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.report.Report;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

/**
 *
 * @author mon2
 */
public class Reports extends Composite {
    
    private static final ReportsUiBinder uiBinder = GWT.create(ReportsUiBinder.class);
    @UiField HTMLPanel reportPanel;
    @UiField HTMLPanel reportPagePanel;
    private final Main main;
    private IncomeAndExpense rptIncomeExpense;
    private PopularProducts rptPopularProducts;
    private SalesAndPayment rptSalesAndPayment;
    
    public void setReportData(List list, Report report) {
        switch (report) {
            case INCOMEANDEXPENSE: rptIncomeExpense.setReportData(list);
                break;
            case POPULARPRODUCTS: rptPopularProducts.setReportData(list);
                break;
            case SALESANDPAYMENT: rptSalesAndPayment.setReportData(list);
                break;                
        }
    }
    
    interface ReportsUiBinder extends UiBinder<Widget, Reports> {
    }
    
    public Reports(Main main) {
        this.main = main;
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    public void showIncomeAndExpense() {
        rptIncomeExpense = new IncomeAndExpense(this.main);     
        reportPagePanel.clear();
        reportPagePanel.add(rptIncomeExpense);
    }

    public void showPopularProducts() {
        rptPopularProducts = new PopularProducts(this.main);        
        reportPagePanel.clear();        
        reportPagePanel.add(rptPopularProducts);
    }
    
    public void showSalesAndPayment() {
        rptSalesAndPayment = new SalesAndPayment(this.main);        
        reportPagePanel.clear();        
        reportPagePanel.add(rptSalesAndPayment);
    }
}
