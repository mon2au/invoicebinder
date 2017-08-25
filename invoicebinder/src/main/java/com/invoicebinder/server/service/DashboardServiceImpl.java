/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.server.service;

import com.invoicebinder.client.service.dashboard.DashboardService;
import com.invoicebinder.server.dataaccess.ExpenseDAO;
import com.invoicebinder.server.dataaccess.InvoiceDAO;
import com.invoicebinder.server.dataaccess.ReportDAO;
import com.invoicebinder.shared.model.DashStatsInfo;
import com.invoicebinder.shared.model.ReportInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("dash")
public class DashboardServiceImpl extends RemoteServiceServlet implements DashboardService {

    @Autowired
    private ReportDAO reportDAO;     
    @Autowired
    private InvoiceDAO invoiceDAO;    
    @Autowired
    private ExpenseDAO expenseDAO;
    
    @Override
    public List<ReportInfo> getIncomeAndExpense(int startMonth, int startYear, int totalMonths) {
        List resultSet;
        List<ReportInfo> reportData = new ArrayList<ReportInfo>();
        ReportInfo info;
        
        resultSet = reportDAO.getIncomeAndExpense(startMonth, startYear, totalMonths);
        for (Iterator it = resultSet.iterator(); it.hasNext();) {
            Object[] item = (Object[]) it.next();

            info = new ReportInfo();
            info.setField1(item[0].toString());
            info.setField2(item[1].toString());
            info.setMonth(item[2].toString());
            info.setYear(item[3].toString());     
            reportData.add(info);
        }
        return reportData;       
    }

    @Override
    public DashStatsInfo getDashboardStats() {
        DashStatsInfo info = new DashStatsInfo();
        info.setIncomeYTD(invoiceDAO.getIncomeYTD());
        info.setIncomeLastMonth(invoiceDAO.getIncomeLastMonth());
        info.setIncomeThisMonth(invoiceDAO.getIncomeThisMonth());
        info.setExpenseYTD(expenseDAO.getExpenseYTD());
        info.setExpenseLastMonth(expenseDAO.getExpeseLastMonth());
        info.setExpenseThisMonth(expenseDAO.getExpenseThisMonth());
        return info;       
    }
}
