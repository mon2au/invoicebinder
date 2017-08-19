/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.invoices;

import com.invoicebinder.client.service.client.ClientServiceClientImpl;
import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.service.invoice.InvoiceServiceClientImpl;
import com.invoicebinder.client.service.product.ProductServiceClientImpl;
import com.invoicebinder.client.service.suggestion.ClientSuggestOracle;
import com.invoicebinder.client.service.suggestion.ProductSuggestOracle;
import com.invoicebinder.client.service.utility.UtilityServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.client.ui.widget.miscui.InvoiceItemLabel;
import com.invoicebinder.shared.enums.autonum.AutoNumType;
import com.invoicebinder.shared.enums.config.BusinessConfigItems;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.config.CustomAttribConfigItems;
import com.invoicebinder.shared.enums.invoice.NewInvoiceItemColumns;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.misc.Utils;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.ItemInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.invoicebinder.shared.suggestion.ClientSuggestion;
import com.invoicebinder.shared.suggestion.ProductSuggestion;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author mon2
 */
public class NewInvoice extends Composite {
    
    private static final NewInvoiceUiBinder uiBinder = GWT.create(NewInvoiceUiBinder.class);
    @UiField HTMLPanel newInvoicePanel;
    private Button btnSave;
    private Button btnCancel;
    private Button btnAdd;
    private Button btnRemove;
    private Button btnNewClient;
    private Button btnNewClientFromWrn;    
    private Button btnNewProduct;
    
    private final Label lblHeader;
    private final Label lblInvoiceHeader;
    private final Label lblInvoiceItemsHeader;
    private final Label lblInvoiceDescHeader;
    private final Label lblAdditionalOptionsHeader;
    private final Label lblBusinessName;
    private final Label lblBusinessABN;
    private final Label lblAddress1;
    private final Label lblAddress2;
    private final Label lblCity;
    private final Label lblState;
    private final Label lblPostCode;
 
    private final TextBox txtInvoiceNumber;
    private final TextArea txtDesc;
    private final TextBox txtAttrib1;
    private final TextBox txtAttrib2;
    private final TextBox txtAttrib3;
    private final TextBox txtAttrib4;
    private final TextBox txtAttrib5;
    private final TextBox txtAttrib6;
    private final TextBox txtAttrib7;
    private final TextBox txtAttrib8;
    private final TextBox txtAttrib9;
    private final TextBox txtAttrib10;
    private final TextBox txtPurchOrdNum;
    private final DateBox invoiceDate;
    private final DateBox dueDate;
    private final DateBox paymentDate;
    private final CheckBox markAsPaid;
    private final CheckBox sendInvoiceEmail;
    //suggestions
    private ClientSuggestOracle clientOracle;
    private SuggestBox clientSuggestBox;
    private ClientSuggestion clientSuggestion;
    private ProductSuggestOracle prodOracle;
    private SuggestBox prodSuggestBox;
    private ProductSuggestion prodSuggestion;
    //tables and panels
    private final FlexTable itemTable;
    private final FlexTable mainTable;
    private final VerticalPanel mainPanel;
    private final HorizontalPanel noClientNoticePanel;
    private final HorizontalPanel noProductNoticePanel; 
    
    //services
    private final InvoiceServiceClientImpl invoiceService;
    private final UtilityServiceClientImpl utilityService;
    private final ConfigServiceClientImpl configService;
    private final ClientServiceClientImpl clientService;
    private final ProductServiceClientImpl productService;
    
    private final Main main;
    
    //item management variables.
    private int invoiceId = 0;
    private int idx = 0;
    private int invItemRowCount = 0;
    private static final String itemIdxDelimiter = "_";
    
    public void setValidationResult(ValidationResult validation) {
        Element element;
        
        element = DOM.getElementById(validation.getTagname());
        element.focus();
        ValidationPopup.Show(validation.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }

    public void showEmptyClientTableMessage() {
        noClientNoticePanel.setVisible(true);
    }

    public void showEmptyProductTableMessage() {
        noProductNoticePanel.setVisible(true);
    }
   
    interface NewInvoiceUiBinder extends UiBinder<Widget, NewInvoice> {
    }
    
    public NewInvoice(Object main) {
        this.main = (Main)main;
        this.invoiceService = new InvoiceServiceClientImpl(GWT.getModuleBaseURL() + "services/invoice", this.main);
        this.utilityService = new UtilityServiceClientImpl(GWT.getModuleBaseURL() + "services/utility", this.main);
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        this.clientService = new ClientServiceClientImpl(GWT.getModuleBaseURL() + "services/client", this.main);
        this.productService = new ProductServiceClientImpl(GWT.getModuleBaseURL() + "services/product", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        //init panels
        this.mainPanel = new VerticalPanel();
        this.lblBusinessName = new Label();
        this.lblBusinessABN = new Label();
        this.lblAddress1 = new Label();
        this.lblAddress2 = new Label();
        this.lblCity = new Label();
        this.lblState = new Label();
        this.lblPostCode = new Label();
        this.txtInvoiceNumber = new TextBox();
        this.txtAttrib1 = new TextBox();
        this.txtAttrib2 = new TextBox();
        this.txtAttrib3 = new TextBox();
        this.txtAttrib4 = new TextBox();
        this.txtAttrib5 = new TextBox();
        this.txtAttrib6 = new TextBox();
        this.txtAttrib7 = new TextBox();
        this.txtAttrib8 = new TextBox();
        this.txtAttrib9 = new TextBox();
        this.txtAttrib10 = new TextBox();
        this.txtDesc = new TextArea();
        this.lblHeader = new Label();
        this.lblInvoiceHeader = new Label();
        this.lblInvoiceItemsHeader = new Label();
        this.lblInvoiceDescHeader = new Label();
        this.lblAdditionalOptionsHeader = new Label();
        this.invoiceDate = new DateBox();
        this.invoiceDate.setValue(new Date());
        this.dueDate = new DateBox();
        this.paymentDate = new DateBox();
        this.paymentDate.setVisible(false);
        this.txtPurchOrdNum = new TextBox();
        this.noClientNoticePanel = new HorizontalPanel();  
        this.noProductNoticePanel = new HorizontalPanel();
        this.markAsPaid = new CheckBox();
        this.sendInvoiceEmail = new CheckBox();
        if (Utils.getParamFromHref("invoiceId") != null) this.invoiceId = Integer.parseInt(Utils.getParamFromHref("invoiceId"));
        setHeaders();
        setInformationMessages();
        itemTable = getInvoiceItemsTable();
        mainTable = getInvoiceTable();
        //add all panels
        mainPanel.add(lblHeader);
        mainPanel.add(noClientNoticePanel);
        mainPanel.add(noProductNoticePanel);
        mainPanel.add(lblInvoiceHeader);
        mainPanel.add(mainTable);
        mainPanel.add(lblInvoiceItemsHeader);
        mainPanel.add(itemTable);
        mainPanel.add(bottomPanel());
        mainPanel.add(lblInvoiceDescHeader);
        mainPanel.add(descriptionPanel());
        mainPanel.add(lblAdditionalOptionsHeader);
        mainPanel.add(additionalOptionsPanel());
        mainPanel.add(buttonsPanel());
        mainPanel.setStyleName("container");
        newInvoicePanel.add(mainPanel);
        loadData();
    }
    
    // <editor-fold defaultstate="collapsed" desc="UI Component Tables and Panels">
    private void setHeaders() {
        lblHeader.getElement().setInnerHTML("<h5><span>New Invoice</span></h5>");
        lblInvoiceHeader.getElement().setInnerHTML("<h6><span>Invoice Details</span></h6>");
        lblInvoiceItemsHeader.getElement().setInnerHTML("<h6><span>Invoice Items</span></h6>");
        lblInvoiceDescHeader.getElement().setInnerHTML("<h6><span>Invoice Notes</span></h6>");
        lblAdditionalOptionsHeader.getElement().setInnerHTML("<h6><span>Additional Options</span></h6>");
    }
    
    private void setInformationMessages() {
        HorizontalPanel noClientNoticePanelMessage;
        HorizontalPanel noClientNoticePanelButton;    
        HorizontalPanel noProductNoticePanelMessage;
        HorizontalPanel noProductNoticePanelButton;  
    
        noClientNoticePanelMessage = new HorizontalPanel();
        noClientNoticePanelButton = new HorizontalPanel();        
        noProductNoticePanelMessage = new HorizontalPanel();
        noProductNoticePanelButton = new HorizontalPanel(); 
        
        this.btnNewClient = new Button();
        this.btnNewClient.setText("Create");
        this.btnNewClient.setStyleName("appbutton-default");
        this.btnNewClient.addClickHandler(new DefaultClickHandler(this));
        
        this.btnNewClientFromWrn = new Button();
        this.btnNewClientFromWrn.setText("Create");
        this.btnNewClientFromWrn.setStyleName("appbutton-default");
        this.btnNewClientFromWrn.addClickHandler(new DefaultClickHandler(this));
        
        this.btnNewProduct = new Button();
        this.btnNewProduct.setText("Create");
        this.btnNewProduct.setStyleName("appbutton-default");
        this.btnNewProduct.addClickHandler(new DefaultClickHandler(this));
        
        //set information message panels
        noClientNoticePanelMessage.add(new Label("No clients have been added yet. Please create clients before creationg an invoice.  "));
        noClientNoticePanelMessage.setStyleName("message-box");
        noClientNoticePanelMessage.addStyleName("warning"); 
        noClientNoticePanelButton.add(btnNewClientFromWrn);
        this.noClientNoticePanel.add(noClientNoticePanelMessage);
        this.noClientNoticePanel.add(noClientNoticePanelButton);
        this.noClientNoticePanel.setCellVerticalAlignment(noClientNoticePanelButton, ALIGN_MIDDLE);
        this.noClientNoticePanel.setCellVerticalAlignment(noClientNoticePanelMessage, ALIGN_MIDDLE);
        this.noClientNoticePanel.setVisible(false);
        
        noProductNoticePanelMessage.add(new Label("No products have been added yet. Please create products before creationg an invoice.  "));
        noProductNoticePanelMessage.setStyleName("message-box");
        noProductNoticePanelMessage.addStyleName("warning"); 
        noProductNoticePanelButton.add(btnNewProduct);
        this.noProductNoticePanel.add(noProductNoticePanelMessage);
        this.noProductNoticePanel.add(noProductNoticePanelButton);
        this.noProductNoticePanel.setCellVerticalAlignment(noProductNoticePanelButton, ALIGN_MIDDLE);
        this.noProductNoticePanel.setCellVerticalAlignment(noProductNoticePanelMessage, ALIGN_MIDDLE);
        this.noProductNoticePanel.setVisible(false);        
    }
    
    private FlexTable getInvoiceTable() {
        FlexTable invoiceTable = new FlexTable();
        VerticalPanel suggestPanel = new VerticalPanel();
        HorizontalPanel clientMessageandLink = new HorizontalPanel();
        VerticalPanel invoiceNumberPanel = new VerticalPanel();
        
        invoiceTable.setCellSpacing(Constants.PANEL_CELL_SPACING);
        invoiceTable.setPixelSize(Constants.FORM_WIDTH, Constants.FORM_ROW_HEIGHT);
               
        clientOracle = new ClientSuggestOracle(main);
        clientSuggestBox = new SuggestBox(clientOracle);
        clientSuggestBox.getElement().setId("clientSuggestBox");
        clientSuggestBox.addSelectionHandler(new ClientSelectionHandler());
        clientSuggestion = null;
        suggestPanel.add(clientSuggestBox);
        clientMessageandLink.add(new HTML("Choose or Create client "));
        clientMessageandLink.add(btnNewClient);
        
        //business information
        invoiceTable.setHTML(idx, 0, "Business Name");
        invoiceTable.setWidget(idx++, 1, lblBusinessName);
        invoiceTable.setHTML(idx, 0, "Business Number");
        invoiceTable.setWidget(idx++, 1, lblBusinessABN);
        invoiceTable.setHTML(idx, 0, "Address 1");
        invoiceTable.setWidget(idx++, 1, lblAddress1);
        invoiceTable.setHTML(idx, 0, "Address 2");
        invoiceTable.setWidget(idx++, 1, lblAddress2);
        invoiceTable.setHTML(idx, 0, "City");
        invoiceTable.setWidget(idx++, 1, lblCity);
        invoiceTable.setHTML(idx, 0, "State");
        invoiceTable.setWidget(idx++, 1, lblState);
        invoiceTable.setHTML(idx, 0, "Post Code");
        invoiceTable.setWidget(idx++, 1, lblPostCode);    
        //invoice number
        idx++;
        txtInvoiceNumber.setReadOnly(true);
        invoiceNumberPanel.add(txtInvoiceNumber);
        idx++;
        invoiceTable.setHTML(idx, 0,"Invoice number");
        invoiceTable.setWidget(idx, 1, invoiceNumberPanel);
        
        //client information        
        idx++;
        invoiceTable.setWidget(idx,0, clientMessageandLink);
        invoiceTable.setWidget(idx, 1, suggestPanel);

        idx++;
        invoiceDate.getElement().setId("invoiceDate");
        invoiceDate.getDatePicker().setSize("200px", "200px");
        invoiceDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_LONG)));
        invoiceTable.setHTML(idx,0, "Invoice Date");
        invoiceTable.setWidget(idx, 1, invoiceDate);

        idx++;
        dueDate.getElement().setId("dueDate");
        dueDate.getDatePicker().setSize("200px", "200px");
        dueDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_LONG)));
        invoiceTable.setHTML(idx,0, "Due Date");
        invoiceTable.setWidget(idx, 1, dueDate);
        
        idx++;
        txtPurchOrdNum.getElement().setId("txtPurchOrdNum");
        invoiceTable.setHTML(idx,0, "Purchase Order No");
        invoiceTable.setWidget(idx, 1, txtPurchOrdNum);
        
        return invoiceTable;
    }
    
    private FlexTable getInvoiceItemsTable() {
        FlexTable invoiceItemsTable = new FlexTable();
        invoiceItemsTable.setCellSpacing(Constants.PANEL_CELL_SPACING);
        invoiceItemsTable.setPixelSize(Constants.INVOICE_ITEMS_FORM_WIDTH, Constants.FORM_ROW_HEIGHT);
        
        //create header.
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.SERIALNO.ordinal(), "S.No.");
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.ITEMNAME.ordinal(), "Item");
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.ITEMDESC.ordinal(), "Description");
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.ITEMQTYPLUSBTN.ordinal(), "+");
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.ITEMQTYMINUSBTN.ordinal(), "-");
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.ITEMQTY.ordinal(), "Quantity");
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.ITEMRATE.ordinal(), "Rate");
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.ITEMAMOUNT.ordinal(), "Amount");
        invoiceItemsTable.setHTML(0, NewInvoiceItemColumns.ITEMREMOVEBTN.ordinal(), "");
        
        invoiceItemsTable.getFlexCellFormatter().setHorizontalAlignment(0, NewInvoiceItemColumns.ITEMQTYPLUSBTN.ordinal(), HasHorizontalAlignment.ALIGN_CENTER);
        invoiceItemsTable.getFlexCellFormatter().setHorizontalAlignment(0, NewInvoiceItemColumns.ITEMQTYMINUSBTN.ordinal(), HasHorizontalAlignment.ALIGN_CENTER);
        addInvoiceItemRow(invoiceItemsTable, invItemRowCount);
        
        return invoiceItemsTable;
    }
    
    private HorizontalPanel bottomPanel() {
        HorizontalPanel panel = new HorizontalPanel();
        btnAdd = new Button();
        btnAdd.setStyleName("appbutton-default");
        btnAdd.setText("Add");
        btnAdd.addClickHandler(new DefaultClickHandler(this));
        panel.add(btnAdd);
        panel.setSpacing(Constants.PANEL_CELL_SPACING);
        return panel;
    }
    
    private HorizontalPanel descriptionPanel() {
        HorizontalPanel panel = new HorizontalPanel();
        txtDesc.setHeight("100px");
        txtDesc.setWidth(("1000px"));
        txtDesc.setStyleName("text-area");
        panel.setSpacing(Constants.PANEL_CELL_SPACING);
        panel.add(txtDesc);
        return panel;
    }

    private HorizontalPanel additionalOptionsPanel() {
        FlexTable addOptionsTable = new FlexTable();
        HorizontalPanel panel = new HorizontalPanel();
        
        addOptionsTable.setWidth("600px");
        this.paymentDate.setWidth("150px");
        this.paymentDate.getDatePicker().setSize("200px","200px");
        this.paymentDate.getElement().setId("paymentDate");
        
        addOptionsTable.setCellSpacing(Constants.PANEL_CELL_SPACING);
        addOptionsTable.setPixelSize(Constants.FORM_WIDTH, Constants.FORM_ROW_HEIGHT);
        
        addOptionsTable.setHTML(0, 0, "Mark as Paid");
        addOptionsTable.setWidget(0, 1, markAsPaid);
        addOptionsTable.setWidget(0, 2, paymentDate);
        //TODO - TO be implimented.
        //addOptionsTable.setHTML(1, 0, "Email Invoice on Save");
        //addOptionsTable.setWidget(1, 1, sendInvoiceEmail);        
        //addOptionsTable.setHTML(1, 2, "");    
        
        addOptionsTable.getCellFormatter().setHeight(0, 0, "40px");
        addOptionsTable.getCellFormatter().setHeight(1, 0, "40px");
        
        addOptionsTable.getCellFormatter().setWidth(0, 0, "180px");
        addOptionsTable.getCellFormatter().setWidth(0, 1, "20px");
        addOptionsTable.getCellFormatter().setWidth(0, 2, "340px");      
        
        markAsPaid.addClickHandler(new DefaultCheckClickHandler());

        panel.setSpacing(Constants.PANEL_CELL_SPACING);
        panel.add(addOptionsTable);
        return panel;
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Invoice Items Mgmt">
    private void removeInvoiceItemRow(int index) {
        if (index > 1) {
            itemTable.removeRow(index);
            invItemRowCount--;
        }
        
        refreshInvoiceItemTable();
    }
    
    private void incrementItemCount(int index) {
        TextBox widget;
        Integer count;
        widget = (TextBox)itemTable.getWidget(index,  NewInvoiceItemColumns.ITEMQTY.ordinal());
        count = Integer.parseInt(widget.getText());
        count ++;
        widget.setText(Integer.toString(count));     
        calculateTotal(index, count);
    }
    
    private void decrementItemCount(int index) {
        TextBox widget;
        Integer count;
        widget = (TextBox)itemTable.getWidget(index,  NewInvoiceItemColumns.ITEMQTY.ordinal());
        count = Integer.parseInt(widget.getText());
        if(count > 1) count --;
        widget.setText(Integer.toString(count)); 
        calculateTotal(index, count);
    }    
    
    private void calculateTotal(int index, int count) {
        TextBox widget;
        BigDecimal rate;
        BigDecimal totalAmount;
        
        widget = (TextBox)itemTable.getWidget(index,  NewInvoiceItemColumns.ITEMRATE.ordinal());        
        rate = BigDecimal.valueOf(Double.parseDouble(widget.getText()));
        totalAmount = rate.multiply(BigDecimal.valueOf(count));
        widget = (TextBox)itemTable.getWidget(index,  NewInvoiceItemColumns.ITEMAMOUNT.ordinal());
        widget.setText(totalAmount.toString());
    }
    
    private void refreshInvoiceItemTable() {
        Label item;
        int row;
        for (row=0; row < itemTable.getRowCount();row++) {
            Widget childWidget = itemTable.getWidget(row, NewInvoiceItemColumns.SERIALNO.ordinal());
            if (childWidget != null) {
                if (childWidget.getElement().getId().contains(NewInvoiceItemColumns.SERIALNO.toString())) {
                    item = (Label)childWidget;
                    item.setText(Integer.toString(row));
                }                  
            }
        }        
    }
    
    private void addInvoiceItemRow(FlexTable table, int rowIndex) {
        rowIndex++;
        TextBox textbox;
        InvoiceItemLabel lblSNo;
        String strRowIndex = String.valueOf(rowIndex);
        Button btnPlus;
        Button btnMinus;
        
        lblSNo = new InvoiceItemLabel(rowIndex);
        lblSNo.getElement().setId(NewInvoiceItemColumns.SERIALNO.toString() + itemIdxDelimiter  + strRowIndex);
        lblSNo.setText(strRowIndex);
        table.setWidget(rowIndex, NewInvoiceItemColumns.SERIALNO.ordinal(), lblSNo);
        
        prodOracle = new ProductSuggestOracle();
        prodSuggestBox = new SuggestBox(prodOracle);
        prodSuggestBox.addSelectionHandler(new ProductSelectionHandler());
        prodSuggestion = null;
        prodSuggestBox.getElement().setId(NewInvoiceItemColumns.ITEMNAME.toString() + itemIdxDelimiter + strRowIndex);
        table.setWidget(rowIndex, NewInvoiceItemColumns.ITEMNAME.ordinal(), prodSuggestBox);
        
        textbox = new TextBox();
        textbox.setWidth("500px");
        textbox.getElement().setId(NewInvoiceItemColumns.ITEMDESC.toString() + itemIdxDelimiter + strRowIndex);
        table.setWidget(rowIndex, NewInvoiceItemColumns.ITEMDESC.ordinal(), textbox);
        
        btnPlus = new Button();
        btnPlus.setStyleName("appbutton-default");
        btnPlus.setText("+");
        btnPlus.setWidth("30px");
        btnPlus.getElement().setId(NewInvoiceItemColumns.ITEMQTYPLUSBTN.toString() + itemIdxDelimiter + strRowIndex);
        btnPlus.addClickHandler(new DefaultClickHandler(this));        
        table.setWidget(rowIndex, NewInvoiceItemColumns.ITEMQTYPLUSBTN.ordinal(), btnPlus);
        
        btnMinus = new Button();
        btnMinus.setStyleName("appbutton-default");
        btnMinus.setText("-");
        btnMinus.setWidth("30px");
        btnMinus.getElement().setId(NewInvoiceItemColumns.ITEMQTYMINUSBTN.toString() + itemIdxDelimiter + strRowIndex);
        btnMinus.addClickHandler(new DefaultClickHandler(this));
        table.setWidget(rowIndex, NewInvoiceItemColumns.ITEMQTYMINUSBTN.ordinal(), btnMinus);
        
        textbox = new TextBox();
        textbox.setWidth("80px");
        textbox.getElement().setId(NewInvoiceItemColumns.ITEMQTY.toString() + itemIdxDelimiter + strRowIndex);
        table.setWidget(rowIndex, NewInvoiceItemColumns.ITEMQTY.ordinal(), textbox);
        
        textbox = new TextBox();
        textbox.setWidth("80px");
        textbox.getElement().setId(NewInvoiceItemColumns.ITEMRATE + itemIdxDelimiter + strRowIndex);
        table.setWidget(rowIndex, NewInvoiceItemColumns.ITEMRATE.ordinal(), textbox);
        
        textbox = new TextBox();
        textbox.setWidth("160px");
        textbox.getElement().setId(NewInvoiceItemColumns.ITEMAMOUNT.toString() + itemIdxDelimiter + strRowIndex);
        table.setWidget(rowIndex, NewInvoiceItemColumns.ITEMAMOUNT.ordinal(), textbox);
        
        btnRemove = new Button();
        btnRemove.getElement().setId(NewInvoiceItemColumns.ITEMREMOVEBTN.toString() + itemIdxDelimiter + strRowIndex);
        btnRemove.setText("Remove");
        btnRemove.setStyleName("appbutton-default");
        btnRemove.addClickHandler(new DefaultClickHandler(this));
        table.setWidget(rowIndex, NewInvoiceItemColumns.ITEMREMOVEBTN.ordinal(), btnRemove);
    }
    
    private void setItemRow(int idx, ProductSuggestion suggestion) {
        ((TextBox)itemTable.getWidget(idx, NewInvoiceItemColumns.ITEMDESC.ordinal())).setText(suggestion.getDesc());
        ((TextBox)itemTable.getWidget(idx, NewInvoiceItemColumns.ITEMQTY.ordinal())).setText("1");
        ((TextBox)itemTable.getWidget(idx, NewInvoiceItemColumns.ITEMRATE.ordinal())).setText(suggestion.getUnitPrice().toString());
        ((TextBox)itemTable.getWidget(idx, NewInvoiceItemColumns.ITEMAMOUNT.ordinal())).setText(calculateTotalCost(1, suggestion.getUnitPrice()));
    }
    
    private String calculateTotalCost(int qty, BigDecimal price) {
        return String.valueOf(price.multiply(new BigDecimal(qty)));
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Save / Load">
    private void loadData() {
        if (invoiceId != 0)  {
            loadInvoice(invoiceId);
        } else {
            utilityService.getNextAutoNum(AutoNumType.INVOICE.toString());
            clientService.isClientsTableEmpty();  
            productService.isProductsTableEmpty();
        }     

        configService.loadBusinessConfigDataForNewInvoicePage();
        configService.loadCustomAttrConfigDataForNewInvoicePage();
    }
    
    private void saveInvoice() {
        InvoiceInfo invoice = new InvoiceInfo();
        ItemInfo item;
        ArrayList<ItemInfo> itemList;
        Double amount = 0.0;
        
        if (this.invoiceId != 0) {
            invoice.setId((long)this.invoiceId);
        }

        //set invoice items
        itemList = new ArrayList<>();       
        for (int row=0; row < itemTable.getRowCount();row++) {
            item = new ItemInfo();
            for (int col=0;col < itemTable.getCellCount(row); col++) {
                Widget childWidget = itemTable.getWidget(row, col);
                if (childWidget != null) {
                    if (childWidget.getElement().getId().contains(NewInvoiceItemColumns.ITEMNAME.toString())) {
                        item.setName(((SuggestBox)childWidget).getValue());
                    }
                    if (childWidget.getElement().getId().contains(NewInvoiceItemColumns.ITEMDESC.toString()) && (childWidget instanceof TextBox)) {
                        item.setDesc(((TextBox)childWidget).getText());
                    }
                    if (childWidget.getElement().getId().contains(NewInvoiceItemColumns.ITEMQTY.toString()) && (childWidget instanceof TextBox))  {
                        if (!((TextBox)childWidget).getText().isEmpty()) {
                            item.setItemQty(Integer.parseInt(((TextBox)childWidget).getText()));
                        }
                    }
                    if (childWidget.getElement().getId().contains(NewInvoiceItemColumns.ITEMRATE.toString()) && (childWidget instanceof TextBox)) {
                        if (!((TextBox)childWidget).getText().isEmpty()) {
                            item.setUnitPrice(new BigDecimal(((TextBox)childWidget).getText()));
                        }
                    }                    
                }
            }
            
            if ((item.getName() != null) && (!item.getName().isEmpty())) {
                //set total amount.
                amount = (amount + (item.getUnitPrice().doubleValue() * item.getItemQty()));
                itemList.add(item);
            }
        }
        //add tax.
        amount = amount + (amount * Constants.TAX_PERCENTAGE.doubleValue());
        invoice.setItemList(itemList);
        //invoice number
        invoice.setInvoiceNumber(txtInvoiceNumber.getText());
        invoice.setInvoiceDate(invoiceDate.getValue());
        invoice.setDueDate(dueDate.getValue());
        //set client
        if (clientSuggestion != null) {
            invoice.setClientId(clientSuggestion.getId());
            invoice.setClientInfo(new ClientInfo(clientSuggestion.getFirstName(), clientSuggestion.getLastName()));
        }
        
        //set attributes
        invoice.setAttr1(txtAttrib1.getText());
        invoice.setAttr2(txtAttrib2.getText());
        invoice.setAttr3(txtAttrib3.getText());
        invoice.setAttr4(txtAttrib4.getText());
        invoice.setAttr5(txtAttrib5.getText());
        invoice.setAttr6(txtAttrib6.getText());
        invoice.setAttr7(txtAttrib7.getText());
        invoice.setAttr8(txtAttrib8.getText());
        invoice.setAttr9(txtAttrib9.getText());
        invoice.setAttr10(txtAttrib10.getText());
        //description
        invoice.setDescription(txtDesc.getText());
        invoice.setPurchOrdNumber(txtPurchOrdNum.getText());
        
        invoice.setMarkPaid(markAsPaid.getValue());
        invoice.setPaymentDate(paymentDate.getValue());
        invoice.setSendEmail(sendInvoiceEmail.getValue());
        invoice.setAmount(BigDecimal.valueOf(amount));

        invoiceService.saveInvoice(invoice);
    }
    
    private void loadInvoice(int invoiceId) {
        invoiceService.loadInvoice(invoiceId);
    } 
    
    public void populateInvoiceInfo(InvoiceInfo invoiceInfo) {
        Widget childWidget;        
        
        //set client
        clientSuggestion = new ClientSuggestion(invoiceInfo.getClientName(), invoiceInfo.getClientId(), invoiceInfo.getClientInfo().getFirstName(), invoiceInfo.getClientInfo().getLastName());
        clientSuggestBox.setText(invoiceInfo.getClientName());
        
        txtInvoiceNumber.setText(invoiceInfo.getInvoiceNumber());
        invoiceDate.setValue(invoiceInfo.getInvoiceDate());
        dueDate.setValue(invoiceInfo.getDueDate());
        
        //set attributes
        txtAttrib1.setText(invoiceInfo.getAttr1());
        txtAttrib2.setText(invoiceInfo.getAttr2());
        txtAttrib3.setText(invoiceInfo.getAttr3());
        txtAttrib4.setText(invoiceInfo.getAttr4());
        txtAttrib5.setText(invoiceInfo.getAttr5());
        txtAttrib6.setText(invoiceInfo.getAttr6());
        txtAttrib7.setText(invoiceInfo.getAttr7());
        txtAttrib8.setText(invoiceInfo.getAttr8());
        txtAttrib9.setText(invoiceInfo.getAttr9());
        txtAttrib10.setText(invoiceInfo.getAttr10());
        //description
        txtDesc.setText(invoiceInfo.getDescription());
        txtPurchOrdNum.setText(invoiceInfo.getPurchOrdNumber());  
        
        //set invoice items
        for (int i=0;i<invoiceInfo.getItemList().size();i++) {
            ItemInfo item = invoiceInfo.getItemList().get(i);
            addInvoiceItemRow(itemTable, i);
            
            childWidget = itemTable.getWidget(i + 1, NewInvoiceItemColumns.ITEMNAME.ordinal());
            ((SuggestBox)childWidget).setValue(item.getName());
            childWidget = itemTable.getWidget(i + 1, NewInvoiceItemColumns.ITEMDESC.ordinal());
            ((TextBox)childWidget).setValue(item.getName());
            childWidget = itemTable.getWidget(i + 1, NewInvoiceItemColumns.ITEMQTY.ordinal());
            ((TextBox)childWidget).setValue(String.valueOf(item.getItemQty()));
            childWidget = itemTable.getWidget(i + 1, NewInvoiceItemColumns.ITEMRATE.ordinal());
            ((TextBox)childWidget).setValue(String.valueOf(item.getUnitPrice()));
            childWidget = itemTable.getWidget(i + 1, NewInvoiceItemColumns.ITEMAMOUNT.ordinal());
            ((TextBox)childWidget).setValue(String.valueOf(item.getUnitPrice().multiply(new BigDecimal(item.getItemQty()))));
        }
        
        invItemRowCount = invoiceInfo.getItemList().size() - 1;
        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class DefaultClickHandler implements ClickHandler {
        private final NewInvoice pageReference;
        
        public DefaultClickHandler(NewInvoice reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            int rowIndex;
            
            if (sender == btnSave) {
                this.pageReference.saveInvoice();
            }
            else if ((sender == btnNewClient) || (sender == btnNewClientFromWrn)) {
                History.newItem(Views.newclient.toString());
                event.getNativeEvent().preventDefault();                   
            }
            else if (sender == btnNewProduct) {
                History.newItem(Views.newproduct.toString());
                event.getNativeEvent().preventDefault();                   
            }            
            else if (sender == btnCancel) {
                History.newItem(Views.invoices.toString());
                event.getNativeEvent().preventDefault();
            }
            else if (sender == btnAdd) {
                addInvoiceItemRow(itemTable, ++invItemRowCount);
            }
            else if (sender.getElement().getId().contains(NewInvoiceItemColumns.ITEMREMOVEBTN.toString())) {
                rowIndex = itemTable.getCellForEvent(event).getRowIndex();
                removeInvoiceItemRow(rowIndex);
            }
            else if (sender.getElement().getId().contains(NewInvoiceItemColumns.ITEMQTYPLUSBTN.toString())) {
                rowIndex = itemTable.getCellForEvent(event).getRowIndex();
                incrementItemCount(rowIndex);
            }
            else if (sender.getElement().getId().contains(NewInvoiceItemColumns.ITEMQTYMINUSBTN.toString())) {
                rowIndex = itemTable.getCellForEvent(event).getRowIndex();
                decrementItemCount(rowIndex);
            }            
        }
    }
    
    private class DefaultCheckClickHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            paymentDate.setVisible(markAsPaid.getValue());
        }
    }
    private class ClientSelectionHandler implements SelectionHandler {
        
        @Override
        public void onSelection(SelectionEvent event) {
            clientSuggestion = ((ClientSuggestion) event.getSelectedItem());
        }
    }
    
    private class ProductSelectionHandler implements SelectionHandler {
        private SuggestBox suggestBox = null;
        private int rowIndex;
        private String suggestId;
        
        @Override
        public void onSelection(SelectionEvent event) {
            prodSuggestion = ((ProductSuggestion) event.getSelectedItem());
            suggestBox = (SuggestBox)event.getSource();
            suggestId = suggestBox.getElement().getId();
            if (suggestId != null) {
                rowIndex = Integer.valueOf(suggestId.substring(suggestId.lastIndexOf(itemIdxDelimiter) + 1));
                setItemRow(rowIndex, prodSuggestion);
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Load Configuration Data">
    public void setConfigData(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        switch (section) {
            case Business:setBusinessData(arrayList);
            break;
            case CustomAttributes:setCustomAttrData(arrayList);
            break;
        }
    }
    
    private void setCustomAttrData(ArrayList<ConfigData> arrayList) {
        HashMap<String, String> customAttrConfigData = new HashMap();
        ConfigData data;
        for (int i=0; i<arrayList.size(); i++) {
            data = arrayList.get(i);
            customAttrConfigData.put(data.getKey(), data.getValue());
        }
        
        //add custom attributes.
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR1.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR1.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR1.toString()));
            mainTable.setWidget(idx, 1, txtAttrib1);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR2.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR2.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR2.toString()));
            mainTable.setWidget(idx, 1, txtAttrib2);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR3.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR3.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR3.toString()));
            mainTable.setWidget(idx, 1, txtAttrib3);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR4.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR4.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR4.toString()));
            mainTable.setWidget(idx, 1, txtAttrib4);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR5.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR5.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR5.toString()));
            mainTable.setWidget(idx, 1, txtAttrib5);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR6.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR6.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR6.toString()));
            mainTable.setWidget(idx, 1, txtAttrib6);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR7.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR7.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR7.toString()));
            mainTable.setWidget(idx, 1, txtAttrib7);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR8.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR8.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR8.toString()));
            mainTable.setWidget(idx, 1, txtAttrib8);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR9.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR9.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR9.toString()));
            mainTable.setWidget(idx, 1, txtAttrib9);
        }
        idx++;
        if ((customAttrConfigData.get(CustomAttribConfigItems.ATTR10.toString()) != null) &&
             (!customAttrConfigData.get(CustomAttribConfigItems.ATTR10.toString()).isEmpty())) {
            mainTable.setHTML(idx, 0, customAttrConfigData.get(CustomAttribConfigItems.ATTR10.toString()));
            mainTable.setWidget(idx, 1, txtAttrib10);
        }
    }
    
    public void SetInvoiceNumber(String num) {
        this.txtInvoiceNumber.setText(num);
    }
    
    private void setBusinessData(ArrayList<ConfigData> arrayList) {
        HashMap<String, String> businessConfigData = new HashMap();
        ConfigData data;
        for (int i=0; i<arrayList.size(); i++) {
            data = arrayList.get(i);
            businessConfigData.put(data.getKey(), data.getValue());
        }
        
        lblBusinessName.setText(businessConfigData.get(BusinessConfigItems.BUSINESSNAME.toString()));
        lblBusinessABN.setText(businessConfigData.get(BusinessConfigItems.BUSINESSABN.toString()));
        lblAddress1.setText(businessConfigData.get(BusinessConfigItems.BUSINESSADDRESS1.toString()));
        lblAddress2.setText(businessConfigData.get(BusinessConfigItems.BUSINESSADDRESS2.toString()));
        lblCity.setText(businessConfigData.get(BusinessConfigItems.BUSINESSCITY.toString()));
        lblState.setText(businessConfigData.get(BusinessConfigItems.BUSINESSSTATE.toString()));
        lblPostCode.setText(businessConfigData.get(BusinessConfigItems.BUSINESSPOSTCODE.toString()));      
    }
    // </editor-fold>
}