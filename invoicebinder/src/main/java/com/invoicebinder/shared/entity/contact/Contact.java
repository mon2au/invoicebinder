package com.invoicebinder.shared.entity.contact;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the address database table.
 *
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "homePhone")
    private String homePhone;
    
    @Column(name = "workPhone")
    private String workPhone;
    
    @Column(name = "mobilePhone")
    private String mobilePhone;
    
    @Column(name = "faxNumber")
    private String faxNumber;
           
    public Contact() {
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homephone) {
        this.homePhone = homephone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workphone) {
        this.workPhone = workphone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobile) {
        this.mobilePhone = mobile;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }
}
