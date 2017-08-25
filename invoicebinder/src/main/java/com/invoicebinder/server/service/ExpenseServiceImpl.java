/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.service;

import com.invoicebinder.client.service.expense.ExpenseService;
import com.invoicebinder.server.dataaccess.ExpenseDAO;
import com.invoicebinder.shared.entity.expense.Expense;
import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
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
@Service("expense")
public class ExpenseServiceImpl extends RemoteServiceServlet implements
        ExpenseService {
    
    @Autowired
    private ExpenseDAO expenseDAO;
    
    @Override
    public List<ExpenseInfo> getAllExpensesInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String expenseNoteFilter) {
        ArrayList<ExpenseInfo> allReceiptsInfo = new ArrayList<ExpenseInfo>();
        ArrayList<Expense> expenseList;
        ExpenseInfo info;
        
        expenseList = expenseDAO.getAllExpensesInfo(start, length, sortList, expenseNoteFilter);
        
        for(Expense expense : expenseList) {
            info = new ExpenseInfo();
            info.setId(expense.getId());
            info.setAmount(expense.getAmount());
            info.setNote(expense.getNote());
            info.setExpenseType(expense.getExpenseType());
            info.setExpenseDate(expense.getExpenseDate());
            allReceiptsInfo.add(info);
        }
        return allReceiptsInfo;
    }

    @Override
    public int deleteExpenses(long[] ids) {
        return expenseDAO.deleteExpense(ids);
    }

    @Override
    public void saveExpense(ExpenseInfo info) {
        expenseDAO.saveExpense(info);
    }

    @Override
    public ExpenseInfo loadExpense(long expenseId) {
        return expenseDAO.loadExpense(expenseId);
    }
    
    @Override
    public int getExpensesCount(String expenseNoteFilter) {
        return expenseDAO.getExpenseCount(expenseNoteFilter);
    }    
}
