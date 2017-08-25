package com.invoicebinder.client.service.client;

import com.invoicebinder.shared.enums.client.ClientStatus;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

public interface ClientServiceAsync {
    
    public void getAllClientsInfo(int start, int length, ArrayList<GridColSortInfo> sortList,
        String firstNameFilter, ClientStatus clientStatusFilter,
        AsyncCallback<ArrayList<ClientInfo>> asyncCallback);
    public void deleteClients(long[] id, AsyncCallback<Integer> asyncCallback);
    public void changeClientStatus(long[] id, ClientStatus status, AsyncCallback<Integer> asyncCallback);
    public void saveClient(ClientInfo info, AsyncCallback<Void> asyncCallback);
    public void loadClient(long clientId, AsyncCallback<ClientInfo> asyncCallback);
    public void getClientsCount(String firstNameFilter, ClientStatus clientStatusFilter, AsyncCallback<Integer>  clientsCountCallback);
}
