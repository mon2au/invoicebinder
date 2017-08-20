/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.entity.template;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mon2
 */
@Entity
@Table(name = "sitetemplatedata")
public class SiteTemplateData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String TemplateID;
    
    private String dataKey;
    
    private String dataValue;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getTemplateID() {
        return TemplateID;
    }
    
    public void setTemplateID(String TemplateID) {
        this.TemplateID = TemplateID;
    }
    
    public String getDataKey() {
        return dataKey;
    }
    
    public void setDataKey(String key) {
        this.dataKey = key;
    }
    
    public String getDataValue() {
        return dataValue;
    }
    
    public void setValue(String value) {
        this.dataValue = value;
    }
}
