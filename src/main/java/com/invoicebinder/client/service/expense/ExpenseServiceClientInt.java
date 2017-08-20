package com.invoicebinder.client.service.expense;

import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public interface ExpenseServiceClientInt {
    void getAllExpenses(int start, int length, ArrayList<GridColSortInfo> sortList, String filter, AsyncDataProvider<ExpenseInfo> provider);
    void deleteExpenses(long[] ids);
    void saveExpense(ExpenseInfo info);
    void loadExpense(long expenseId);
    void getExpensesCount(String expenseNoteFilter);    
}
