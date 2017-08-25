package com.invoicebinder.client.service.invoice;

import com.invoicebinder.shared.enums.invoice.InvoiceStatus;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.invoicebinder.shared.model.ViewInvoiceInfo;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
@RemoteServiceRelativePath("/invoicebinder/services/invoice")
public interface InvoiceService extends RemoteService {
    public ArrayList<InvoiceInfo> getAllInvoicesInfo(int start, int length, 
            ArrayList<GridColSortInfo> sortList,
            String invoiceClientNameFilter, 
            InvoiceStatus invoiceStatusFilter);       
    public ArrayList<InvoiceInfo> getAllOverdueInvoicesInfo(int start, int length, ArrayList<GridColSortInfo> sortList);    
    public ArrayList<InvoiceInfo> getOverdueInvoiceInfoForClient(long clientId);   
    public int deleteInvoices(long[] id); 
    public long saveInvoice(InvoiceInfo info);
    public InvoiceInfo loadInvoice(long invoiceId);
    public ViewInvoiceInfo loadViewInvoice(long invoiceId);
    public int getInvoicesCount(String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter);     
    public int getOverdueInvoicesCount();    
    public Boolean setInvoiceStatus(long id, InvoiceStatus invoiceStatus);   
}
