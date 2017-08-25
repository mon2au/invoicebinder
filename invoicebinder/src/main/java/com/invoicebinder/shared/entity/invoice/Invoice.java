package com.invoicebinder.shared.entity.invoice;

import com.invoicebinder.shared.entity.client.Client;
import com.invoicebinder.shared.entity.item.Item;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the invoice database table.
 *
 */
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "invoiceNumber",unique=true)    
    private String invoiceNumber;
    private String purchOrdNumber;        
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;    
    @Temporal(TemporalType.DATE)
    private Date dueDate;    
    @Temporal(TemporalType.DATE)
    private Date paymentDate;    
    private String description;
    private String groupName;        
    private BigDecimal amount;    
    private String invoiceStatus;    
    private String type;    
    private String attr1;    
    private String attr2;    
    private String attr3;    
    private String attr4;        
    private String attr5;            
    private String attr6;                
    private String attr7;                    
    private String attr8;                        
    private String attr9;                            
    private String attr10;
                                
    // bi-directional many-to-one association to client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private Client client;
    private String authToken;
    
    // bi-directional many-to-many association to Item
    @ManyToMany
    @JoinTable(name = "invoiceitem", joinColumns = { @JoinColumn(name = "invoice_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
    private List<Item> items = new ArrayList<>();
    
    public Invoice() {
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Date getInvoiceDate() {
        return this.invoiceDate;
    }
    
    public void setInvoiceDate(Date date) {
        this.invoiceDate = date;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Client getClient() {
        return this.client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public List<Item> getItems() {
        return this.items;
    }
    
    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    } 

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    public String getAttr6() {
        return attr6;
    }

    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    public String getAttr7() {
        return attr7;
    }

    public void setAttr7(String attr7) {
        this.attr7 = attr7;
    }

    public String getAttr8() {
        return attr8;
    }

    public void setAttr8(String attr8) {
        this.attr8 = attr8;
    }

    public String getAttr9() {
        return attr9;
    }

    public void setAttr9(String attr9) {
        this.attr9 = attr9;
    }

    public String getAttr10() {
        return attr10;
    }

    public void setAttr10(String attr10) {
        this.attr10 = attr10;
    }

    public String getPurchOrdNumber() {
        return purchOrdNumber;
    }

    public void setPurchOrdNumber(String purchOrdNumber) {
        this.purchOrdNumber = purchOrdNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
