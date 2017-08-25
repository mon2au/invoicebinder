package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.contact.Contact;
import com.invoicebinder.shared.entity.invoice.Invoice;
import com.invoicebinder.shared.entity.payment.Payment;
import org.springframework.stereotype.Repository;
import com.invoicebinder.shared.entity.client.Client;
import com.invoicebinder.shared.entity.contact.Email;
import com.invoicebinder.shared.enums.invoice.InvoiceStatus;
import com.invoicebinder.shared.enums.payment.PaymentStatus;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.PaymentInfo;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@Repository
public class PaymentDAO extends BaseDAO<Client,Long>{
    
    public PaymentDAO() {
        super(Client.class);
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Payment> getAllPaymentsInfo(int start, int length,
                                                 ArrayList<GridColSortInfo> sortList,
                                                 String filter,
                                                 PaymentStatus status) {
        ArrayList<Payment> paymentList = null;
        String sql;
        
        try {
            sql = " from Payment p where p.id >= :start ";
            
            if (!filter.isEmpty()) {
                sql += "and p.referenceNo LIKE '%" + filter + "%' ";
            }
            
            if (status != PaymentStatus.ALL) {
                sql += "and p.paymentStatus = '" + status.toString() + "' ";
            }
            
            sql += "order by p.referenceNo ";
            
            paymentList = (ArrayList<Payment>) sf.getCurrentSession()
                    .createQuery(sql)
                    .setParameter("start", (long) start)
                    .setMaxResults(length).list();
            //for(Payment p : paymentList) {
            //    if (c.getPhysicalAddress() != null) Hibernate.initialize(c.getPhysicalAddress());
            //    if (c.getContactDetails()!= null) Hibernate.initialize(c.getContactDetails());
            //}
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(PaymentDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return paymentList;
    }
    
    @SuppressWarnings("unchecked")
    public void savePayment(PaymentInfo info) {
        Payment payment = new Payment();
        Client client = null;
        Contact cont = null;
        Email mail;
        Invoice invoice;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            //set id if this is an update.
            if (info.getId() != 0) { payment.setId(info.getId()); }
            //set all mandatory fields
            payment.setClient((Client)session.get(Client.class, info.getClientInfo().getId()));
            payment.setAmount(info.getAmount());
            payment.setCurrencyCode(info.getCurrencyCode());
            payment.setNote(info.getNote());
            payment.setPaymentDate(info.getPaymentDate());
            payment.setPaymentStatus(info.getPaymentStatus());
            payment.setPaymentType(info.getPaymentType());
            payment.setProviderRef(info.getProviderRef());
            payment.setReferenceNo(info.getReferenceNo());
            
            session.saveOrUpdate(payment);
            
            if (info.getInvoiceIds() != null) {
                for (Long id: info.getInvoiceIds()) {
                    invoice = (Invoice)session.get(Invoice.class, id);
                    invoice.setInvoiceStatus(InvoiceStatus.PAID.toString());
                    session.saveOrUpdate(invoice);
                }
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(PaymentDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public PaymentInfo loadPayment(long paymentId) {
        Payment payment;
        PaymentInfo info = new PaymentInfo();
        Session session;
        try {
            session = sf.getCurrentSession();
            
            payment = (Payment)session.get(Payment.class, paymentId);
            
            info.setAmount(payment.getAmount());
            info.setPaymentDate(payment.getPaymentDate());
            info.setId(payment.getId());
            return info;
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(PaymentDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public PaymentInfo loadPaymentForClient(long clientId) {
        Payment payment;
        PaymentInfo info = new PaymentInfo();
        Session session;
        try {
            session = sf.getCurrentSession();
            
            payment = (Payment)session.get(Payment.class, clientId);
            
            info.setAmount(payment.getAmount());
            info.setPaymentDate(payment.getPaymentDate());
            info.setId(payment.getId());
            return info;
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(PaymentDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public int getPaymentsCount(String filter, PaymentStatus status) {
        Long value;
        int count = 0;
        String sql;
        
        try {
            sql = "SELECT COUNT(p) FROM Payment p where p.amount IS NOT NULL ";
            
            if (status != PaymentStatus.ALL) {
                sql += "and p.paymentStatus = '" + status.toString() + "' ";
            }
            
            if (!filter.isEmpty()) {
                sql += "and p.referenceNo LIKE '%" + filter + "%' ";
            }
            
            value = (Long) sf.getCurrentSession()
                    .createQuery(sql).uniqueResult();
            count = value.intValue();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(PaymentDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return count;
    }
}
