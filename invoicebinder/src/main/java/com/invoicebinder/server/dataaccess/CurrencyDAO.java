/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import java.util.List;
import com.invoicebinder.shared.entity.currency.Currency;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mon2
 */
@Repository
public class CurrencyDAO extends BaseDAO<Currency,Long> {
    
    public CurrencyDAO() {
        super(Currency.class);
    }
    
    public List<Currency> getAllMatchingCurrencies(String match) {
        List<Currency> currencyList = null;
        
        try {
            currencyList = (List) sf.getCurrentSession()
                    .createQuery(" from Currency where code LIKE '%" + match + "%'").list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(CurrencyDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));
            throw new RuntimeException(e.getMessage());
        }
        
        return currencyList;
    }    
}
