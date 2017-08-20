/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.server.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.server.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.city.City;
import com.invoicebinder.shared.entity.country.Country;
import com.invoicebinder.shared.entity.state.State;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mon2
 */
@Repository
public class CountryDAO extends BaseDAO<Country, Long> {
    
    public CountryDAO() {
        super(Country.class);
    }
    
    public List<City> getAllCities(String countryCode, String stateCode) {
        List<City> cityList = null;
        
        try {
            cityList = (List) sf.getCurrentSession()
                    .createQuery(" from City where country_code = :countrycode and state_code = :statecode")
                    .setParameter("countrycode", countryCode)
                    .setParameter("statecode", stateCode)
                    .list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(CurrencyDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return cityList;
    }
    public List<City> getAllMatchingCities(String match, String countryCode, String stateCode, int limit) {
        List<City> cityList = null;
        
        try {
            cityList = (List) sf.getCurrentSession()
                    .createQuery(" from City c where c.countryIsoCode = :countrycode and c.stateCode = :statecode " +
                            "and name %LIKE% '" + match + "'")
                    .setParameter("countrycode", countryCode)
                    .setParameter("statecode", stateCode)
                    .list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(CurrencyDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return cityList;
    }
    public List<Country> getAllCountries() {
        List<Country> countryList = null;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            countryList = (List) session.createQuery(" from Country ").list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(CountryDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return countryList;
    }
    public String getCountryName(String countryCode) {
        String countryName = "";
        String sql = "";
        
        try {
            sql = "select name from country c where c.countryIsoCode = :countrycode";
            
            countryName = (String) sf.getCurrentSession()
                    .createQuery(sql).setParameter("countrycode", countryCode)
                    .uniqueResult();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(CountryDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return countryName;
    }
    public List<State> getAllStatesForCountry(String countryCode) {
        List<State> stateList = null;
        
        try {
            stateList = (List) sf.getCurrentSession()
                    .createQuery("from State s where s.countryIsoCode = :countrycode")
                    .setParameter("countrycode", countryCode)
                    .list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(CountryDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));              
            throw new RuntimeException(e.getMessage());
        }
        
        return stateList;
    }
}
