/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.payments;

import com.invoicebinder.client.service.payment.PaymentServiceClientImpl;
import com.invoicebinder.client.service.invoice.InvoiceServiceClientImpl;
import com.invoicebinder.client.service.suggestion.ClientSuggestOracle;
import com.invoicebinder.client.service.suggestion.CurrencySuggestOracle;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.enums.payment.PaymentStatus;
import com.invoicebinder.shared.enums.payment.PaymentType;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.misc.Utils;
import static com.invoicebinder.shared.misc.Utils.getParamFromHref;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.PaymentInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.invoicebinder.shared.suggestion.ClientSuggestion;
import com.invoicebinder.shared.suggestion.CurrencySuggestion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mon2
 */
public class NewPayment extends Composite {
    
    private static final NewPaymentUiBinder uiBinder = GWT.create(NewPaymentUiBinder.class);
    @UiField VerticalPanel mainPanel;
    private final Label lblHeader;
    private final Label lblOverdueInvoicesHeader;
    private final Main main;
    private final PaymentServiceClientImpl paymentService;
    private final InvoiceServiceClientImpl invoiceService;
    private final DateBox dtPaymentDate;
    
    private IntegerBox txtPaymentAmount;
    private TextArea txtPaymentNote;
    private TextBox txtPaymentReference;
    private TextBox txtProviderRef;

    private ListBox lstPaymentType;
    private Button btnNewClient;
    private Button btnSave;
    private Button btnCancel;
    
    private CurrencySuggestOracle currencyOracle;
    private SuggestBox currencySuggestBox;
    private CurrencySuggestion currencySuggestion;
    
    private ClientSuggestOracle clientOracle;
    private SuggestBox clientSuggestBox;
    private ClientSuggestion clientSuggestion;
    private FlexTable tblOverdueInvoices;
    private static final String LABELCOLWIDTH = "160px";
    private String invoiceNum;
    private ArrayList<Long> invoiceIds = new ArrayList<>();    
    
    public void updateValidationStatus(ValidationResult result) {
        Element element;
        
        element = DOM.getElementById(result.getTagname());
        element.focus();
        ValidationPopup.Show(result.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }

    private void recalculateTotalPaymentAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        TextBox txtAmount;
        CheckBox chkInvoice;
        final int AMOUNT_COL = 9;
        final int CHECKBOX_COL = 0;
        invoiceIds.clear();
        
        for (int i=0;i<tblOverdueInvoices.getRowCount() - 1;i++) {
            if (this.tblOverdueInvoices.getCellCount(i) >= AMOUNT_COL) {               
                if (this.tblOverdueInvoices.getWidget(i, CHECKBOX_COL) != null) {
                    chkInvoice = (CheckBox)this.tblOverdueInvoices.getWidget(i, CHECKBOX_COL);
                    if (chkInvoice.getValue()) {
                        if (this.tblOverdueInvoices.getWidget(i, AMOUNT_COL) != null) {
                            txtAmount = (TextBox)this.tblOverdueInvoices.getWidget(i, AMOUNT_COL);
                            totalAmount = totalAmount.add(new BigDecimal(txtAmount.getText()));
                            invoiceIds.add(Long.valueOf(chkInvoice.getName()));
                        }
                    }
                }                  
            }        
        }
        
        this.txtPaymentAmount.setText(totalAmount.toString());
    }
    
    interface NewPaymentUiBinder extends UiBinder<Widget, NewPayment> {
    }
    
    public NewPayment(Main main) {
        initWidget(uiBinder.createAndBindUi(this));
        this.main = main;
        this.paymentService = new PaymentServiceClientImpl(GWT.getModuleBaseURL() + "services/payment", this.main);
        this.invoiceService = new InvoiceServiceClientImpl(GWT.getModuleBaseURL() + "services/invoice", this.main);
        this.lblHeader = new Label();
        this.lblOverdueInvoicesHeader = new Label();
        this.lblHeader.getElement().setInnerHTML("<h5><span>New Payment</span></h5>");
        this.lblOverdueInvoicesHeader.getElement().setInnerHTML("<h5><span>Unpaid Invoices</span></h5>");
        this.lblOverdueInvoicesHeader.setVisible(false); //hide this header by default. Only shown if there are unpaid invoices.
        this.dtPaymentDate = new DateBox();
        setPendingInvoicesTable();
        this.mainPanel.setStyleName("container");
        this.mainPanel.add(lblHeader);
        this.mainPanel.add(getPaymentDetailsTable());
        this.mainPanel.add(lblOverdueInvoicesHeader);
        this.mainPanel.add(tblOverdueInvoices);
        this.mainPanel.add(buttonsPanel());
        lstPaymentType.setStyleName("list-box");
        txtPaymentNote.setStyleName("text-area");
        setupPaymentPage();
    }
    
    private void setupPaymentPage() {
        long clientId = 0;
        
        for (PaymentType type : PaymentType.values()) {
            lstPaymentType.addItem(type.name(), String.valueOf(type.ordinal()));
        }
        
        this.dtPaymentDate.setValue(new Date());
        this.dtPaymentDate.getDatePicker().setSize("200px", "200px");
        this.dtPaymentDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_LONG)));    
        if (Utils.getParamFromHref("clientId") != null) { clientId = Integer.parseInt(Utils.getParamFromHref("clientId")); }
             
        String clientName = getParamFromHref("clientName");
        String paymentAmount = getParamFromHref("paymentAmount");
        String currencyCode = getParamFromHref("currencyCode");
        invoiceNum = getParamFromHref("invoiceNum");
        
        if (!"".equals(clientName)) { 
            this.clientSuggestBox.setValue(clientName); 
            this.clientSuggestion = new ClientSuggestion(clientName, clientId);
        }
        
        if (!"".equals(currencyCode)) { 
            this.currencySuggestion = new CurrencySuggestion(currencyCode, currencyCode);
            this.currencySuggestBox.setValue(currencyCode); 
        }
        
        if (!"".equals(paymentAmount)) { 
            this.txtPaymentAmount.setText(paymentAmount); 
            this.dtPaymentDate.setValue(new Date());
        }

        if (clientId != 0) { 
            this.invoiceService.getOverdueInvoiceInfoForClient(clientId); 
        }
    }
    
    private void savePayment() {
        PaymentInfo info = new PaymentInfo();
        
        if (!"".equals(txtPaymentAmount.getText())) info.setAmount(new BigDecimal(txtPaymentAmount.getText()));
        if (clientSuggestion != null) info.setClientInfo(new ClientInfo(clientSuggestion.getId()));
        if (currencySuggestion != null) info.setCurrencyCode(currencySuggestion.getCode());
        info.setNote(txtPaymentNote.getText());
        info.setPaymentDate(dtPaymentDate.getValue());
        info.setPaymentStatus(PaymentStatus.PAID.toString());
        info.setPaymentType(lstPaymentType.getSelectedValue());
        info.setProviderRef(txtProviderRef.getText());
        info.setReferenceNo(txtPaymentReference.getText());
        info.setInvoiceIds(invoiceIds);
        
        paymentService.savePayment(info);
    }
    
    public void setOverdueInvoicesInfo(ArrayList<InvoiceInfo> infoList) {
        Label lblInvoiceNum;
        TextBox txtInvoiceAmount;
        TextBox txtTotalOutstandingAmount = new TextBox();
        Label lblInvoiceDate;  
        CheckBox chkInvoice;
        InvoiceInfo info;
        int i;
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalAmountLabelCol;
        int totalAmountCol;
        
        if (infoList.size() > 0) {
            lblOverdueInvoicesHeader.setVisible(true);
            totalAmountLabelCol = 8;
            totalAmountCol = 9; 
        } else {
            totalAmountLabelCol = 0;
            totalAmountCol = 1;
        }
        
        this.tblOverdueInvoices.clear(true);        
        for (i = 0; i <= infoList.size() - 1; i++) {
            info = infoList.get(i);
            lblInvoiceNum = new Label();
            txtInvoiceAmount = new TextBox();
            lblInvoiceDate = new Label();
            lblInvoiceNum.setText(info.getInvoiceNumber());
            txtInvoiceAmount.setText(String.valueOf(info.getAmount()));         
            lblInvoiceDate.setText(String.valueOf(info.getInvoiceDate()));   
            txtInvoiceAmount.setReadOnly(true);   
            chkInvoice = new CheckBox();
            chkInvoice.setName(String.valueOf(info.getId()));
            chkInvoice.getElement().setId("chkInvoice" + info.getInvoiceNumber());
            chkInvoice.addClickHandler(new DefaultClickHandler(this, chkInvoice));
            if (info.getInvoiceNumber().equals(invoiceNum)) {
                chkInvoice.setValue(Boolean.TRUE);
            }
            this.tblOverdueInvoices.setWidget(i, 0, chkInvoice); 
            this.tblOverdueInvoices.getFlexCellFormatter().setWidth(i, 0, "10px");
            this.tblOverdueInvoices.setText(i, 1, "Invoice #: ");
            this.tblOverdueInvoices.setWidget(i, 2, lblInvoiceNum);          
            this.tblOverdueInvoices.getFlexCellFormatter().setWidth(i, 3, "100px");        
            this.tblOverdueInvoices.setText(i, 4, "Invoice Date: ");
            this.tblOverdueInvoices.setWidget(i, 5, lblInvoiceDate);          
            this.tblOverdueInvoices.getFlexCellFormatter().setWidth(i, 6, "100px");                
            this.tblOverdueInvoices.setText(i, 8, "Amount Due: ");
            this.tblOverdueInvoices.setWidget(i, 9, txtInvoiceAmount);
            totalAmount = totalAmount.add(info.getAmount());           
        } 
        
        totalAmount.setScale(2, BigDecimal.ROUND_DOWN);        
        txtTotalOutstandingAmount.setReadOnly(true);
        txtTotalOutstandingAmount.setText(String.valueOf(totalAmount));
        this.tblOverdueInvoices.setText(++i, totalAmountLabelCol, "Total Amount Due: ");
        this.tblOverdueInvoices.setWidget(i, totalAmountCol, txtTotalOutstandingAmount);        
        
    }
    
    private FlexTable getPaymentDetailsTable() {
        FlexTable table = new FlexTable();
        VerticalPanel suggestPanel = new VerticalPanel();
        HorizontalPanel clientMessageandLink = new HorizontalPanel();
        
        this.txtPaymentAmount = new IntegerBox();
        this.txtPaymentReference = new TextBox();
        this.txtPaymentNote = new TextArea();
        this.txtProviderRef = new TextBox();
        this.lstPaymentType = new ListBox();
        
        this.btnNewClient = new Button();
        this.btnNewClient.setText("Create");
        this.btnNewClient.setStyleName("appbutton-default");
        this.btnNewClient.addClickHandler(new DefaultClickHandler(this));
        
        txtPaymentAmount.getElement().setId("txtPaymentAmount");
        table.getColumnFormatter().setWidth(0, LABELCOLWIDTH);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setHTML(0, 0, "");
        //client information
        
        clientOracle = new ClientSuggestOracle(main);
        clientSuggestBox = new SuggestBox(clientOracle);
        clientSuggestBox.getElement().setId("clientSuggestBox");
        clientSuggestBox.addSelectionHandler(new ClientSelectionHandler());
        clientSuggestBox.addValueChangeHandler(new ClientValueChangedHandler());
        clientSuggestion = null;
        suggestPanel.add(clientSuggestBox);        
        currencyOracle = new CurrencySuggestOracle();
        currencySuggestBox = new SuggestBox(currencyOracle);
        currencySuggestBox.getElement().setId("currencySuggestBox");
        currencySuggestBox.addSelectionHandler(new CurrencySelectionHandler());
        currencySuggestBox.setWidth("70px");
        currencySuggestion = null;
        
        clientMessageandLink.add(new HTML("Choose or Create client "));
        clientMessageandLink.add(btnNewClient);
        
        table.setWidget(1, 0, clientMessageandLink);
        table.setWidget(1, 1, suggestPanel);
        table.setHTML(2, 0, "Payment Amount:");
        table.setWidget(2, 1, txtPaymentAmount);
        table.setWidget(2,2, currencySuggestBox);
        table.setHTML(3, 0, "Date:");
        table.setWidget(3, 1, dtPaymentDate);
        table.setHTML(4, 0, "Payment Reference:");
        table.setWidget(4, 1, txtPaymentReference);
        table.setHTML(5, 0, "Provider Reference:");
        table.setWidget(5, 1, txtProviderRef);
        table.setHTML(6, 0, "Payment Type:");
        table.setWidget(6, 1, lstPaymentType);
        table.setHTML(7, 0, "Payment Note:");
        table.setWidget(7, 1, txtPaymentNote);
        return table;
    }
    
    private void setPendingInvoicesTable() {
        tblOverdueInvoices = new FlexTable();
        tblOverdueInvoices.setCellSpacing(Constants.PANEL_CELL_SPACING);
        tblOverdueInvoices.setHTML(0, 0, "");
    }
    
    private HorizontalPanel buttonsPanel() {
        HorizontalPanel panel = new HorizontalPanel();
        btnSave = new Button();
        btnCancel = new Button();
        
        btnSave.setStyleName("appbutton-default");
        btnCancel.setStyleName("appbutton-default");
        btnSave.setText("Save");
        btnCancel.setText("Cancel");
        panel.setSpacing(Constants.PANEL_CELL_SPACING);
        btnSave.addClickHandler(new DefaultClickHandler(this));
        btnCancel.addClickHandler(new DefaultClickHandler(this));
        panel.add(btnSave);
        panel.add(btnCancel);
        return panel;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class ClientValueChangedHandler implements ValueChangeHandler {

        @Override
        public void onValueChange(ValueChangeEvent event) {
            if (clientSuggestBox.getText().isEmpty()) {
                clientSuggestion = null;
                tblOverdueInvoices.clear(true);
                lblOverdueInvoicesHeader.setVisible(false);
            }
        }
        
    }
    
    private class ClientSelectionHandler implements SelectionHandler {
        
        @Override
        public void onSelection(SelectionEvent event) {
            clientSuggestion = ((ClientSuggestion) event.getSelectedItem());
            invoiceService.getOverdueInvoiceInfoForClient(clientSuggestion.getId());
            
        }
    }
    
    private class CurrencySelectionHandler implements SelectionHandler {
        
        @Override
        public void onSelection(SelectionEvent event) {
            currencySuggestion = ((CurrencySuggestion) event.getSelectedItem());
        }
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final NewPayment pageReference;
        private final CheckBox chkReference;
        
        public DefaultClickHandler(NewPayment reference, CheckBox chkReference) {
            this.pageReference = reference;
            this.chkReference = chkReference;
        }
        
        public DefaultClickHandler(NewPayment reference) {
            this.pageReference = reference;
            this.chkReference = null;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                this.pageReference.savePayment();
            }
            else if (sender == btnCancel) {
                History.newItem(Views.payments.toString());
                event.getNativeEvent().preventDefault();
            }
            else if (sender == btnNewClient) {
                History.newItem(Views.newclient.toString());
                event.getNativeEvent().preventDefault();
            }
            else if (sender == chkReference) {
                this.pageReference.recalculateTotalPaymentAmount();
            }
        }
    }
    // </editor-fold>
}
