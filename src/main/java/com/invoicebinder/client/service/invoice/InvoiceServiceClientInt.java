package com.invoicebinder.client.service.invoice;

import com.invoicebinder.shared.enums.invoice.InvoiceStatus;
import com.invoicebinder.shared.enums.invoice.InvoiceStatusChangePage;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.google.gwt.view.client.AsyncDataProvider;
import java.util.ArrayList;

/**
 *
 * @author msushil
 */
public interface InvoiceServiceClientInt {
    void getAllInvoices(int start, int length, ArrayList<GridColSortInfo> sortList, String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter, AsyncDataProvider<InvoiceInfo> provider);
    void getAllOverdueInvoices(int start, int length, ArrayList<GridColSortInfo> sortList, AsyncDataProvider<InvoiceInfo> provider);
    void getOverdueInvoiceInfoForClient(long clientId);       
    void deleteInvoices(long[] ids);
    void saveInvoice(InvoiceInfo info);
    void loadInvoice(long invoiceId);
    void getInvoicesCount(String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter);    
    void setInvoiceStatus(long id, InvoiceStatus invoiceStatus, InvoiceStatusChangePage fromPage);
    void loadInvoiceDetailsForInvoicePreviewPage(int invoiceId);
    void loadInvoiceDetailsForInvoicePage(int invoiceId);    
}
