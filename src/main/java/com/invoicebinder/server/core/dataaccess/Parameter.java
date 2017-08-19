/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.core.dataaccess;

/**
 *
 * @author mon2
 */
public class Parameter {
 String parameterName;
    Object parameterValue;
    ParameterType parameterType;

    public Parameter(String parameterName, Object parameterValue, ParameterType parameterType) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
        this.parameterType = parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Object getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Object parameterValue) {
        this.parameterValue = parameterValue;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }    
}
