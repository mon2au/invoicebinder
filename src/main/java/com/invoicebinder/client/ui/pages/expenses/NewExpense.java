/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.expenses;

import com.invoicebinder.client.service.expense.ExpenseServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.enums.ExpenseType;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.misc.Utils;
import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import java.math.BigDecimal;

/**
 *
 * @author mon2
 */
public class NewExpense extends Composite {
    
    private static final NewClientUiBinder uiBinder = GWT.create(NewClientUiBinder.class);
    @UiField HTMLPanel newReceiptPanel;
    private final Button btnSave;
    private final Button btnCancel;
    private TextBox txtAmount;
    private TextBox txtNote;
    private final ListBox lstReceiptType;

    private final HorizontalPanel buttonPanel;
    private final VerticalPanel mainPanel;
    private final Label lblHeader;
    private final Label lblReceiptDetailsHeader;    
    private final DateBox expenseDate;
    private final ExpenseServiceClientImpl expenseService;
    private final Main main;
    private int expenseId = 0;
    
    interface NewClientUiBinder extends UiBinder<Widget, NewExpense> {
    }
            
    public NewExpense(Object main) {
        this.main = (Main)main;
        this.expenseService = new ExpenseServiceClientImpl(GWT.getModuleBaseURL() + "services/expense", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        this.mainPanel = new VerticalPanel();
        this.buttonPanel = new HorizontalPanel();
        this.lblHeader = new Label();
        this.lblReceiptDetailsHeader = new Label();
        this.btnSave = new Button();
        this.btnCancel = new Button();        
        this.lstReceiptType = new ListBox();
        this.expenseDate = new DateBox();
        if (Utils.getParamFromHref("expenseId") != null) this.expenseId = Integer.parseInt(Utils.getParamFromHref("expenseId"));

        for (ExpenseType type : ExpenseType.values()) {
            lstReceiptType.addItem(type.name(), String.valueOf(type.ordinal()));                
        }
               
        //buttons
        btnSave.setStyleName("appbutton-default");
        btnCancel.setStyleName("appbutton-default");
        btnSave.setText("Save");
        btnCancel.setText("Cancel");         
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        lblHeader.getElement().setInnerHTML("<h5><span>New Expense</span></h5>");
        lblReceiptDetailsHeader.getElement().setInnerHTML("<h6><span>Expense Details</span></h6>");
        mainPanel.add(lblHeader);
        mainPanel.add(lblReceiptDetailsHeader);
        mainPanel.add(getExpenseTable());
        mainPanel.add(buttonPanel);
        lstReceiptType.setStyleName("list-box");
        mainPanel.setStyleName("container");
        newReceiptPanel.add(mainPanel);
        
        btnSave.addClickHandler(new DefaultClickHandler(this));
        btnCancel.addClickHandler(new DefaultClickHandler(this));
        
        if (expenseId != 0) {
            loadExpense(expenseId);
        }
    }  
    
    public void setValidationResult(ValidationResult validation) {
        Element element;
        
        element = DOM.getElementById(validation.getTagname());
        element.focus();
        ValidationPopup.Show(validation.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }    
    
    private FlexTable getExpenseTable() {
        FlexTable table = new FlexTable();
        txtAmount = new TextBox();
        txtNote = new TextBox();
           
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setHTML(0, 0, "");
        //receipt information
        expenseDate.getElement().setId("expenseDate");
        txtAmount.getElement().setId("expenseAmount");
        txtNote.getElement().setId("expenseDesc");
        table.setHTML(1, 0, "Amount:");
        table.setWidget(1, 1, txtAmount);
        expenseDate.getDatePicker().setSize("200px", "200px");        
        expenseDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_LONG)));
        table.setHTML(2,0, "Expense Date");
        table.setWidget(2, 1, expenseDate);
        table.setHTML(3, 0, "Description:");
        table.setWidget(3, 1, txtNote);
        table.setHTML(4, 0, "Expense Type:");
        table.setWidget(4, 1, lstReceiptType);
               
        return table;
    }
       
    private void saveExpense() {
   	ExpenseInfo info = new ExpenseInfo();
        if (this.expenseId != 0) {
            info.setId((long)this.expenseId);
        }
        if (!txtAmount.getText().isEmpty()) info.setAmount(new BigDecimal(txtAmount.getText()));
        info.setNote(txtNote.getText());
        info.setExpenseType(ExpenseType.valueOf(lstReceiptType.getItemText((lstReceiptType.getSelectedIndex()))));
        info.setExpenseDate(expenseDate.getValue());
        expenseService.saveExpense(info);
    }
    
    private void loadExpense(int expenseId) {
        expenseService.loadExpense(expenseId);
    }
    
    public void populateExpenseInfo(ExpenseInfo info) {
        this.txtAmount.setText(info.getAmount().toString());
        this.txtNote.setText(info.getNote());
        this.lstReceiptType.setItemText(0, info.getExpenseType().toString());
        this.expenseDate.setValue(info.getExpenseDate());
    }
   
    private class DefaultClickHandler implements ClickHandler {
        private final NewExpense pageReference;
        
        public DefaultClickHandler(NewExpense reference) {
            this.pageReference = reference;
        }
                
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                this.pageReference.saveExpense();        
            } 
            else if (sender == btnCancel) {          
                History.newItem(Views.expenses.toString());
                event.getNativeEvent().preventDefault();
            }
        }
    }
}