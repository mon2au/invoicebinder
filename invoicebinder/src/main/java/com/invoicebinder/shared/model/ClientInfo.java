package com.invoicebinder.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author mon2
 */
public class ClientInfo implements IsSerializable {
    
    private long id;
    private String firstName;
    private String lastName;
    private String homePhone;
    private String workPhone;
    private String mobilePhone;
    private String faxNumber;
    private String emailAddress;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String countryCode;
    private String postCode;
       
    public ClientInfo() {
    }
    
    public ClientInfo(long id) {
        this.id = id;
    }
    
    public ClientInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddressString() {
        String addrString = "";
        
        if (address1 != null) {
            addrString += address1;
        }
        
        if (address2 != null) {
            addrString += ", " + address2;
        }
        
        return  addrString;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCityStatePostcodeString() { 
        String longAddr = "";
        
         if (city != null) {
             longAddr += city;
         } 

         if (state != null) {
             longAddr += ", " + state;
         }         
         
         if (postCode != null) {
             longAddr += ", " + postCode;
         }         
        
        return longAddr;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
