package com.invoicebinder.shared.entity.client;

import com.invoicebinder.shared.entity.address.Address;
import com.invoicebinder.shared.entity.contact.Contact;
import com.invoicebinder.shared.entity.contact.Email;
import com.invoicebinder.shared.entity.invoice.Invoice;
import com.invoicebinder.shared.entity.user.User;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the client database table.
 *
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String firstName;
    private String lastName;
    private String clientStatus;    
    
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @OneToOne
    private User userId;
        
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "addressId")
    private Address physicalAddress;
       
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "contactId")
    private Contact contactDetails;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "emailId")
    private Email emailAddress;
    
    // bi-directional many-to-one association to Invoice
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Invoice> invoices;
               
    public Client() {
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Address getPhysicalAddress() {
        return this.physicalAddress;
    }
    
    public void setPhysicalAddress(Address physicalAddress) {
        this.physicalAddress = physicalAddress;
    }
    
    public List<Invoice> getInvoices() {
        return this.invoices;
    }
    
    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Contact getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(Contact contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Email getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(Email emailAddress) {
        this.emailAddress = emailAddress;
    }
}