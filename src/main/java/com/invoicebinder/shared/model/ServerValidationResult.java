/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.model;

import com.invoicebinder.shared.enums.ServiceResponseStatus;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author mon2
 */
public class ServerValidationResult implements IsSerializable {
    private ServiceResponseStatus status;
    private String message;

    public ServiceResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
