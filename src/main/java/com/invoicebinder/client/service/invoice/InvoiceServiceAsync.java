package com.invoicebinder.client.service.invoice;

import com.invoicebinder.shared.enums.invoice.InvoiceStatus;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.ViewInvoiceInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public interface InvoiceServiceAsync {

    public void getAllInvoicesInfo(int start, int length, 
            ArrayList<GridColSortInfo> sortList,
            String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter,     
            AsyncCallback<ArrayList<InvoiceInfo>> asyncCallback);
    public void getAllOverdueInvoicesInfo(int start, int length, ArrayList<GridColSortInfo> sortList, AsyncCallback<ArrayList<InvoiceInfo>> asyncCallback);        
    public void getOverdueInvoiceInfoForClient(long clientId, AsyncCallback<ArrayList<InvoiceInfo>> asyncCallback);   
    public void deleteInvoices(long[] id, AsyncCallback<Integer> asyncCallback);
    public void saveInvoice(InvoiceInfo info, AsyncCallback<Long> asyncCallback);
    public void loadInvoice(long invoiceId, AsyncCallback<InvoiceInfo> asyncCallback);
    public void loadViewInvoice(long invoiceId, AsyncCallback<ViewInvoiceInfo> asyncCallback);
    public void getInvoicesCount(String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter, AsyncCallback<Integer> invoicesCountCallback);
    public void setInvoiceStatus(long id, InvoiceStatus invoiceStatus, AsyncCallback<Boolean> asyncCallback);       
    public void getOverdueInvoicesCount(AsyncCallback<Integer> invoicesCountCallback);
}
