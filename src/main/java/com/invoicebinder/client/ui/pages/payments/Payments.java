/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.payments;

import com.invoicebinder.client.service.payment.PaymentServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.payment.PaymentStatus;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.PaymentInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
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
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import java.util.ArrayList;
import com.google.gwt.view.client.SelectionChangeEvent;

import java.util.Set;

/**
 *
 * @author mon2
 */
public class Payments extends Composite {
    
    private static final PaymentsUiBinder uiBinder = GWT.create(PaymentsUiBinder.class);
    private final PaymentServiceClientImpl paymentService;
    private PaymentInfo[] selection;
    private final Main main;
    private CellTable<PaymentInfo> table;
    private final VerticalPanel panel;
    private final VerticalPanel gridDataPanel;    
    private final SimplePager pager;
    private final PaymentsDataProvider dataProvider;
    private Range range;
    private final ArrayList<GridColSortInfo> gridColSortList;
    private final ColumnSortList sortList;
    private String filterText;
    private ColumnSortInfo item;
    private GridColSortInfo sortItem;
    private PaymentStatus status;
    
    @UiField HTMLPanel paymentsPanel;
    @UiField TextBox txtSearch; 
    @UiField Button btnNewPayment;    
    @UiField ToggleButton toggleAll;
    @UiField ToggleButton toggleDeclined; 
    @UiField ToggleButton togglePaid;
    
    private static final String PAYMENT_FILTER_TEXT = "filter payment";    

    public void updateTableCount(int count) {
        dataProvider.updateRowCount(count, true);
    }
    
    interface PaymentsUiBinder extends UiBinder<Widget, Payments> {
    }
    
    public Payments(Main main) {
        this.main = main;
        this.paymentService = new PaymentServiceClientImpl(GWT.getModuleBaseURL() + "services/payment", this.main);        
        initWidget(uiBinder.createAndBindUi(this));
        this.setPaymentStatusToggle(PaymentStatus.PAID);
        dataProvider = new PaymentsDataProvider(this);
        gridColSortList = new ArrayList<>();
        panel = new VerticalPanel();
        gridDataPanel = new VerticalPanel();        
        pager = new SimplePager();
        table = createPaymentsTable();
        sortList = table.getColumnSortList();
        
        panel.setWidth("100%");
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        txtSearch.setText(PAYMENT_FILTER_TEXT);
        pager.setDisplay(table);
        gridDataPanel.add(table);
        gridDataPanel.setHeight(Constants.STANDARD_GRID_HEIGHT);
        panel.add(gridDataPanel);
        panel.add(pager);
        
        // Add the widgets to the root panel.
        paymentsPanel.add(panel);        
        toggleAll.addClickHandler(new DefaultClickHandler(this));
        togglePaid.addClickHandler(new DefaultClickHandler(this));
        toggleDeclined.addClickHandler(new DefaultClickHandler(this));
        btnNewPayment.addClickHandler(new DefaultClickHandler(this));
        
        txtSearch.addFocusHandler(new SearchFocusHandler());
        txtSearch.addBlurHandler(new SearchLostFocusHandler());
        txtSearch.addKeyUpHandler(new SearchChangeHandler());
        
        btnNewPayment.setStyleName("appbutton-default");
        
        //create sort list.
        for(int i=0;i<sortList.size();i++) {
            item = sortList.get(i);
            sortItem = new GridColSortInfo(item.getColumn().getDataStoreName(), item.isAscending());
            gridColSortList.add(sortItem);
        }
        //call remote service to fetch the data.
        getAllPayments();        
    }
    
    private void setPaymentStatusToggle(PaymentStatus status) {
        if (status == PaymentStatus.ALL) {
            this.status = PaymentStatus.ALL;
            this.toggleAll.setDown(true);
            this.toggleDeclined.setDown(false);
            this.togglePaid.setDown(false);
            this.toggleAll.setStyleName("appbutton-default-toggle");
            this.toggleDeclined.setStyleName("appbutton-default");
            this.togglePaid.setStyleName("appbutton-default");
        }
        if (status == PaymentStatus.PAID) {
            this.status = PaymentStatus.PAID;
            this.toggleAll.setDown(false);
            this.togglePaid.setDown(true);
            this.toggleDeclined.setDown(false);
            this.toggleAll.setStyleName("appbutton-default");
            this.togglePaid.setStyleName("appbutton-default-toggle");
            this.toggleDeclined.setStyleName("appbutton-default");            
        }
        if (status == PaymentStatus.DECLINED) {
            this.status = PaymentStatus.DECLINED;
            this.toggleAll.setDown(false);
            this.togglePaid.setDown(false);
            this.toggleDeclined.setDown(true);
            this.toggleAll.setStyleName("appbutton-default");
            this.togglePaid.setStyleName("appbutton-default");
            this.toggleDeclined.setStyleName("appbutton-default-toggle");            
        }
    }    
    
    private CellTable createPaymentsTable(){
        table = new CellTable();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED);
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);
        
        
        TextColumn<PaymentInfo> id =
                new TextColumn<PaymentInfo>() {
                    @Override
                    public String getValue(PaymentInfo object) {
                        return String.valueOf(object.getId());
                    }
                };
        
        table.setColumnWidth(id, 100, Style.Unit.PX);
        table.addColumn(id, "ID");
        
        // Add a text column to show the lastname.
        TextColumn<PaymentInfo> amount =
                new TextColumn<PaymentInfo>() {
                    @Override
                    public String getValue(PaymentInfo object) {
                        return String.valueOf(object.getAmount());
                    }
                };
        amount.setSortable(true);
        table.setColumnWidth(amount, 100, Style.Unit.PX);
        table.addColumn(amount, "Amount");

        TextColumn<PaymentInfo> paymentDate =
                new TextColumn<PaymentInfo>() {
                    @Override
                    public String getValue(PaymentInfo object) {
                        return String.valueOf(object.getPaymentDate());
                    }
                };
        paymentDate.setSortable(true);
        table.setColumnWidth(paymentDate, 240, Style.Unit.PX);
        table.addColumn(paymentDate, "Payment Date");
        

        TextColumn<PaymentInfo> paymentType =
                new TextColumn<PaymentInfo>() {
                    @Override
                    public String getValue(PaymentInfo object) {
                        return object.getPaymentType();
                    }
                };
        paymentType.setSortable(true);
        table.addColumn(paymentType, "Payment Type");

        TextColumn<PaymentInfo> clientName
                = new TextColumn<PaymentInfo>() {
                    @Override
                    public String getValue(PaymentInfo object) {
                        return object.getClientInfo().getFullName();
                    }
                };
        table.addColumn(clientName, "Client Name");
        table.setColumnWidth(clientName, 250, Style.Unit.PX);
        
        TextColumn<PaymentInfo> refNo
                = new TextColumn<PaymentInfo>() {
                    @Override
                    public String getValue(PaymentInfo object) {
                        return object.getReferenceNo();
                    }
                };
        table.addColumn(refNo, "Reference No");
        table.setColumnWidth(refNo, 250, Style.Unit.PX);
        
        TextColumn<PaymentInfo> paymentStatus
                = new TextColumn<PaymentInfo>() {
                    @Override
                    public String getValue(PaymentInfo object) {
                        return object.getPaymentStatus();
                    }
                };
        table.addColumn(paymentStatus, "Payment Status");
        table.setColumnWidth(paymentStatus, 250, Style.Unit.PX);

        dataProvider.addDataDisplay(table);
        
        //column sort handler
        ColumnSortEvent.AsyncHandler columnSortHandler = new ColumnSortEvent.AsyncHandler(table);
        table.addColumnSortHandler(columnSortHandler);
        
        // We know that the data is sorted alphabetically by default.
        //table.getColumnSortList().push(firstName);
        
        // Add a selection model to handle user selection.
        MultiSelectionModel<PaymentInfo> selectionModel;
        selectionModel = new MultiSelectionModel();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new GridSelectionEventHandler());
        table.setWidth(Constants.STANDARD_GRID_WIDTH, true);
        table.setPageSize(Constants.STANDARD_GRID_PAGESIZE);
        table.setEmptyTableWidget(new Label(Constants.EMPTY_DATATABLE_MESSAGE));        
        return table;
    }    
    
    private void getAllPayments() {
        filterText = txtSearch.getText();
        if (filterText.equals(PAYMENT_FILTER_TEXT)) {
            filterText = "";
        }
        paymentService.getAllPayments(
                range.getStart(),
                range.getLength(),
                gridColSortList,
                filterText,
                status,
                dataProvider);
        paymentService.getPaymentsCount(filterText, status);
    }

    private class PaymentsDataProvider extends AsyncDataProvider<PaymentInfo> {
        private final Payments pageReference;
        
        
        public PaymentsDataProvider(Payments reference) {
            this.pageReference = reference;
        }
        
        @Override
        protected void onRangeChanged(HasData<PaymentInfo> display) {
            this.pageReference.range = display.getVisibleRange();
            this.pageReference.getAllPayments();
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    //grid multiselection model.
    private class GridSelectionEventHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            Set<PaymentInfo> selected = ((MultiSelectionModel)event.getSource()).getSelectedSet();
            selection = new PaymentInfo[selected.size()];
            int i = 0;
            
            if (selected.isEmpty()) {
                //editClient.setStyleName("appbutton-default-disabled");
                //deleteClient.setStyleName("appbutton-default-disabled");
                //archiveClient.setStyleName("appbutton-default-disabled");
                //archiveClient.setEnabled(false);
                //deleteClient.setEnabled(false);
                //editClient.setEnabled(false);
            }
            else {
                if (selected.size() == 1) {
                    //editPayment.setStyleName("appbutton-default");
                    //editPayment.setEnabled(true);
                }
                else {
                    //editClient.setStyleName("appbutton-default-disabled");
                    //editClient.setEnabled(false);
                    //deleteClient.setStyleName("appbutton-default");
                    //deleteClient.setEnabled(true);
                    //archiveClient.setStyleName("appbutton-default");
                    //archiveClient.setEnabled(true);
                }
                
                for (PaymentInfo info : selected) {
                    selection[i] = info;
                    i++;
                }
            }
        }
    }
    private class DefaultClickHandler implements ClickHandler {
        private final Payments pageReference;
        
        public DefaultClickHandler(Payments reference) {
            this.pageReference = reference;
        }
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == toggleAll) {
                this.pageReference.setPaymentStatusToggle(PaymentStatus.ALL);
                this.pageReference.getAllPayments();
                
            }
            if (sender == toggleDeclined) {
                this.pageReference.setPaymentStatusToggle(PaymentStatus.DECLINED);
                this.pageReference.getAllPayments();
            }
            if (sender == togglePaid) {
                this.pageReference.setPaymentStatusToggle(PaymentStatus.PAID);
                this.pageReference.getAllPayments();
            }
            if (sender == btnNewPayment) {
                History.newItem(Views.newpayment.toString());
                event.getNativeEvent().preventDefault();                
            }
        }
    }
    private class SearchFocusHandler implements FocusHandler {
        
        @Override
        public void onFocus(FocusEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().equals(PAYMENT_FILTER_TEXT)) {
                ((TextBox)sender).setText("");
            }
        }
    }
    private class SearchLostFocusHandler implements BlurHandler {
        @Override
        public void onBlur(BlurEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().isEmpty()) {
                ((TextBox)sender).setText(PAYMENT_FILTER_TEXT);
            }
        }
    }
    private class SearchChangeHandler implements KeyUpHandler {
        @Override
        public void onKeyUp(KeyUpEvent event) {
            getAllPayments();
        }
    }
    // </editor-fold>    
}
