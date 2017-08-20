/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.server.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.server.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.autonum.AutoNum;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mon2
 */
@Repository
public class AutoNumDAO  extends BaseDAO<AutoNum,String> {

    public AutoNumDAO() {
        super(AutoNum.class);
    }
    
    @SuppressWarnings("unchecked")
    public String getAutoNum(String autoNumId) {
        
        String autoNum;
        AutoNum num;
       
        try {                                                                  
            num = (AutoNum) sf.getCurrentSession()
                    .createQuery(" from AutoNum a where a.id=:id ")
                    .setString("id", autoNumId).uniqueResult();            
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());            
        }
        
        autoNum = num.getNumPrefix() + num.getNumValue().toString() + 
                num.getNumSuffix();
        return autoNum;
    }    

    @SuppressWarnings("unchecked")
    public int incAutoNum(String autoNumId) {
        
        int result;
       
        try {                                                                  
            result = (int) sf.getCurrentSession()
                    
                    .createQuery("update AutoNum set numValue = numValue + 1" +
    				" where id = :id")
                    .setString("id", autoNumId).executeUpdate();            
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(AutoNumDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));
            throw new RuntimeException(e.getMessage());            
        }
        
        return result;
    }       
}
