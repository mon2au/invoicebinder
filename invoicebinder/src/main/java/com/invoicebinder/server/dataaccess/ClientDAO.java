package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.address.Address;
import com.invoicebinder.shared.entity.client.Client;
import com.invoicebinder.shared.entity.contact.Contact;
import com.invoicebinder.shared.entity.invoice.Invoice;
import com.invoicebinder.shared.entity.item.Item;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import org.springframework.stereotype.Repository;
import com.invoicebinder.shared.enums.client.ClientStatus;
import com.invoicebinder.shared.entity.contact.Email;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

@Repository
public class ClientDAO extends BaseDAO<Client,Long>{
    
    public ClientDAO() {
        super(Client.class);
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Client> getAllClientsInfo(int start, int length,
            ArrayList<GridColSortInfo> sortList,
            String firstNameFilter,
            ClientStatus clientStatusFilter) {
        ArrayList<Client> clientList = null;
        String sql;
        
        try {
            sql = " from Client c where c.id >= :start ";
            
            if (!firstNameFilter.isEmpty()) {
                sql += "and c.firstName LIKE '%" + firstNameFilter + "%' ";
            }
            
            if (clientStatusFilter != ClientStatus.ALL) {
                sql += "and c.clientStatus = '" + clientStatusFilter.toString() + "' ";
            }
            
            sql += "order by c.firstName ";
            
            clientList = (ArrayList<Client>) sf.getCurrentSession()
                    .createQuery(sql)
                    .setParameter("start", (long) start)
                    .setMaxResults(length).list();
            for(Client c : clientList) {
                if (c.getPhysicalAddress() != null) Hibernate.initialize(c.getPhysicalAddress());
                if (c.getContactDetails()!= null) Hibernate.initialize(c.getContactDetails());
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ClientDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return clientList;
    }
    
    @SuppressWarnings("unchecked")
    public int deleteClients(long[] ids) {
        int result = 0;
        Client client;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            for (int i=0;i<ids.length;i++) {
                
                client = (Client)session.get(Client.class, ids[i]);
                //delete contact
                if (client.getContactDetails() != null) {
                    session.delete(client.getContactDetails());
                }
                //delete email
                if (client.getEmailAddress() != null) {
                    session.delete(client.getEmailAddress());
                }
                //delete invoices
                if (client.getInvoices() != null) {
                    for(Invoice inv : client.getInvoices()) {
                        //delete items
                        if (inv.getItems() != null) {
                            for(Item item : inv.getItems()) {
                                session.delete(item);
                            }
                        }
                        session.delete(inv);
                    }
                }
                //delete client
                session.delete(client);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ClientDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public int changeStatus(long[] ids, ClientStatus status) {
        int result = 0;
        Client client;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            for (int i=0;i<ids.length;i++) {
                
                client = (Client)session.get(Client.class, ids[i]);
                client.setClientStatus(status.toString());
                session.saveOrUpdate(client);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ClientDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public int updateClient(long id, Client client) {
        int result = 0;
        
        try {
            Query query = sf.getCurrentSession()
                    .createQuery("update client set firstname = :firstname" +
                            " where id = :id");
            query.setParameter("firstname", client.getFirstName());
            query.setParameter("id", id);
            result = query.executeUpdate();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ClientDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public void saveClient(ClientInfo info) {
        Client client = new Client();
        Address addr = null;
        Contact cont = null;
        Email mail;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            //set id if this is an update.
            if (info.getId() != 0) { client.setId(info.getId()); }
            //set all mandatory fields
            client.setFirstName(info.getFirstName());
            client.setClientStatus(ClientStatus.ACTIVE.toString());
            //fill address
            if (!info.getAddress1().isEmpty()) {
                addr = new Address();
                addr.setAddress1(info.getAddress1());
                addr.setAddress2(info.getAddress2());
                addr.setCity(info.getCity());
                addr.setState(info.getState());
                addr.setCountryCode(info.getCountryCode());
                addr.setPostCode(info.getPostCode());
            }
            if (addr != null) client.setPhysicalAddress(addr);
            //fill contact details.
            if (!info.getHomePhone().isEmpty() ||
                    !info.getWorkPhone().isEmpty() ||
                    !info.getMobilePhone().isEmpty() ||
                    !info.getFaxNumber().isEmpty()) {
                cont = new Contact();
                cont.setHomePhone(info.getHomePhone());
                cont.setWorkPhone(info.getWorkPhone());
                cont.setMobilePhone(info.getMobilePhone());
                cont.setFaxNumber(info.getFaxNumber());
            }
            if (cont != null) client.setContactDetails(cont);
            //email
            if (!info.getEmailAddress().isEmpty()) {
                mail = new Email();
                mail.setEmailAddress(info.getEmailAddress());
                client.setEmailAddress(mail);
            }
            //lastname
            if (!info.getLastName().isEmpty()) client.setLastName(info.getLastName());
            
            session.saveOrUpdate(client);
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ClientDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public ClientInfo loadClient(long clientId) {
        Client client;
        ClientInfo info = new ClientInfo();
        Session session;
        try {
            session = sf.getCurrentSession();
            
            client = (Client)session.get(Client.class, clientId);
            
            info.setFirstName(client.getFirstName());
            info.setLastName(client.getLastName());
            info.setId(client.getId());
            if (client.getPhysicalAddress() != null) {
                info.setAddress1(client.getPhysicalAddress().getAddress1());
                info.setAddress2(client.getPhysicalAddress().getAddress2());
                info.setCity(client.getPhysicalAddress().getCity());
                info.setState(client.getPhysicalAddress().getState());
                info.setPostCode(client.getPhysicalAddress().getPostCode());
                info.setCountryCode(client.getPhysicalAddress().getCountryCode());
            }

            if (client.getContactDetails() != null) {
                info.setHomePhone(client.getContactDetails().getHomePhone());
                info.setWorkPhone(client.getContactDetails().getWorkPhone());
                info.setMobilePhone(client.getContactDetails().getMobilePhone());
                info.setFaxNumber(client.getContactDetails().getFaxNumber());
            }
            if (client.getEmailAddress() != null) {
                info.setEmailAddress(client.getEmailAddress().getEmailAddress());
            }
            return info;
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ClientDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public List<Client> getAllMatchingClients(String match) {
        List<Client> clientList = null;
        
        try {
            clientList = (List) sf.getCurrentSession()
                    .createQuery(" from Client where firstname LIKE '%" + match + "%'").list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ClientDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        return clientList;
    }
    
    public int getClientsCount(String firstNameFilter, ClientStatus clientStatusFilter) {
        Long value;
        int count = 0;
        String sql;
        
        try {
            sql = "SELECT COUNT(c) FROM Client c where c.firstName IS NOT NULL ";
            
            if (clientStatusFilter != ClientStatus.ALL) {
                sql += "and c.clientStatus = '" + clientStatusFilter.toString() + "' ";
            }
            
            if (!firstNameFilter.isEmpty()) {
                sql += "and c.firstName LIKE '%" + firstNameFilter + "%' ";
            }
            
            value = (Long) sf.getCurrentSession()
                    .createQuery(sql).uniqueResult();
            count = value.intValue();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ClientDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return count;
    }
}
