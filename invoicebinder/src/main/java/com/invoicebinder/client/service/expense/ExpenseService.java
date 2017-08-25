package com.invoicebinder.client.service.expense;

import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/expense")
public interface ExpenseService extends RemoteService {
    public List<ExpenseInfo> getAllExpensesInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String expenseNoteFilter);      
    public int deleteExpenses(long[] id); 
    public void saveExpense(ExpenseInfo info);
    public ExpenseInfo loadExpense(long expenseId);
    public int getExpensesCount(String expenseNoteFilter);     
}
