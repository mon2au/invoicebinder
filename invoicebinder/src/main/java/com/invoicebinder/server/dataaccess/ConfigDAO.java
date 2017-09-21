/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.invoicebindercore.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.invoicebindercore.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.config.Configuration;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.model.ConfigData;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


/**
 *
 * @author mon2
 */
@Repository
public class ConfigDAO extends BaseDAO<Configuration,Long>{
    
    public ConfigDAO() {
        super(Configuration.class);
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<ConfigData> getConfigData(ConfigurationSection section) {
    	ArrayList<ConfigData> configList = new ArrayList<>();
        ConfigData data;
        List<Configuration> rawList;
        
        try {
            rawList = (ArrayList<Configuration>) sf.getCurrentSession()
                    .createQuery(" from Configuration c where c.configSection=:section ")
                    .setString("section", section.toString()).list();
            
            for (Configuration item: rawList){
                data = new ConfigData(item.getId(), item.getConfigKey(), item.getConfigSection(), item.getValue(), item.getDataType(), item.getMaxLength());
                configList.add(data);
            }
            
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ConfigDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));               
            throw new RuntimeException(e.getMessage());
        }
        
        return configList;
    }
    
    @SuppressWarnings("unchecked")
    public List<ConfigData> getConfigData() {
        List<ConfigData> configList = new ArrayList<>();
        ConfigData data;
        List<Configuration> rawList;
        
        try {
            rawList = (ArrayList<Configuration>) sf.getCurrentSession()
                    .createQuery(" from Configuration ").list();
            
            for (Configuration item: rawList){
                data = new ConfigData(item.getId(), item.getConfigKey(), item.getConfigSection(), item.getValue(), item.getDataType(), item.getMaxLength());
                configList.add(data);
            }
            
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ConfigDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));             
            throw new RuntimeException(e.getMessage());
        }
        
        return configList;
    }
    
    @SuppressWarnings("unchecked")
    public void setConfigData(List<Configuration> data, ConfigurationSection section) {
        Session session;
        
        session = sf.getCurrentSession();
        
        try {
            for (Configuration item: data){
                //delete and recreate config
                session.createQuery("delete Configuration c where c.configSection=:section and c.configKey=:key")
                        .setString("section", section.toString())
                        .setString("key", item.getConfigKey())
                        .executeUpdate();
                
                session.save(item);
            }
            
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(ConfigDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));             
            throw new RuntimeException(e.getMessage());
        }
    }
}
