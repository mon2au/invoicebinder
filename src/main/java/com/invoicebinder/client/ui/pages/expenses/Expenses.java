/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.expenses;

import com.invoicebinder.client.service.expense.ExpenseServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author msushil
 */
public class Expenses extends Composite {
    private static final ClientsUiBinder uiBinder = GWT.create(ClientsUiBinder.class);
    private final ExpenseServiceClientImpl expenseService;
    private ExpenseInfo[] selection;
    private final Main main;
    private CellTable<ExpenseInfo> table;
    
    private final ExpensesDataProvider expensesDataProvider;
    private Range expensesDataRange;
    private final ArrayList<GridColSortInfo> gridColSortList; 
    private String expensesFilterText;
    private final VerticalPanel panel;
    private final SimplePager pager;
    private final VerticalPanel gridDataPanel;        
        
    @UiField Button newExpense;
    @UiField Button editExpense;
    @UiField Button deleteExpense;
    @UiField HTMLPanel receiptsContainer;
    @UiField TextBox txtSearch;   
    
    private static final String EXPENSE_NOTE_FILTER_TEXT = "filter by note";    

    public void refresh() {
        table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
    }

    public void updateTableCount(Integer count) {
        expensesDataProvider.updateRowCount(count, true);
    }
    
    interface ClientsUiBinder extends UiBinder<Widget, Expenses> {
    }
    
    public Expenses(Main main) {
        this.main = main;
        this.expenseService = new ExpenseServiceClientImpl(GWT.getModuleBaseURL() + "services/expense", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        expensesDataProvider = new ExpensesDataProvider(this);
        gridColSortList = new ArrayList<GridColSortInfo>();            
        panel = new VerticalPanel();
        gridDataPanel = new VerticalPanel();
        pager = new SimplePager();
        txtSearch.setText(EXPENSE_NOTE_FILTER_TEXT);        
        panel.setWidth("100%");
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        table = createExpensesTable();
        pager.setDisplay(table);
        gridDataPanel.add(table);
        gridDataPanel.setHeight(Constants.STANDARD_GRID_HEIGHT);
        panel.add(gridDataPanel);
        panel.add(pager);
        
        // Add the widgets to the root panel.
        receiptsContainer.add(panel);
        newExpense.addClickHandler(new DefaultClickHandler());
        editExpense.addClickHandler(new DefaultClickHandler());
        deleteExpense.addClickHandler(new DefaultClickHandler());
        txtSearch.addFocusHandler(new SearchFocusHandler());
        txtSearch.addBlurHandler(new SearchLostFocusHandler());
        txtSearch.addKeyUpHandler(new SearchChangeHandler());        
        
        //set defaults
        txtSearch.setText(EXPENSE_NOTE_FILTER_TEXT);        
        newExpense.setStyleName("appbutton-default");
        editExpense.setStyleName("appbutton-default-disabled");
        deleteExpense.setStyleName("appbutton-default-disabled");
        editExpense.setEnabled(false);
        deleteExpense.setEnabled(false);
    }
    
    private CellTable createExpensesTable(){
        table = new CellTable();
        table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);
        
        // Add a text column to show the note.
        TextColumn<ExpenseInfo> note =
                new TextColumn<ExpenseInfo>() {
                    @Override
                    public String getValue(ExpenseInfo object) {
                        return object.getNote();
                    }
                };
        table.addColumn(note, "Note");       
        
        // Add a text column to show the amount.
        TextColumn<ExpenseInfo> amount =
                new TextColumn<ExpenseInfo>() {
                    @Override
                    public String getValue(ExpenseInfo object) {
                        return object.getAmount().toString();
                    }
                };
        table.addColumn(amount, "Amount");
        
        // Add a text column to show the date.
        TextColumn<ExpenseInfo> date =
                new TextColumn<ExpenseInfo>() {
                    @Override
                    public String getValue(ExpenseInfo object) {
                        return object.getExpenseDate().toString();
                    }
                };
        table.addColumn(date, "Expense Date");
               
        // Add a text column to show the type.
        TextColumn<ExpenseInfo> recType
                = new TextColumn<ExpenseInfo>() {
                    @Override
                    public String getValue(ExpenseInfo object) {
                        return object.getExpenseType().toString();
                    }
                };
        table.addColumn(recType, "Type");
               
        expensesDataProvider.addDataDisplay(table);
        
        // Add a selection model to handle user selection.
        MultiSelectionModel<ExpenseInfo> selectionModel;
        selectionModel = new MultiSelectionModel();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new GridSelectionEventHandler());
        table.setWidth(Constants.STANDARD_GRID_WIDTH, true);
        table.setPageSize(Constants.STANDARD_GRID_PAGESIZE);
        table.setEmptyTableWidget(new Label(Constants.EMPTY_DATATABLE_MESSAGE));        
        return table;
    }
    
    private void getAllExpenses() {
        expensesFilterText = txtSearch.getText();
        if (expensesFilterText.equals(EXPENSE_NOTE_FILTER_TEXT)) {
            expensesFilterText = "";
        }
        expenseService.getAllExpenses(
                expensesDataRange.getStart(), 
                expensesDataRange.getLength(), 
                gridColSortList,
                expensesFilterText,
                expensesDataProvider);        
        expenseService.getExpensesCount(expensesFilterText);
    }
  
    private class ExpensesDataProvider extends AsyncDataProvider<ExpenseInfo> {
        private final Expenses pageReference;
        
        public ExpensesDataProvider(Expenses reference) {
            this.pageReference = reference;
        }

        @Override
        protected void onRangeChanged(HasData<ExpenseInfo> display) {
            this.pageReference.expensesDataRange = display.getVisibleRange();
            this.pageReference.getAllExpenses();
        }                   
    }
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">      
    private class DefaultClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == newExpense) {
                History.newItem(Views.newexpense.toString());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == editExpense) {
                if (isDemoApplication()) return;                  
                History.newItem(Views.editexpense.toString() + "/expenseId=" + selection[0].getId());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == deleteExpense) {
                if (isDemoApplication()) return;                   
                deleteReceipts();
            }
        }
        
        private void deleteReceipts() {
            long[] ids = new long[selection.length];
            
            for (int i=0;i<selection.length;i++) {
               ids[i] = selection[i].getId();
            }
            
            expenseService.deleteExpenses(ids);
        } 
    }
    private class GridSelectionEventHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            Set<ExpenseInfo> selected = ((MultiSelectionModel)event.getSource()).getSelectedSet();
            selection = new ExpenseInfo[selected.size()];
            int i = 0;
            
            if (selected.isEmpty()) {
                editExpense.setStyleName("appbutton-default-disabled");
                deleteExpense.setStyleName("appbutton-default-disabled");
                deleteExpense.setEnabled(false); 
                editExpense.setEnabled(false);
            }
            else {
                if (selected.size() == 1) {
                    editExpense.setStyleName("appbutton-default");
                    editExpense.setEnabled(true);
                    deleteExpense.setStyleName("appbutton-default");
                    deleteExpense.setEnabled(true);  
                }
                else {
                    editExpense.setStyleName("appbutton-default-disabled");
                    editExpense.setEnabled(false);
                    deleteExpense.setStyleName("appbutton-default");
                    deleteExpense.setEnabled(true);                    
                }
                
                for (ExpenseInfo info : selected) {
                    selection[i] = info;
                    i++;
                }               
            }
        }
    }
    private class SearchFocusHandler implements FocusHandler {
        @Override
        public void onFocus(FocusEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().equals(EXPENSE_NOTE_FILTER_TEXT)) {
                ((TextBox)sender).setText("");
            }
        }
    }
    private class SearchLostFocusHandler implements BlurHandler {
        @Override
        public void onBlur(BlurEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().isEmpty()) {
                ((TextBox)sender).setText(EXPENSE_NOTE_FILTER_TEXT);
            }
        }
    }    
    private class SearchChangeHandler implements KeyUpHandler {
        @Override
        public void onKeyUp(KeyUpEvent event) {
            getAllExpenses();
        }
    }    
// </editor-fold>
}

