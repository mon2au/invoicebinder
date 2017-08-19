/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.shared.model;

import com.invoicebinder.shared.enums.config.CustomAttribConfigItems;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mon2
 */
public class InvoiceInfo implements IsSerializable {
    private long id;
    private Long clientId;
    private String invoiceNumber;
    private String description;
    private Date invoiceDate;
    private Date dueDate;
    private String purchOrdNumber;
    private String groupName;
    private BigDecimal amount;
    private String invoiceStatus;
    private List<ItemInfo> itemList;
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
    private String authToken;
    private ClientInfo clientInfo;
    private Boolean markPaid;
    private Date paymentDate;
    private Boolean sendEmail;     

    public InvoiceInfo() {
    }  

    public String getClientName() {
        String clientName = "";
        
        if (this.clientInfo != null) {
            clientName += this.clientInfo.getFirstName();
            
            if (this.clientInfo.getLastName() != null) {
                clientName += " " + this.clientInfo.getLastName();
            }
        }
        return clientName;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<ItemInfo> getItemList() {
        return itemList;
    }
    
    public void setItemList(List<ItemInfo> itemList) {
        this.itemList = itemList;
    }
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date date) {
        this.invoiceDate = date;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPurchOrdNumber() {
        return purchOrdNumber;
    }

    public void setPurchOrdNumber(String purchOrdNumber) {
        this.purchOrdNumber = purchOrdNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
    
    public String getAttribute(CustomAttribConfigItems item) {
            String attribString = null;
            
        switch (item) {
            case ATTR1: attribString = attr1;
                break;
            case ATTR2: attribString = attr2;
                break;
            case ATTR3: attribString = attr3;
                break;
            case ATTR4: attribString = attr4;
                break;
            case ATTR5: attribString = attr5;
                break;
            case ATTR6: attribString = attr6;
                break;
            case ATTR7: attribString = attr7;
                break;
            case ATTR8: attribString = attr8;
                break;
            case ATTR9: attribString = attr9;
                break;
            case ATTR10: attribString = attr10;
                break;                
        } 
        
        return attribString;
    }

    public Boolean isMarkPaid() {
        return markPaid;
    }

    public void setMarkPaid(Boolean markPaid) {
        this.markPaid = markPaid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
}
