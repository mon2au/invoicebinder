package com.invoicebinder.client.service.config;

/**
 * Created by mon2 on 16/07/2017.
 */
public class ConfigServiceCallbacks {
    public enum TemplateNameTargetPage {
        InvoicePage,
        InvoicePreviewPage,
        ConfigPage
    }

    public enum BusinessConfigTargetPage {
        NewInvoicePage,
        HomePage
    }

    public enum EmailConfigTargetPage {
        ViewInvoicePage
    }
}
