/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.invoices.view;

import com.invoicebinder.client.service.config.ConfigServiceCallbacks;
import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.invoice.InvoiceMode;
import com.invoicebinder.shared.model.ConfigData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.invoicebinder.client.ui.templates.invoice.artistic.ArtisticInvoice;
import com.invoicebinder.client.ui.templates.invoice.minimalist.MinimalistInvoice;
import com.invoicebinder.client.ui.templates.invoice.professional.ProfessionalInvoice;
import com.invoicebinder.client.ui.templates.invoice.standard.StandardInvoice;
import com.invoicebinder.shared.enums.config.InvoiceTemplateConfigItems;
import com.invoicebinder.shared.enums.invoice.InvoiceTemplate;
import com.invoicebinder.shared.model.ViewInvoiceInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author msushil
 */
public class Invoice extends Composite {
    
    private static final InvoiceUiBinder uiBinder = GWT.create(InvoiceUiBinder.class);
    @UiField VerticalPanel invoicePanel;
    private final ConfigServiceClientImpl configService;
    private final Main main;
    private final int invoiceId;
    private final InvoiceMode invoiceMode;
    private StandardInvoice standardInvoice;
    private MinimalistInvoice minimalistInvoice;
    private ProfessionalInvoice professionalInvoice;
    private ArtisticInvoice artisticInvoice;
    
    void updateInvoiceDetails(ViewInvoiceInfo viewInvoiceInfo) {
        switch (viewInvoiceInfo.getInvoiceTemplate()) {
            case Standard:
                this.standardInvoice.updateInvoiceDetails(viewInvoiceInfo);
                break;
            case Professional:
                this.professionalInvoice.updateInvoiceDetails(viewInvoiceInfo);
                break;
            case Minimalist:
                this.minimalistInvoice.updateInvoiceDetails(viewInvoiceInfo);
                break;
            case Artistic:
                this.artisticInvoice.updateInvoiceDetails(viewInvoiceInfo);
                break;
        }
    }
    
    void createInvoiceBasedOnInvoiceTemplate(ArrayList<ConfigData> templateList) {
        InvoiceTemplate template;
        
        HashMap<String, String> templateData = new HashMap();
        for (ConfigData data : templateList) {
            templateData.put(data.getKey(), data.getValue());
        }
        
        if (templateData.size() > 0) {
            template = InvoiceTemplate.valueOf(templateData.get(InvoiceTemplateConfigItems.INVOICETEMPLATENAME.toString()));
            
            switch (template) {
                case Standard:
                    standardInvoice = new StandardInvoice(this.main, this.invoiceId, this.invoiceMode);
                    invoicePanel.add(standardInvoice);
                    break;
                case Professional:
                    professionalInvoice = new ProfessionalInvoice(this.main, this.invoiceId, this.invoiceMode);
                    invoicePanel.add(professionalInvoice);
                    break;
                case Minimalist:
                    minimalistInvoice = new MinimalistInvoice(this.main, this.invoiceId, this.invoiceMode);
                    invoicePanel.add(minimalistInvoice);
                    break;
                case Artistic:
                    artisticInvoice = new ArtisticInvoice(this.main, this.invoiceId, this.invoiceMode);
                    invoicePanel.add(artisticInvoice);
                    break;
            }
        }
    }
    
    interface InvoiceUiBinder extends UiBinder<Widget, Invoice> {
    }
    
    public Invoice(Object main, long invoiceId, InvoiceMode mode) {
        this.main = (Main)main;
        this.invoiceId = (int)invoiceId;
        this.invoiceMode = mode;
        initWidget(uiBinder.createAndBindUi(this));
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        if (mode == InvoiceMode.VIEWINVOICEDETAILS) {
            this.configService.loadInvoiceTemplateName(ConfigServiceCallbacks.TemplateNameTargetPage.InvoicePage);
        }
        else if (mode == InvoiceMode.PRINTPREVIEW) {
            this.configService.loadInvoiceTemplateName(ConfigServiceCallbacks.TemplateNameTargetPage.InvoicePreviewPage);
        }
    }
}
