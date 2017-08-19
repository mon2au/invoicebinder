/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.service;

import com.invoicebinder.client.service.client.ClientService;
import com.invoicebinder.server.dataaccess.ClientDAO;
import com.invoicebinder.shared.entity.client.Client;
import com.invoicebinder.shared.enums.client.ClientStatus;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("client")
public class ClientServiceImpl extends RemoteServiceServlet implements
        ClientService {
    @Autowired
    private ClientDAO clientDAO;
    
    @Override
    public ArrayList<ClientInfo> getAllClientsInfo(int start, int length, ArrayList<GridColSortInfo> sortList,
            String firstNameFilter, ClientStatus clientStatusFilter) {
        ArrayList<ClientInfo> allClientsInfo;
        ArrayList<Client> clientList;
        ClientInfo info;
        
        clientList = clientDAO.getAllClientsInfo(start, length, sortList, firstNameFilter, clientStatusFilter);
        allClientsInfo = new ArrayList<>();
        for(Client client : clientList) {
            info = new ClientInfo();
            info.setId(client.getId());
            info.setFirstName(client.getFirstName());
            info.setLastName(client.getLastName());
            if (client.getPhysicalAddress() != null) {
                info.setAddress1(client.getPhysicalAddress().getAddress1());
                info.setAddress2(client.getPhysicalAddress().getAddress2());
                info.setCity(client.getPhysicalAddress().getCity());
                info.setState(client.getPhysicalAddress().getState());
                info.setPostCode(client.getPhysicalAddress().getPostCode());                 
            }
            if (client.getContactDetails() != null) {
                info.setWorkPhone(client.getContactDetails().getWorkPhone());
                info.setHomePhone(client.getContactDetails().getHomePhone());
                info.setMobilePhone(client.getContactDetails().getMobilePhone());
                info.setFaxNumber(client.getContactDetails().getFaxNumber());
            }
            
            if (client.getEmailAddress() != null) {
                info.setEmailAddress(client.getEmailAddress().getEmailAddress());
            }
            
            allClientsInfo.add(info);
        }
        return allClientsInfo;
    }

    @Override
    public int deleteClients(long[] ids) {
        return clientDAO.deleteClients(ids);
    }

    @Override
    public int changeClientStatus(long[] ids, ClientStatus status) {
        return clientDAO.changeStatus(ids, status);
    }
    
    @Override
    public void saveClient(ClientInfo info) {
        clientDAO.saveClient(info);
    }

    @Override
    public ClientInfo loadClient(long clientId) {
        return clientDAO.loadClient(clientId);
    }

    @Override
    public int getClientsCount(String firstNameFilter, ClientStatus clientStatusFilter) {
        return clientDAO.getClientsCount(firstNameFilter, clientStatusFilter);
    }
}
