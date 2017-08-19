package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author mon2
 */
public class ValidationResult implements IsSerializable {
    private String message;
    private String tagname;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }    

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
}
