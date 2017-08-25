/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.templates.invoice.standard;

import com.invoicebinder.client.service.invoice.InvoiceServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.config.CustomAttribConfigItems;
import com.invoicebinder.shared.enums.invoice.InvoiceMode;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.ItemInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.invoicebinder.shared.misc.Constants.DEFAULT_BUSINESS_NUM_LABEL;
import com.invoicebinder.shared.model.BusinessInfo;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.CustomAttribInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.ViewInvoiceInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author mon2
 */
public class StandardInvoice extends Composite {
    
    private static final StandardInvoiceUiBinder uiBinder = GWT.create(StandardInvoiceUiBinder.class);
    @UiField HTMLPanel customAttribPanel;
    @UiField Label lblCompanyName;
    @UiField Label lblCompanyABN;
    @UiField Label lblCompanyAddress;
    @UiField Label lblCompanySuburb;
    @UiField Label lblCompanyStateAndPostCode;
    @UiField Label lblCompanyPhone;
    @UiField Label lblCompanyEmail;
    @UiField Label lblClientName;
    @UiField Label lblClientAddress;
    @UiField Label lblClientSuburb;
    @UiField Label lblClientPhone;
    @UiField Label lblClientEmail;
    @UiField Label lblClientHeader;
    @UiField Label lblInvoiceNumber;
    @UiField Label lblInvoiceDate;
    @UiField Label lblDueDate;
    @UiField Label lblPurchaseOrderNo;
    @UiField HTMLPanel itemsPanel;
    @UiField HTMLPanel descPanel;
    @UiField VerticalPanel invoicePanel;
    @UiField Image imgLogo;
    @UiField Label lblTaxInvoice;
    @UiField TableElement tblMain;
    private final Label lblTaxLabel;    
    private final InvoiceServiceClientImpl invoiceService;
    private final FlexTable customAttribTable;
    private final FlexTable itemsTable;
    private final Main main;
    
    public String invoiceTotal; 
    
    interface StandardInvoiceUiBinder extends UiBinder<Widget, StandardInvoice> {
    }
    
    public StandardInvoice(Object main, int invoiceId, InvoiceMode mode) {
        initWidget(uiBinder.createAndBindUi(this));
        this.main = (Main)main;
        this.invoiceService = new InvoiceServiceClientImpl(GWT.getModuleBaseURL() + "services/invoice", this.main);
        this.customAttribTable = new FlexTable();
        this.itemsTable = new FlexTable();
        this.customAttribPanel.add(customAttribTable);
        this.itemsPanel.add(itemsTable);
        this.lblTaxLabel = new Label();
        
        setStaticData();
        setStyles();
        //load contents.
        if (mode == InvoiceMode.VIEWINVOICEDETAILS) {
            this.invoiceService.loadInvoiceDetailsForInvoicePage(invoiceId);
        }
        if (mode == InvoiceMode.PRINTPREVIEW) {
            this.invoiceService.loadInvoiceDetailsForInvoicePreviewPage(invoiceId);            
        }        
    }
    
    public void updateInvoiceDetails(ViewInvoiceInfo viewInvoiceInfo) {
        setInvoiceDetails(viewInvoiceInfo.getInvoiceInfo());        
        setClientDetails(viewInvoiceInfo.getInvoiceInfo().getClientInfo());
        setInvoiceItemsData(viewInvoiceInfo.getInvoiceInfo().getItemList(), viewInvoiceInfo.getCurrencyCode());
        setInvoiceDesc(viewInvoiceInfo.getInvoiceInfo().getDescription());
        setBusinessData(viewInvoiceInfo.getBusinessInfo());
        setCustomAttrData(viewInvoiceInfo.getCustomAttributes());
    }    
    
    private void setStaticData() {
        lblTaxInvoice.setText("TAX INVOICE");
        lblClientHeader.setText("Bill To");
        imgLogo.setUrl(GWT.getHostPageBaseURL() + "logo/logo.png");
    }
    private void setStyles() {
        lblTaxInvoice.setStyleName("standardinvoice-main-header");
        lblClientHeader.setStyleName("standardinvoice-component-header");
        invoicePanel.setStyleName("invoice-inner-container");
        itemsTable.setWidth("100%");
    }   
    
    // <editor-fold defaultstate="collapsed" desc="Set Invoice Data">
    private void setInvoiceDetails(InvoiceInfo info) {
        Widget widget;
        
        this.lblInvoiceNumber.setText(info.getInvoiceNumber());
        this.lblInvoiceDate.setText(info.getInvoiceDate().toString());
        this.lblDueDate.setText(info.getDueDate().toString());
        this.lblPurchaseOrderNo.setText(info.getPurchOrdNumber());
        
        //update custom attributes.
        for (int idx=0;idx < customAttribTable.getRowCount(); idx++) {
            widget = customAttribTable.getWidget(idx,1);
            if (widget != null) {
                ((Label)widget).setText(info.getAttribute(CustomAttribConfigItems.values()[idx]));
            }
        }        
    }
    private void setClientDetails(ClientInfo info) {
        String clientFullName;
        clientFullName = info.getFirstName();
        if (info.getLastName() != null) {
            clientFullName += " " + info.getLastName();
        }
        
        this.lblClientName.setText(clientFullName);
        this.lblClientAddress.setText(info.getAddressString());
        this.lblClientSuburb.setText(info.getCityStatePostcodeString());        
    }
    private void setInvoiceItemsData(List<ItemInfo> items, String currencyCode) {
        int idx = 0;
        BigDecimal gstpercent = Constants.TAX_PERCENTAGE;
        BigDecimal total = new BigDecimal(0);
        BigDecimal itemtotal;
        BigDecimal gst;
        NumberFormat fmt = NumberFormat.getGlobalCurrencyFormat(currencyCode);
        NumberFormat taxfmt = NumberFormat.getFormat(".##");
        //insert header row.
        itemsTable.insertRow(idx);
        itemsTable.setHTML(idx, 0, "Name");
        itemsTable.setHTML(idx, 1, "Description");
        itemsTable.setHTML(idx, 2, "Quantity");
        itemsTable.setHTML(idx, 3, "Unit Price");
        itemsTable.setHTML(idx, 4, "Total Price");
        itemsTable.getCellFormatter().getElement(0, 0).setClassName("standardinvoice-item-header");
        itemsTable.getCellFormatter().getElement(0, 1).setClassName("standardinvoice-item-header");
        itemsTable.getCellFormatter().getElement(0, 2).setClassName("standardinvoice-item-header");
        itemsTable.getCellFormatter().getElement(0, 3).setClassName("standardinvoice-item-header");
        itemsTable.getCellFormatter().getElement(0, 4).setClassName("standardinvoice-item-header");
        //insert items
        idx++;
        for (ItemInfo item : items) {
            itemsTable.insertRow(idx);
            itemsTable.setHTML(idx, 0, item.getName());
            itemsTable.setHTML(idx, 1, item.getDesc());
            itemsTable.setHTML(idx, 2, String.valueOf(item.getItemQty()));
            itemsTable.setHTML(idx, 3, String.valueOf(item.getUnitPrice()));
            itemsTable.setHTML(idx, 4, String.valueOf(item.getUnitPrice().multiply(new BigDecimal(item.getItemQty()))));
            itemtotal = item.getUnitPrice().multiply(new BigDecimal(item.getItemQty()));
            total = total.add(itemtotal);
            idx++;
        }
        
        //insert sub total row.
        itemsTable.insertRow(idx);
        itemsTable.setHTML(idx, 0, "");
        itemsTable.setHTML(idx, 1, "");
        itemsTable.setHTML(idx, 2, "");
        itemsTable.setHTML(idx, 3, "SUBTOTAL");
        itemsTable.setHTML(idx, 4, String.valueOf(total));
        itemsTable.getCellFormatter().setStyleName(idx, 3, "standardinvoice-totals");
        itemsTable.getCellFormatter().setStyleName(idx, 4, "standardinvoice-totals");
        //insert gst row.
        gst = total.multiply(gstpercent);
        idx++;
        itemsTable.insertRow(idx);
        itemsTable.setHTML(idx, 0, "");
        itemsTable.setHTML(idx, 1, "");
        itemsTable.setHTML(idx, 2, "");
        itemsTable.setWidget(idx, 3, lblTaxLabel);
        gst.setScale(2, BigDecimal.ROUND_DOWN);
        itemsTable.setHTML(idx, 4, taxfmt.format(gst));
        itemsTable.getCellFormatter().setStyleName(idx, 3, "standardinvoice-totals");
        itemsTable.getCellFormatter().setStyleName(idx, 4, "standardinvoice-totals");
        
        //insert total row.
        idx++;
        itemsTable.insertRow(idx);
        itemsTable.setHTML(idx, 0, "");
        itemsTable.setHTML(idx, 1, "");
        itemsTable.setHTML(idx, 2, "");
        itemsTable.setHTML(idx, 3, "TOTAL");
        total = total.add(gst);
        total.setScale(2, BigDecimal.ROUND_DOWN);
        invoiceTotal = fmt.format(total);
        itemsTable.setHTML(idx, 4, invoiceTotal);
        itemsTable.getCellFormatter().setStyleName(idx, 3, "standardinvoice-totals");
        itemsTable.getCellFormatter().setStyleName(idx, 4, "standardinvoice-totals");
    }
    private void setInvoiceDesc(String description){
        Label lblDesc = new Label();
        lblDesc.setText("Note: " + description);
        descPanel.add(lblDesc);
    }
    private void setBusinessData(BusinessInfo info) {
        String token = "";
        
        lblCompanyName.setText(info.getCompanyName());
        if ((!"".equals(info.getBusinessNumber())) && ("".equals(info.getBusinessNumberLabel()))) { 
            info.setBusinessNumberLabel(DEFAULT_BUSINESS_NUM_LABEL); 
        } 
        else { 
            info.setBusinessNumberLabel(info.getBusinessNumberLabel() + ": "); 
        }
        lblCompanyABN.setText(info.getBusinessNumberLabel() + info.getBusinessNumber());
        if ((!"".equals(info.getBusinessAddress1())) && (!"".equals(info.getBusinessAddress2()))) token = ", "; 
        lblCompanyAddress.setText(info.getBusinessAddress1() + token + info.getBusinessAddress2());
        lblCompanySuburb.setText(info.getSuburb());
        lblCompanyStateAndPostCode.setText(info.getState() + " " + info.getPostCode());
        lblCompanyPhone.setText(info.getPhone());
        lblCompanyEmail.setText(info.getEmail());        
    }
    private void setCustomAttrData(CustomAttribInfo info) {
        int idx = 0;
        Label lblItem;
        
        //add custom attributes.
        if ((info.getAttrib1() != null) && (!info.getAttrib1().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib1());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib2() != null) && (!info.getAttrib2().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib2());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib3() != null) && (!info.getAttrib3().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib3());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib4() != null) && (!info.getAttrib4().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib4());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib5() != null) && (!info.getAttrib5().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib5());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib6() != null) && (!info.getAttrib6().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib6());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib7() != null) && (!info.getAttrib7().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib7());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib8() != null) && (!info.getAttrib8().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib8());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib9() != null) && (!info.getAttrib9().isEmpty())){
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib9());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
        idx++;
        if ((info.getAttrib10() != null) && (!info.getAttrib10().isEmpty())) {
            lblItem = new Label();
            customAttribTable.insertRow(idx);
            customAttribTable.setHTML(idx, 0, info.getAttrib10());
            customAttribTable.setWidget(idx, 1, lblItem);
            customAttribTable.getCellFormatter().getElement(idx, 0).setClassName("standardinvoice-component-header");
        }
    }
    // </editor-fold>      
}
