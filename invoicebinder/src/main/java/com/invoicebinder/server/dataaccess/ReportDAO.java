/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mon2
 */
@Repository
public class ReportDAO extends BaseDAO {
    
    public ReportDAO() {
        super();
    }
    
    public List getIncomeAndExpense(int startMonth, int startYear, int totalMonths) {
        Session session;
        List result = null;
     
        try {
            session = sf.getCurrentSession();
            
            result = session.createSQLQuery("call get_rptIncomeAndExpense(:startMonth, :startYear, :totalMonths)")
                    .setParameter("startMonth", startMonth)
                    .setParameter("startYear", startYear)
                    .setParameter("totalMonths", totalMonths)
                    .list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ReportDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                    
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public List getSalesAndPayment(int startMonth, int startYear, int totalMonths) {
        Session session;
        List result = null;
        
        try {
            session = sf.getCurrentSession();
            
            result = session.createSQLQuery("call get_rptSalesAndPayments(:startMonth, :startYear, :totalMonths)")
                    .setParameter("startMonth", startMonth)
                    .setParameter("startYear", startYear)
                    .setParameter("totalMonths", totalMonths)
                    .list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ReportDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
}
