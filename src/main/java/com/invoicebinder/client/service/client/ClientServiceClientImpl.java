package com.invoicebinder.client.service.client;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.client.ClientStatus;
import com.invoicebinder.shared.misc.FieldVerifier;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;


/**
 *
 * @author msushil
 */
public class ClientServiceClientImpl implements ClientServiceClientInt {
    private final ClientServiceAsync service;
    private final Main mainui;
    
    public ClientServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(ClientService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }
    
    @Override
    public void getAllClients(int start, int length, ArrayList<GridColSortInfo> sortList, String firstNameFilter,
                              ClientStatus clientStatusFilter, AsyncDataProvider<ClientInfo> provider) {
        this.service.getAllClientsInfo(start, length, sortList, firstNameFilter, clientStatusFilter, 
        		new GetClientListCallback(provider, start));
    }
    
    @Override
    public void getClientsCount(String firstNameFilter, ClientStatus clientStatusFilter) {
        this.service.getClientsCount(firstNameFilter, clientStatusFilter, new GetClientsCountCallback());
    }
    
    @Override
    public void deleteClients(long[] ids) {
        this.service.deleteClients(ids, new DeleteClientCallback());
    }
    
    @Override
    public void changeClientStatus(long[] ids, ClientStatus status) {
        this.service.changeClientStatus(ids, status, new ChangeClientStatusCallback());
    }
    
    @Override
    public void saveClient(ClientInfo info) {
        ValidationResult result = new ValidationResult();
        //1. client first name
        if (FieldVerifier.isBlankField(info.getFirstName())) {
            result.setMessage("First name must be supplied.");
            result.setTagname("txtFirstName");
            mainui.getContainer().doValidation(Views.newclient, result);
            return;
        }
        
        this.service.saveClient(info, new SaveClientCallback());
    }
    
    @Override
    public void loadClient(long clientId) {
        this.service.loadClient(clientId, new LoadClientCallback());
    }

    @Override
    public void isClientsTableEmpty() {
        this.service.getClientsCount("", ClientStatus.ALL, new IsClientsTableEmptyCallback());
    }
    
    private class LoadClientCallback implements AsyncCallback<ClientInfo> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(ClientInfo result) {
            mainui.getContainer().loadClient(result);
        }
    }
    
    private class DeleteClientCallback implements AsyncCallback<Integer> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Integer result) {
            if (result == 0) {
                mainui.getContainer().refreshClients();
            } else {
                Alert.show("unable to delete.", AlertLevel.ERROR);
            }
        }
    }
    
    public class GetClientListCallback implements AsyncCallback<ArrayList<ClientInfo>> {
        private final AsyncDataProvider<ClientInfo> dataProvider;
        private final int start;
        
        public GetClientListCallback(AsyncDataProvider<ClientInfo> provider, int start){
            this.dataProvider = provider;
            this.start = start;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(ArrayList<ClientInfo> result) {
            this.dataProvider.updateRowData(start, result);
        }
    }
    
    private class SaveClientCallback implements AsyncCallback<Void> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Void result) {
            History.newItem(Views.clients.toString());
        }
    }
    
    private class GetClientsCountCallback implements AsyncCallback<Integer> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Integer result) {
            mainui.updateClientsCount(result);
        }
    }
    
    private class IsClientsTableEmptyCallback implements AsyncCallback<Integer> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Integer result) {
            if (result == 0) {
                mainui.updateNewInvoiceClientTableEmptyMessage();
            }
        }
    }
    
    private class ChangeClientStatusCallback implements AsyncCallback<Integer> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Integer result) {
            if (result == 0) {
                mainui.getContainer().refreshClients();
            } else {
                Alert.show("unable to change status.", AlertLevel.ERROR);
            }
        }
    }
}
