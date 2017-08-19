/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.invoices;

import com.invoicebinder.client.service.invoice.InvoiceServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.invoice.InvoiceStatus;
import com.invoicebinder.shared.enums.invoice.InvoiceStatusChangePage;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.invoicebinder.shared.misc.Utils;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author msushil
 */
public class Invoices extends Composite {
    private static final ClientsUiBinder uiBinder = GWT.create(ClientsUiBinder.class);
    private final InvoiceServiceClientImpl invoiceService;
    private InvoiceInfo[] selection;
    private final Main main;
    private CellTable<InvoiceInfo> table;
    private Range invoiceDataRange;
    private String invoiceFilterText;   
    private final InvoicesDataProvider invoicesDataProvider;  
    private final ArrayList<GridColSortInfo> gridColSortList;
    private InvoiceStatus invoiceStatusFilter;    
    private final VerticalPanel gridDataPanel;    
    
    @UiField Button btnNewInvoice;
    @UiField Button btnEditInvoice;
    @UiField Button btnDeleteInvoice;
    @UiField Button btnViewInvoice;
    @UiField Button btnArchiveInvoice;
    @UiField HTMLPanel invoicesPanel;
    @UiField TextBox txtSearch;    
    @UiField ToggleButton toggleOverdue;
    @UiField ToggleButton toggleDraft;
    @UiField ToggleButton toggleSent;    
    @UiField ToggleButton toggleAll;
    @UiField ToggleButton togglePaid;
    @UiField ToggleButton toggleArchived;    
    
    private static final String INVOICE_CLIENTNAME_FILTER_TEXT = "filter by client first name";    
    
    public void refresh() {
        table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
    }

    public void updateTableCount(Integer count) {
        invoicesDataProvider.updateRowCount(count, true);
    }

    private void archiveInvoices() {
        for (InvoiceInfo info : selection) {
            invoiceService.setInvoiceStatus(info.getId(), InvoiceStatus.ARCHIVED, InvoiceStatusChangePage.INVOICES);
        }
    }

    public void setInvoiceStatus(Boolean aBoolean) {
        this.refresh();
    }
    
    interface ClientsUiBinder extends UiBinder<Widget, Invoices> {
    }
    
    public Invoices(Main main) {
        this.main = main;
        this.invoiceService = new InvoiceServiceClientImpl(GWT.getModuleBaseURL() + "services/invoice", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        this.setInvoiceStatusToggle(InvoiceStatus.ALL);        
        invoicesDataProvider = new InvoicesDataProvider(this);
        gridColSortList = new ArrayList<GridColSortInfo>();         
        VerticalPanel panel = new VerticalPanel();
        gridDataPanel = new VerticalPanel(); 
        SimplePager pager = new SimplePager();
        panel.setWidth("100%");
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        table = createInvoiceTable();
        pager.setDisplay(table);
        gridDataPanel.add(table);
        gridDataPanel.setHeight(Constants.STANDARD_GRID_HEIGHT);
        panel.add(gridDataPanel);
        panel.add(pager);
        
        // Add the widgets to the root panel.
        invoicesPanel.add(panel);
        btnNewInvoice.addClickHandler(new DefaultClickHandler(this));
        btnEditInvoice.addClickHandler(new DefaultClickHandler(this));
        btnDeleteInvoice.addClickHandler(new DefaultClickHandler(this));
        btnViewInvoice.addClickHandler(new DefaultClickHandler(this));
        btnArchiveInvoice.addClickHandler(new DefaultClickHandler(this));
        
        toggleAll.addClickHandler(new DefaultClickHandler(this));
        toggleDraft.addClickHandler(new DefaultClickHandler(this));
        toggleArchived.addClickHandler(new DefaultClickHandler(this));
        toggleOverdue.addClickHandler(new DefaultClickHandler(this));
        togglePaid.addClickHandler(new DefaultClickHandler(this));
        toggleSent.addClickHandler(new DefaultClickHandler(this));
        
        txtSearch.addFocusHandler(new SearchFocusHandler());
        txtSearch.addBlurHandler(new SearchLostFocusHandler());
        txtSearch.addKeyUpHandler(new SearchChangeHandler());        
        
        //set defaults
        txtSearch.setText(INVOICE_CLIENTNAME_FILTER_TEXT);        
        btnNewInvoice.setStyleName("appbutton-default");
        btnEditInvoice.setStyleName("appbutton-default-disabled");
        btnDeleteInvoice.setStyleName("appbutton-default-disabled");
        btnViewInvoice.setStyleName("appbutton-default-disabled");
        btnArchiveInvoice.setStyleName("appbutton-default-disabled");
        btnEditInvoice.setEnabled(false);
        btnDeleteInvoice.setEnabled(false);
        btnViewInvoice.setEnabled(false);
        btnArchiveInvoice.setEnabled(false);
    }
    
    private void setInvoiceStatusToggle(InvoiceStatus status) {
        if (status == InvoiceStatus.ALL) {
            this.invoiceStatusFilter = InvoiceStatus.ALL;
            this.toggleAll.setDown(true);
            this.toggleDraft.setDown(false);
            this.toggleArchived.setDown(false);
            this.toggleOverdue.setDown(false);
            this.togglePaid.setDown(false);
            this.toggleSent.setDown(false);
            this.toggleAll.setStyleName("appbutton-default-toggle");
            this.toggleDraft.setStyleName("appbutton-default");
            this.toggleArchived.setStyleName("appbutton-default");
            this.toggleOverdue.setStyleName("appbutton-default");
            this.togglePaid.setStyleName("appbutton-default");
            this.toggleSent.setStyleName("appbutton-default");
        }
        if (status == InvoiceStatus.DRAFT) {
            this.invoiceStatusFilter = InvoiceStatus.DRAFT;
            this.toggleAll.setDown(false);
            this.toggleDraft.setDown(true);
            this.toggleArchived.setDown(false);
            this.toggleOverdue.setDown(false);
            this.togglePaid.setDown(false);
            this.toggleSent.setDown(false);
            this.toggleAll.setStyleName("appbutton-default");
            this.toggleDraft.setStyleName("appbutton-default-toggle");
            this.toggleArchived.setStyleName("appbutton-default");
            this.toggleOverdue.setStyleName("appbutton-default");
            this.togglePaid.setStyleName("appbutton-default");
            this.toggleSent.setStyleName("appbutton-default");          
        }
        if (status == InvoiceStatus.ARCHIVED) {
            this.invoiceStatusFilter = InvoiceStatus.ARCHIVED;
            this.toggleAll.setDown(false);
            this.toggleDraft.setDown(false);
            this.toggleArchived.setDown(true);
            this.toggleOverdue.setDown(false);
            this.togglePaid.setDown(false);
            this.toggleSent.setDown(false);
            this.toggleAll.setStyleName("appbutton-default");
            this.toggleDraft.setStyleName("appbutton-default");
            this.toggleArchived.setStyleName("appbutton-default-toggle");
            this.toggleOverdue.setStyleName("appbutton-default");
            this.togglePaid.setStyleName("appbutton-default");
            this.toggleSent.setStyleName("appbutton-default");            
        }
        if (status == InvoiceStatus.OVERDUE) {
            this.invoiceStatusFilter = InvoiceStatus.OVERDUE;
            this.toggleAll.setDown(false);
            this.toggleDraft.setDown(false);
            this.toggleArchived.setDown(false);
            this.toggleOverdue.setDown(true);
            this.togglePaid.setDown(false);
            this.toggleSent.setDown(false);
            this.toggleAll.setStyleName("appbutton-default");
            this.toggleDraft.setStyleName("appbutton-default");
            this.toggleArchived.setStyleName("appbutton-default");
            this.toggleOverdue.setStyleName("appbutton-default-toggle");
            this.togglePaid.setStyleName("appbutton-default");
            this.toggleSent.setStyleName("appbutton-default");         
        }
        if (status == InvoiceStatus.PAID) {
            this.invoiceStatusFilter = InvoiceStatus.PAID;
            this.toggleAll.setDown(false);
            this.toggleDraft.setDown(false);
            this.toggleArchived.setDown(false);
            this.toggleOverdue.setDown(false);
            this.togglePaid.setDown(false);
            this.toggleSent.setDown(false);
            this.toggleAll.setStyleName("appbutton-default");
            this.toggleDraft.setStyleName("appbutton-default");
            this.toggleArchived.setStyleName("appbutton-default");
            this.toggleOverdue.setStyleName("appbutton-default");
            this.togglePaid.setStyleName("appbutton-default-toggle");
            this.toggleSent.setStyleName("appbutton-default");          
        }
        if (status == InvoiceStatus.SENT) {
            this.invoiceStatusFilter = InvoiceStatus.SENT;
            this.toggleAll.setDown(false);
            this.toggleDraft.setDown(false);
            this.toggleArchived.setDown(false);
            this.toggleOverdue.setDown(false);
            this.togglePaid.setDown(false);
            this.toggleSent.setDown(true);
            this.toggleAll.setStyleName("appbutton-default");
            this.toggleDraft.setStyleName("appbutton-default");
            this.toggleArchived.setStyleName("appbutton-default");
            this.toggleOverdue.setStyleName("appbutton-default");
            this.togglePaid.setStyleName("appbutton-default");
            this.toggleSent.setStyleName("appbutton-default-toggle");        
        }        
    }    
    
    private CellTable createInvoiceTable(){
        table = new CellTable();
        table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);
        
        //Checkbox
        //Column<InvoiceInfo, Boolean> checkColumn = new Column<InvoiceInfo, Boolean>(
        //        new CheckboxCell(true, false)) {
        //            @Override
        //            public Boolean getValue(InvoiceInfo object) {
        //                return true;                        
        //            }
        //        };
        //table.addColumn(checkColumn, "");
        //table.setColumnWidth(checkColumn, 40, Unit.PX);
        
        //Clent First Name
        TextColumn<InvoiceInfo> clientFirstName =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getClientInfo().getFirstName();
                    }
                };
        table.addColumn(clientFirstName, "First Name");        
        
        //Clent Last Name
        TextColumn<InvoiceInfo> clientLastName =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getClientInfo().getLastName();
                    }
                };
        table.addColumn(clientLastName, "Last Name");   
        
        //Invoice#
        TextColumn<InvoiceInfo> invNumber =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getInvoiceNumber();
                    }
                };
        table.addColumn(invNumber, "Invoice No");
        
        //Invoice Date
        TextColumn<InvoiceInfo> invDate =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getInvoiceDate().toString();
                    }
                };
        table.addColumn(invDate, "Date");
        
        //Due Date
        TextColumn<InvoiceInfo> dueDate =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getDueDate().toString();
                    }
                };
        table.addColumn(dueDate, "Due Date");
               
        //Description
        TextColumn<InvoiceInfo> desc =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getDescription();
                    }
                };
        table.addColumn(desc, "Description");
        table.setColumnWidth(desc, 260, Unit.PX);
        
        //Status
        TextColumn<InvoiceInfo> status =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return object.getInvoiceStatus();
                    }
                };
        table.addColumn(status, "Status");
        
        //Amount
        TextColumn<InvoiceInfo> amount =
                new TextColumn<InvoiceInfo>() {
                    @Override
                    public String getValue(InvoiceInfo object) {
                        return String.valueOf(object.getAmount());
                    }
                };
        table.addColumn(amount, "Amount");        
        table.setColumnWidth(amount, 120, Unit.PX);
        
        invoicesDataProvider.addDataDisplay(table);
        
        // Add a selection model to handle user selection.
        MultiSelectionModel<InvoiceInfo> selectionModel;
        selectionModel = new MultiSelectionModel();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new GridSelectionEventHandler());
        table.setWidth(Constants.STANDARD_GRID_WIDTH, true);
        table.setPageSize(Constants.STANDARD_GRID_PAGESIZE);
        table.setEmptyTableWidget(new Label(Constants.EMPTY_DATATABLE_MESSAGE));
        return table;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Save / Load">    
    private void getAllInvoices() {
        invoiceFilterText = txtSearch.getText();
        if (invoiceFilterText.equals(INVOICE_CLIENTNAME_FILTER_TEXT)) {
            invoiceFilterText = "";
        }
        invoiceService.getAllInvoices(
                invoiceDataRange.getStart(), 
                invoiceDataRange.getLength(), 
                gridColSortList,
                invoiceFilterText,
                invoiceStatusFilter,
                invoicesDataProvider);        
        invoiceService.getInvoicesCount(invoiceFilterText, invoiceStatusFilter);
    }
    private void deleteInvoices() {
        long[] ids = new long[selection.length];

        for (int i=0;i<selection.length;i++) {
            ids[i] = selection[i].getId();
        }

        invoiceService.deleteInvoices(ids);
    }  
    // </editor-fold>        
    
    // <editor-fold defaultstate="collapsed" desc="Data Provider">        
    private class InvoicesDataProvider extends AsyncDataProvider<InvoiceInfo> {
        private final Invoices pageReference;
        
        public InvoicesDataProvider(Invoices reference) {
            this.pageReference = reference;
        }

        @Override
        protected void onRangeChanged(HasData<InvoiceInfo> display) {
            this.pageReference.invoiceDataRange = display.getVisibleRange();
            this.pageReference.getAllInvoices();
        }                   
    }    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">        
    private class DefaultClickHandler implements ClickHandler {
        Invoices pageReference;
        
        public DefaultClickHandler(Invoices reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnNewInvoice) {
                History.newItem(Views.newinvoice.toString());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == btnEditInvoice) {
                if (Utils.isDemoApplication()) return;
                History.newItem(Views.editinvoice.toString() + "/invoiceId=" + selection[0].getId());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == btnDeleteInvoice) {
                if (Utils.isDemoApplication()) return;
                pageReference.deleteInvoices();
            }
                       
            if (sender == btnViewInvoice) {
                History.newItem(Views.showinvoice.toString() + "/invoiceId=" + selection[0].getId());
                event.getNativeEvent().preventDefault();
            }
            
            if (sender == btnArchiveInvoice) {
                if (Utils.isDemoApplication()) return;
                pageReference.archiveInvoices();
            } 
            
            if (sender == toggleAll) {
                this.pageReference.setInvoiceStatusToggle(InvoiceStatus.ALL);
                this.pageReference.getAllInvoices();
            }
            
            if (sender == toggleDraft) {
                this.pageReference.setInvoiceStatusToggle(InvoiceStatus.DRAFT);
                this.pageReference.getAllInvoices();
            }
            
            if (sender == toggleArchived) {
                this.pageReference.setInvoiceStatusToggle(InvoiceStatus.ARCHIVED);
                this.pageReference.getAllInvoices();
            }   
            
            if (sender == togglePaid) {
                this.pageReference.setInvoiceStatusToggle(InvoiceStatus.PAID);
                this.pageReference.getAllInvoices();
                
            }
            
            if (sender == toggleSent) {
                this.pageReference.setInvoiceStatusToggle(InvoiceStatus.SENT);
                this.pageReference.getAllInvoices();
            }
            
            if (sender == toggleOverdue) {
                this.pageReference.setInvoiceStatusToggle(InvoiceStatus.OVERDUE);
                this.pageReference.getAllInvoices();
            } 
        }       
    }
    private class GridSelectionEventHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            Set<InvoiceInfo> selected = ((MultiSelectionModel)event.getSource()).getSelectedSet();
            selection = new InvoiceInfo[selected.size()];
            int i = 0;
            
            if (selected.isEmpty()) {
                btnEditInvoice.setStyleName("appbutton-default-disabled");
                btnDeleteInvoice.setStyleName("appbutton-default-disabled");
                btnViewInvoice.setStyleName("appbutton-default-disabled");
                btnArchiveInvoice.setStyleName("appbutton-default-disabled");
                btnDeleteInvoice.setEnabled(false);
                btnViewInvoice.setEnabled(false);
                btnEditInvoice.setEnabled(false);
                btnArchiveInvoice.setEnabled(false);
            }
            else {
                if (selected.size() == 1) {
                    btnEditInvoice.setStyleName("appbutton-default");
                    btnEditInvoice.setEnabled(true);
                    btnDeleteInvoice.setStyleName("appbutton-default");
                    btnDeleteInvoice.setEnabled(true);
                    btnViewInvoice.setStyleName("appbutton-default");
                    btnViewInvoice.setEnabled(true);
                    btnArchiveInvoice.setStyleName("appbutton-default");
                    btnArchiveInvoice.setEnabled(true);
                }
                else {
                    btnEditInvoice.setStyleName("appbutton-default-disabled");
                    btnEditInvoice.setEnabled(false);
                    btnViewInvoice.setStyleName("appbutton-default-disabled");
                    btnViewInvoice.setEnabled(false);
                    btnDeleteInvoice.setStyleName("appbutton-default");
                    btnDeleteInvoice.setEnabled(true);               
                    btnArchiveInvoice.setStyleName("appbutton-default");
                    btnArchiveInvoice.setEnabled(true);                    
                }
                
                for (InvoiceInfo info : selected) {
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
            if (((TextBox)sender).getText().equals(INVOICE_CLIENTNAME_FILTER_TEXT)) {
                ((TextBox)sender).setText("");
            }
        }
    }
    private class SearchLostFocusHandler implements BlurHandler {
        @Override
        public void onBlur(BlurEvent event) {
            Widget sender = (Widget) event.getSource();
            if (((TextBox)sender).getText().isEmpty()) {
                ((TextBox)sender).setText(INVOICE_CLIENTNAME_FILTER_TEXT);
            }
        }
    }    
    private class SearchChangeHandler implements KeyUpHandler {
        @Override
        public void onKeyUp(KeyUpEvent event) {
            getAllInvoices();
        }
    }    
    // </editor-fold>
}

