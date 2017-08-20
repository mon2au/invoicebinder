package com.invoicebinder.client.service.client;

import com.invoicebinder.shared.enums.client.ClientStatus;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;

/**
 *
 * @author mon2
 */
public interface ClientServiceClientInt {
    void getAllClients(int start, int length, ArrayList<GridColSortInfo> sortList, String filter,
                       ClientStatus clientStatusFilter, AsyncDataProvider<ClientInfo> provider);
    void deleteClients(long[] ids);
    void changeClientStatus(long[] ids, ClientStatus status);  
    void saveClient(ClientInfo info);
    void loadClient(long clientId);
    void getClientsCount(String firstNameFilter, ClientStatus clientStatusFilter);
    void isClientsTableEmpty();
}
