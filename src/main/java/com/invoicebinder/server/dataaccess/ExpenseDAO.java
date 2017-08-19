/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.server.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.server.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.expense.Expense;
import com.invoicebinder.shared.misc.Calculations;
import com.invoicebinder.shared.model.ExpenseInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mon2
 */
@Repository
public class ExpenseDAO extends BaseDAO<Expense,Long>{
    
    public ExpenseDAO() {
        super(Expense.class);
    }
    @SuppressWarnings("unchecked")
    public ArrayList<Expense> getAllExpensesInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String expenseNoteFilter) {
        ArrayList<Expense> expenseList = null;
        String sql;        
        
        try {
            sql = " from Expense e where e.id >= :start ";
            
            if (!expenseNoteFilter.isEmpty()) {
                sql += "and e.note LIKE '%" + expenseNoteFilter + "%' ";
            }
            sql += "order by e.note ";            
            
            expenseList = (ArrayList<Expense>) sf.getCurrentSession()
                    .createQuery(sql)
                    .setParameter("start", (long) start)
                    .setMaxResults(length)                    
                    .list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ExpenseDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));             
            throw new RuntimeException(e.getMessage());            
        }
        
        return expenseList;
    }    
    
    @SuppressWarnings("unchecked")
    public int deleteExpense(long[] ids) {
        int result = 0;
        Expense expense;
        Session session;
        
        try {
            session = sf.getCurrentSession();

            for (int i=0;i<ids.length;i++) {

                expense = (Expense)session.get(Expense.class, ids[i]);
                session.delete(expense);
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public void saveExpense(ExpenseInfo info) {
        Expense expense = new Expense();
        Session session;
            
        try {
            session = sf.getCurrentSession();

            //fill receipt
            if (info.getId() != 0) {
                expense.setId(info.getId());
            }
            expense.setAmount(info.getAmount());
            expense.setNote(info.getNote());
            expense.setExpenseType(info.getExpenseType());
            expense.setExpenseDate(info.getExpenseDate());
            
            session.saveOrUpdate(expense);
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());            
        }
    }

    public ExpenseInfo loadExpense(long expenseId) {
        Expense expense;
        ExpenseInfo info = new ExpenseInfo();
        Session session;
        
        try {
            session = sf.getCurrentSession();

            expense = (Expense)session.get(Expense.class, expenseId);
            
            info.setAmount(expense.getAmount());
            info.setNote(expense.getNote());
            info.setExpenseType(expense.getExpenseType());
            info.setExpenseDate(expense.getExpenseDate());
            
            return info;
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());            
        }    
    }    

    public int getExpenseCount(String expenseNoteFilter) {
        Long value;
        int count = 0;
        String sql;      

        try {
            sql = "SELECT COUNT(e) FROM Expense e ";
            
            if (!expenseNoteFilter.isEmpty()) {
                sql += "where e.note LIKE '%" + expenseNoteFilter + "%' ";
            }
                    
            value = (Long) sf.getCurrentSession()
                    .createQuery(sql).uniqueResult();
            count = value.intValue();
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        return count;
    }
    
    public BigDecimal getExpeseLastMonth() {
        Session session;
        Object result;
        Query query;
        String sql;
        
        try {
            session = sf.getCurrentSession();
            sql = "select sum(amount) from Expense " +
                    "where expenseDate  >= :startDate " +
                    "and expenseDate <= :endDate";
            
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
            throw new RuntimeException(e.getMessage());
        }     
    }

    public BigDecimal getExpenseThisMonth() {
        Session session;
        Object result;
        Query query;
        String sql;
        
        try {
            session = sf.getCurrentSession();
            sql = "select sum(amount) from Expense " +
                    "where expenseDate  >= :startDate " +
                    "and expenseDate <= :endDate";
            
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
            throw new RuntimeException(e.getMessage());
        }
    }    

    public BigDecimal getExpenseYTD() {
        Session session;
        Object result;
        Query query;
        String sql;
        
        try {
            session = sf.getCurrentSession();
            sql = "select sum(amount) from Expense " +
                    "where expenseDate  >= :startYear " +
                    "and expenseDate <= :endYear";
            
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
            throw new RuntimeException(e.getMessage());
        }     
    }    
}
