package com.invoicebinder.client.service.expense;

import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mon2
 */
public interface ExpenseServiceAsync {

    public void getAllExpensesInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String expenseNoteFilter, AsyncCallback<List<ExpenseInfo>> asyncCallback);
    public void deleteExpenses(long[] id, AsyncCallback<Integer> asyncCallback);
    public void saveExpense(ExpenseInfo info, AsyncCallback<Void> asyncCallback);
    public void loadExpense(long clientId, AsyncCallback<ExpenseInfo> asyncCallback);
    public void getExpensesCount(String expenseNoteFilter, AsyncCallback<Integer> expensesCountCallback);    
}
