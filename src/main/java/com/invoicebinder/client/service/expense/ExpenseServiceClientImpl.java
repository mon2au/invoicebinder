package com.invoicebinder.client.service.expense;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msushil
 */
public class ExpenseServiceClientImpl implements  ExpenseServiceClientInt {
    private final ExpenseServiceAsync service;
    private final Main mainui;
    
    public ExpenseServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(ExpenseService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }
    
    @Override
    public void getAllExpenses(int start, int length, ArrayList<GridColSortInfo> sortList, String expenseNoteFilter, AsyncDataProvider<ExpenseInfo> provider) {
        this.service.getAllExpensesInfo(start, length, sortList, expenseNoteFilter, new GetExpenseListCallback(provider, start));
    }  

    @Override
    public void deleteExpenses(long[] ids) {
         this.service.deleteExpenses(ids, new DeleteExpenseCallback());
    }  

    @Override
    public void saveExpense(ExpenseInfo info) {
        ValidationResult result = new ValidationResult();
        
        if (info.getAmount() == null) {
            result.setMessage("Expense amount must be supplied.");
            result.setTagname("expenseAmount");
            mainui.getContainer().doValidation(Views.newexpense, result);
            return;
        }        
        
        if (info.getExpenseDate() == null) {
            result.setMessage("Expense date must be supplied.");
            result.setTagname("expenseDate");
            mainui.getContainer().doValidation(Views.newexpense, result);
            return;
        }        
        
        if (info.getNote().isEmpty()) {
            result.setMessage("Expense description must be supplied.");
            result.setTagname("expenseDesc");
            mainui.getContainer().doValidation(Views.newexpense, result);
            return;
        }   
        
        this.service.saveExpense(info, new SaveExpenseCallback());
    }

    @Override
    public void loadExpense(long expenseId) {
        this.service.loadExpense(expenseId, new LoadExpenseCallback());
    }

    @Override
    public void getExpensesCount(String expenseNoteFilter) {
        this.service.getExpensesCount(expenseNoteFilter, new GetExpensesCountCallback());
    }

    private class LoadExpenseCallback implements AsyncCallback<ExpenseInfo> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }

        @Override
        public void onSuccess(ExpenseInfo result) {
            mainui.getContainer().loadExpense(result);
        }
    }
    
    private class DeleteExpenseCallback implements AsyncCallback<Integer> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Integer result) {
            if (result == 0) {
                mainui.getContainer().refreshExpenses();
            } else {
            Alert.show("unable to delete.", AlertLevel.ERROR);                
            }
        }
    }

    private class GetExpenseListCallback implements AsyncCallback<List<ExpenseInfo>> {
        private AsyncDataProvider<ExpenseInfo> dataProvider;
        private int start;
        
        public GetExpenseListCallback(AsyncDataProvider<ExpenseInfo> provider, int start){
            this.dataProvider = provider;
            this.start = start;
        }
                
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(List<ExpenseInfo> result) {
            this.dataProvider.updateRowData(start,result);
        }
    }

    private class SaveExpenseCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
             History.newItem(Views.expenses.toString());
        }
    }
    
    private class GetExpensesCountCallback implements AsyncCallback<Integer> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Integer result) {
             mainui.updateExpensesCount(result);
        }
    }       
}
