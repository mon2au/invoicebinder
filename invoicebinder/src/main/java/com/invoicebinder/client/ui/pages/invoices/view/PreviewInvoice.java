/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.invoices.view;

import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.invoice.InvoiceMode;
import static com.invoicebinder.shared.misc.Utils.getParamFromHref;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.ViewInvoiceInfo;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public class PreviewInvoice extends Composite {
    
    private static final PreviewInvoiceUiBinder uiBinder = GWT.create(PreviewInvoiceUiBinder.class);
    private final Invoice invoice; 
    private int invoiceId = 0; 
    
    @UiField HTMLPanel contentPanel;      

    public void updateInvoiceDetailsForInvoicePreviewPage(ViewInvoiceInfo viewInvoiceInfo) {
        this.invoice.updateInvoiceDetails(viewInvoiceInfo);
    }

    public void createInvoiceBasedOnInvoiceTemplateForInvoicePreviewPage(ArrayList<ConfigData> templateData) {
        this.invoice.createInvoiceBasedOnInvoiceTemplate(templateData);
    }
    
    interface PreviewInvoiceUiBinder extends UiBinder<Widget, PreviewInvoice> {
    }
    
    public PreviewInvoice(Main main) {
        initWidget(uiBinder.createAndBindUi(this));
        this.invoiceId = Integer.parseInt(getParamFromHref("invoiceId"));

        this.invoice = new Invoice(main, invoiceId, InvoiceMode.PRINTPREVIEW);
        contentPanel.setStyleName("print-preview");
        Document.get().getBody().getStyle().setBackgroundColor("#ffffff");
        contentPanel.add(invoice);
    }
}
