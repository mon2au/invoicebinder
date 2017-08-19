package com.invoicebinder.client.service.client;

import com.invoicebinder.shared.enums.client.ClientStatus;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

@RemoteServiceRelativePath("/invoicebinder/services/client")
public interface ClientService extends RemoteService {
    public ArrayList<ClientInfo> getAllClientsInfo(int start, int length,
                                                   ArrayList<GridColSortInfo> sortList, String firstNameFilter,
                                                   ClientStatus clientStatusFilter);
    public int deleteClients(long[] id); 
    public int changeClientStatus(long[] id, ClientStatus status);
    public void saveClient(ClientInfo info);
    public ClientInfo loadClient(long clientId);
    public int getClientsCount(String firstNameFilter, ClientStatus clientStatusFilter); 
}
