/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.core.dataaccess;

import java.util.List;

/**
 *
 * @author mon2
 */
public class StoredProcResult {
    List<Parameter> outputParams;
    Parameter returnParam;

    public List<Parameter> getOutputParams() {
        return outputParams;
    }

    public void setOutputParams(List<Parameter> outputParams) {
        this.outputParams = outputParams;
    }

    public Parameter getReturnParam() {
        return returnParam;
    }

    public void setReturnParam(Parameter returnParam) {
        this.returnParam = returnParam;
    }
}
