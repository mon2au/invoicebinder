package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.client.Client;
import com.invoicebinder.shared.entity.invoice.Invoice;
import com.invoicebinder.shared.enums.invoice.InvoiceStatusTypes;
import com.invoicebinder.shared.misc.Calculations;
import com.invoicebinder.shared.model.GridColSortInfo;
import org.springframework.stereotype.Repository;
import com.invoicebinder.shared.entity.item.Item;
import com.invoicebinder.shared.enums.invoice.InvoiceStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

@Repository
public class InvoiceDAO extends BaseDAO<Invoice, Long> {
    
    public InvoiceDAO() {
        super(Invoice.class);
    }

    @SuppressWarnings("unchecked")
    public Invoice authenticateInvoice(BigDecimal invoiceAmount, String token, String invoiceNumber) {
        Invoice invoice = null;
        Session session;

        session = sf.getCurrentSession();
        try {
            //authenticate with invoice
            invoice = (Invoice) session
                    .createQuery("from Invoice i where i.authToken=:authToken and i.invoiceNumber=:invoiceNumber and i.amount=:invoiceAmount")
                    .setString("authToken", token)
                    .setString("invoiceNumber", invoiceNumber)
                    .setBigDecimal("invoiceAmount", invoiceAmount).uniqueResult();

            //check if authentication successful
            if (invoice != null) {
                return invoice;
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));
            throw e;
        }

        return invoice;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Invoice> getAllInvoicesInfo(int start, int length, ArrayList<GridColSortInfo> sortList,
            String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter) {
        ArrayList<Invoice> invoiceList = null;
        String sql;
        
        try {
            sql = "Select i from Invoice i INNER JOIN i.client c where i.id >= :start ";
            
            if (!invoiceClientNameFilter.isEmpty()) {
                sql += "and c.firstName LIKE '%" + invoiceClientNameFilter + "%' ";
            }
            
            if (invoiceStatusFilter != InvoiceStatus.ALL) {
                sql += "and i.invoiceStatus = '" + invoiceStatusFilter.toString() + "' ";
            }      
            
            sql += "order by i.invoiceNumber ";
            
            invoiceList = (ArrayList<Invoice>) sf.getCurrentSession()
                    .createQuery(sql)
                    .setParameter("start", (long) start)
                    .setMaxResults(length).list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));             
            throw new RuntimeException(e.getMessage());
        }
        
        return invoiceList;
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Invoice> getAllOverdueInvoicesInfo(int start, int length, ArrayList<GridColSortInfo> sortList) {
        ArrayList<Invoice> invoiceList = null;
        String sql;
        
        try {
            sql = "Select i from Invoice i WHERE i.id >= :start AND ";
            
            for (int i = 0; i< InvoiceStatusTypes.getUnpaidStatuses().size(); i++) {
                sql += "i.invoiceStatus = '" + (String)InvoiceStatusTypes.getUnpaidStatuses().get(i) + "' ";
                if ((InvoiceStatusTypes.getUnpaidStatuses().size()>1) && (InvoiceStatusTypes.getUnpaidStatuses().size() - 1 != i)) {
                    sql += "OR ";
                }
            }            
            sql += " ";
            sql += "order by i.invoiceNumber ";
            
            invoiceList = (ArrayList<Invoice>) sf.getCurrentSession()
                    .createQuery(sql)
                    .setParameter("start", (long) start)
                    .setMaxResults(length).list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
        
        return invoiceList;
    }    
    
    @SuppressWarnings("unchecked")
    public ArrayList<Invoice> getOverdueInvoiceInfoForClient(long clientId) {
        ArrayList<Invoice> invoiceList = null;
        String sql;
        
        try {
            sql = "Select i from Invoice i INNER JOIN i.client c WHERE c.id = :clientId AND ";
            
            for (int i=0;i<InvoiceStatusTypes.getUnpaidStatuses().size(); i++) {
                sql += "i.invoiceStatus = '" + (String)InvoiceStatusTypes.getUnpaidStatuses().get(i) + "' ";
                if ((InvoiceStatusTypes.getUnpaidStatuses().size()>1) && (InvoiceStatusTypes.getUnpaidStatuses().size() - 1 != i)) {
                    sql += "OR ";
                }
            }            
            sql += " ";
            sql += "order by i.invoiceNumber ";
            
            invoiceList = (ArrayList<Invoice>) sf.getCurrentSession()
                    .createQuery(sql)
                    .setParameter("clientId", (long) clientId)
                    .list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
        
        return invoiceList;
    }      
    
    @SuppressWarnings("unchecked")
    public int deleteInvoices(long[] ids) {
        int result = 0;
        Invoice invoice;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            for (int i=0;i<ids.length;i++) {
                
                invoice = (Invoice)session.get(Invoice.class, ids[i]);
                session.delete(invoice);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public void saveInvoice(Invoice invoice) {
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            invoice.setClient((Client)session.get(Client.class, invoice.getClient().getId()));
            //save all items
            for(Item item : invoice.getItems()) {
                session.saveOrUpdate(item);
            }
            session.saveOrUpdate(invoice);
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public Invoice loadInvoice(long invoiceId) {
        Invoice invoice;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            invoice = (Invoice)session.get(Invoice.class, invoiceId);
            if (invoice.getClient() != null) Hibernate.initialize(invoice.getClient());
            return invoice;
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public int getInvoicesCount(String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter) {
        Long value;
        int count = 0;
        String sql;
        
        try {
            sql = "Select count(i) from Invoice i INNER JOIN i.client c where i.invoiceStatus IS NOT NULL ";
            
            if (invoiceStatusFilter != InvoiceStatus.ALL) {
                sql += "and i.invoiceStatus = '" + invoiceStatusFilter.toString() + "' ";
            }            
            
            if (!invoiceClientNameFilter.isEmpty()) {
                sql += "and c.firstName LIKE '%" + invoiceClientNameFilter + "%' ";
            }
            
            value = (Long) sf.getCurrentSession()
                    .createQuery(sql).uniqueResult();
            count = value.intValue();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
        
        return count;
    }
    
    public Boolean setInvoiceStatus(long id, InvoiceStatus invoiceStatus) {
        Boolean result = false;
        Invoice invoice;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            invoice = (Invoice)session.get(Invoice.class, id);
            invoice.setInvoiceStatus(invoiceStatus.toString());
            session.update(invoice);
            result = true;
            
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    
    public BigDecimal getIncomeYTD() {
        Session session;
        Object result;
        String sql;
        Query query;
        
        try {
            session = sf.getCurrentSession();
            sql = "select sum(i.amount) from Invoice i " +
                    "where i.invoiceDate  >= :startYear " +
                    "and i.invoiceDate <= :endYear";
            
            query = session.createQuery(sql)
                    .setParameter("startYear", Calculations.getFinancialYearStartDate())
                    .setParameter("endYear", Calculations.getFinancialYearEndDate());
            
            result = query.uniqueResult();
            if (result != null) {
                return (BigDecimal)result;
            }
            else {
                return new BigDecimal(0);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public BigDecimal getIncomeLastMonth() {
        Session session;
        Object result;
        Query query;        
        String sql;
        
        try {
            session = sf.getCurrentSession();
            sql = "select sum(i.amount) from Invoice i " +
                    "where i.invoiceDate  >= :startDate " +
                    "and i.invoiceDate <= :endDate";
            
            query = session.createQuery(sql)
                    .setParameter("startDate", Calculations.getLastMonthStartDate())
                    .setParameter("endDate", Calculations.getLastMonthEndDate());
            
            result = query.uniqueResult();
            if (result != null) {
                return (BigDecimal)result;
            }
            else {
                return new BigDecimal(0);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public BigDecimal getIncomeThisMonth() {
        Session session;
        Object result;
        Query query; 
        String sql;
        
        try {
            session = sf.getCurrentSession();
            sql = "select sum(amount) from Invoice " +
                    "where invoiceDate  >= :startDate " +
                    "and invoiceDate <= :endDate";
            
            query = session.createQuery(sql)
                    .setParameter("startDate", Calculations.getThisMonthStartDate())
                    .setParameter("endDate", Calculations.getThisMonthEndDate());
            result = query.uniqueResult();
            
            if (result != null) {
                return (BigDecimal)result;
            }
            else {
                return new BigDecimal(0);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public int getOverdueInvoicesCount() {
        Long value;
        int count = 0;
        String sql;
        
        try {
            sql = "Select count(i) from Invoice i WHERE ";
            
            for (int i=0;i<InvoiceStatusTypes.getUnpaidStatuses().size(); i++) {
                sql += "i.invoiceStatus = '" + (String)InvoiceStatusTypes.getUnpaidStatuses().get(i) + "' ";
                if ((InvoiceStatusTypes.getUnpaidStatuses().size()>1) && (InvoiceStatusTypes.getUnpaidStatuses().size() - 1 != i)) {
                    sql += "OR ";
                }
            }
            
            
            value = (Long) sf.getCurrentSession()
                    .createQuery(sql).uniqueResult();
            count = value.intValue();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(InvoiceDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
        
        return count;
    }
}
