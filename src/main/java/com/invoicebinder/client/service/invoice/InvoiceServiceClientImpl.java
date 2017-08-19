package com.invoicebinder.client.service.invoice;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.invoice.InvoiceStatus;
import com.invoicebinder.shared.enums.invoice.InvoiceStatusChangePage;
import com.invoicebinder.shared.enums.invoice.NewInvoiceItemColumns;
import com.invoicebinder.shared.misc.FieldVerifier;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.invoicebinder.shared.model.ViewInvoiceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msushil
 */
public class InvoiceServiceClientImpl implements InvoiceServiceClientInt {
    private final InvoiceServiceAsync service;
    private final Main mainui;
    
    public InvoiceServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(InvoiceService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }
    
    @Override
    public void getAllInvoices(int start, int length, ArrayList<GridColSortInfo> sortList, String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter, AsyncDataProvider<InvoiceInfo> provider) {
        this.service.getAllInvoicesInfo(start, length,  sortList, invoiceClientNameFilter, invoiceStatusFilter, new GetInvoiceListCallback(provider, start));
    }
    
    @Override
    public void getAllOverdueInvoices(int start, int length, ArrayList<GridColSortInfo> sortList, AsyncDataProvider<InvoiceInfo> provider) {
        this.service.getAllOverdueInvoicesInfo(start, length,  sortList, new GetInvoiceListCallback(provider, start));
    }
    
    @Override
    public void deleteInvoices(long[] ids) {
        this.service.deleteInvoices(ids, new DeleteInvoiceCallback());
    }
    
    @Override
    public void saveInvoice(InvoiceInfo info) {
        ValidationResult result = new ValidationResult();
        //validations
        
        //client
        if (FieldVerifier.isBlankField(info.getClientName())) {
            result.setMessage("Please select a client.");
            result.setTagname("clientSuggestBox");
            mainui.getContainer().doValidation(Views.newinvoice, result);
            return;
        }
        
        //invoice date
        if (info.getInvoiceDate() == null) {
            result.setMessage("Please enter the invoice date");
            result.setTagname("invoiceDate");
            mainui.getContainer().doValidation(Views.newinvoice, result);
            return;
        }
        
        //due date
        if (info.getDueDate() == null) {
            result.setMessage("Please enter the due date");
            result.setTagname("dueDate");
            mainui.getContainer().doValidation(Views.newinvoice, result);
            return;
        }
        
        //due date and invoice date
        if (info.getDueDate().before(info.getInvoiceDate())) {
            result.setMessage("Due date cannot be less than the Invoice date");
            result.setTagname("dueDate");
            mainui.getContainer().doValidation(Views.newinvoice, result);
            return;
        }
        
        //item
        if (info.getItemList().isEmpty()) {
            result.setMessage("Please enter an item");
            result.setTagname(NewInvoiceItemColumns.ITEMNAME.toString() + "_1");
            mainui.getContainer().doValidation(Views.newinvoice, result);
            return;
        }
        
        //mark paid
        if (info.isMarkPaid()) {
            if (info.getPaymentDate() == null) {
                result.setMessage("Please enter the payment date");
                result.setTagname("paymentDate");
                mainui.getContainer().doValidation(Views.newinvoice, result);
                return;
            }
        }
        
        this.service.saveInvoice(info, new SaveInvoiceCallback());
    }
    
    @Override
    public void loadInvoice(long invoiceId) {
        this.service.loadInvoice(invoiceId, new LoadInvoiceCallback());
    }
    
    @Override
    public void getInvoicesCount(String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter) {
        this.service.getInvoicesCount(invoiceClientNameFilter, invoiceStatusFilter, new GetInvoicesCountCallback());
    }
    
    public void getOverdueInvoicesCount() {
        this.service.getOverdueInvoicesCount(new GetOverdueInvoicesCountCallback());
    }
    
    @Override
    public void setInvoiceStatus(long id, InvoiceStatus invoiceStatus, InvoiceStatusChangePage fromPage) {
        this.service.setInvoiceStatus(id, invoiceStatus, new SetInvoiceStatusCallback(fromPage));
    }
    
    @Override
    public void loadInvoiceDetailsForInvoicePreviewPage(int invoiceId) {
        this.service.loadViewInvoice(invoiceId, new LoadInvoiceDetailsForInvoicePreviewPageCallback());
    }
    
    @Override
    public void loadInvoiceDetailsForInvoicePage(int invoiceId) {
        this.service.loadViewInvoice(invoiceId, new LoadInvoiceDetailsForViewInvoicePageCallback());
    }

    @Override
    public void getOverdueInvoiceInfoForClient(long clientId) {
        this.service.getOverdueInvoiceInfoForClient(clientId, new GetOverdueInvoiceInfoForClientCallback());
    }
    
    private class GetOverdueInvoicesCountCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Object result) {
            mainui.updateOverdueInvoicesCount((Integer)result);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Callback Handlers">
    private class LoadInvoiceDetailsForViewInvoicePageCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Object result) {
            mainui.updateInvoiceDetailsForInvoicePage((ViewInvoiceInfo)result);
        }
    }
    private class LoadInvoiceDetailsForInvoicePreviewPageCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Object result) {
            mainui.updateInvoiceDetailsForInvoicePreviewPage((ViewInvoiceInfo)result);
        }
    }
    private class LoadInvoiceCallback implements AsyncCallback<InvoiceInfo> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(InvoiceInfo result) {
            mainui.getContainer().loadInvoice(result);
        }
    }
    private class DeleteInvoiceCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            if ((Integer)result == 0) {
                mainui.getContainer().refreshClients();
            } else {
                Alert.show("unable to delete.", AlertLevel.ERROR);
            }
        }
    }
    private class GetInvoiceListCallback implements AsyncCallback {
        private final AsyncDataProvider<InvoiceInfo> dataProvider;
        private final int start;
        
        public GetInvoiceListCallback(AsyncDataProvider<InvoiceInfo> provider, int start){
            this.dataProvider = provider;
            this.start = start;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            this.dataProvider.updateRowData(start,(List<InvoiceInfo>)result);
        }
    }
    private class SaveInvoiceCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            History.newItem(Views.showinvoice.toString()  + "/invoiceId=" + ((Long)result).toString());
        }
    }
    private class GetInvoicesCountCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            mainui.updateInvoicesCount((Integer)result);
        }
    }
    private class SetInvoiceStatusCallback implements AsyncCallback {
        InvoiceStatusChangePage fromPage;
        
        public SetInvoiceStatusCallback(InvoiceStatusChangePage page){
            this.fromPage = page;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            if (fromPage == InvoiceStatusChangePage.VIEWINVOICE) {
                mainui.updateInvoiceStatusForViewInvoicePage((Boolean)result);
            }
            if (fromPage == InvoiceStatusChangePage.INVOICES) {
                mainui.updateInvoiceStatusForInvoicesPage((Boolean)result);
            }
        }
        
    }
    private class GetOverdueInvoiceInfoForClientCallback implements AsyncCallback<ArrayList<InvoiceInfo>> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(ArrayList<InvoiceInfo> result) {
            mainui.getContainer().getNewPayment().setOverdueInvoicesInfo(result);
        }        
    }
// </editor-fold>
}
