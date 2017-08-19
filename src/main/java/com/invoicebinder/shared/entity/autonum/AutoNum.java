/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.entity.autonum;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author msushil
 */
@Entity
@Table(name = "autonum")
public class AutoNum implements Serializable {
    private static final long serialVersionUID = 1L;
        
    @Id
    private String id;
    
    private String numPrefix;
    
    private Long numValue;
    
    private String numSuffix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumPrefix() {
        return numPrefix;
    }

    public void setNumPrefix(String numPrefix) {
        this.numPrefix = numPrefix;
    }

    public Long getNumValue() {
        return numValue;
    }

    public void setNumValue(Long numValue) {
        this.numValue = numValue;
    }

    public String getNumSuffix() {
        return numSuffix;
    }

    public void setNumSuffix(String numSuffix) {
        this.numSuffix = numSuffix;
    }
}
