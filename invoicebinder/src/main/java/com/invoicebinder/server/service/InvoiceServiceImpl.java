/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.server.service;

import com.invoicebinder.client.service.invoice.InvoiceService;
import com.invoicebinder.server.dataaccess.AutoNumDAO;
import com.invoicebinder.server.dataaccess.InvoiceDAO;
import com.invoicebinder.shared.entity.client.Client;
import com.invoicebinder.shared.entity.invoice.Invoice;
import com.invoicebinder.shared.entity.item.Item;
import com.invoicebinder.shared.enums.autonum.AutoNumType;
import com.invoicebinder.shared.enums.invoice.InvoiceStatus;
import com.invoicebinder.shared.model.ClientInfo;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.InvoiceInfo;
import com.invoicebinder.shared.model.ItemInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.invoicebinder.server.dataaccess.ConfigDAO;
import com.invoicebinder.shared.enums.config.ApplicationSettingsItems;
import com.invoicebinder.shared.enums.config.BusinessConfigItems;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.config.CustomAttribConfigItems;
import com.invoicebinder.shared.enums.config.EmailConfigItems;
import com.invoicebinder.shared.enums.config.InvoiceTemplateConfigItems;
import com.invoicebinder.shared.enums.invoice.InvoiceTemplate;
import static com.invoicebinder.shared.misc.Utils.convertNullToBlank;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.ViewInvoiceInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mon2
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("invoice")
public class InvoiceServiceImpl extends RemoteServiceServlet implements
        InvoiceService {
    @Autowired
    private InvoiceDAO invoiceDAO;
    @Autowired
    private AutoNumDAO autonumDAO;
    @Autowired
    private ConfigDAO configDAO;
    
    @Override
    public ArrayList<InvoiceInfo> getAllInvoicesInfo(int start, int length, 
            ArrayList<GridColSortInfo> sortList,
            String invoiceClientNameFilter,
            InvoiceStatus invoiceStatusFilter) {
        ArrayList<InvoiceInfo> allInvoiceInfo = new ArrayList<>();
        ArrayList<Invoice> invoiceList;
        InvoiceInfo info;
        ClientInfo client;
        
        invoiceList = invoiceDAO.getAllInvoicesInfo(start, length, sortList, 
                invoiceClientNameFilter, invoiceStatusFilter);
        
        for(Invoice invoice : invoiceList) {
            info = new InvoiceInfo();
            info.setId(invoice.getId());
            info.setInvoiceNumber(invoice.getInvoiceNumber());
            info.setDescription(invoice.getDescription());
            info.setInvoiceDate(invoice.getInvoiceDate());
            info.setDueDate(invoice.getDueDate());
            info.setInvoiceStatus(invoice.getInvoiceStatus());
            info.setAmount(invoice.getAmount());
            //client
            client = new ClientInfo(invoice.getClient().getFirstName(), invoice.getClient().getLastName());
            info.setClientInfo(client);
            
            allInvoiceInfo.add(info);
        }
        return allInvoiceInfo;
    }
    
    @Override
    public ArrayList<InvoiceInfo> getAllOverdueInvoicesInfo(int start, int length, ArrayList<GridColSortInfo> sortList) {
        ArrayList<InvoiceInfo> allInvoiceInfo = new ArrayList<>();
        ArrayList<Invoice> invoiceList;
        InvoiceInfo info;
        ClientInfo client;
        
        invoiceList = invoiceDAO.getAllOverdueInvoicesInfo(start, length, sortList);
        
        for(Invoice invoice : invoiceList) {
            info = new InvoiceInfo();
            info.setId(invoice.getId());
            info.setInvoiceNumber(invoice.getInvoiceNumber());
            info.setDescription(invoice.getDescription());
            info.setInvoiceDate(invoice.getInvoiceDate());
            info.setDueDate(invoice.getDueDate());
            info.setInvoiceStatus(invoice.getInvoiceStatus());
            //client
            client = new ClientInfo(invoice.getClient().getFirstName(), invoice.getClient().getLastName());
            info.setClientInfo(client);
            
            allInvoiceInfo.add(info);
        }
        return allInvoiceInfo;
    }    
    
    @Override
    public int deleteInvoices(long[] ids) {
        return invoiceDAO.deleteInvoices(ids);
    }
    
    @Override
    public long saveInvoice(InvoiceInfo invoiceInfo) {
        Invoice invoice = new Invoice();
        Client client;
        Item item;
        ArrayList<Item> itemList;
        
        if (invoiceInfo.getId() != 0) {
            invoice.setId(invoiceInfo.getId());
        }
        //set client.
        if (invoiceInfo.getClientId() != null) {
            client = new Client();
            client.setId(invoiceInfo.getClientId());
            invoice.setClient(client);
        }
        
        invoice.setInvoiceDate(invoiceInfo.getInvoiceDate());
        invoice.setDueDate(invoiceInfo.getDueDate());
        invoice.setInvoiceNumber(invoiceInfo.getInvoiceNumber());
        invoice.setInvoiceStatus(InvoiceStatus.DRAFT.toString());
        invoice.setGroupName(invoiceInfo.getGroupName());
        invoice.setType(invoiceInfo.getType());
        invoice.setPurchOrdNumber(invoiceInfo.getPurchOrdNumber());
        
        //set invoice items
        if (invoiceInfo.getItemList().size() > 0) {
            itemList = new ArrayList<>();
            for(ItemInfo itemInfo : invoiceInfo.getItemList()) {
                item = new Item();
                item.setName(itemInfo.getName());
                item.setDescription(itemInfo.getDesc());
                item.setPrice(itemInfo.getUnitPrice());
                item.setQty(itemInfo.getItemQty());
                itemList.add(item);
            }
            invoice.setItems(itemList);
        }
        
        //set attributes
        invoice.setAttr1(invoiceInfo.getAttr1());
        invoice.setAttr2(invoiceInfo.getAttr2());
        invoice.setAttr3(invoiceInfo.getAttr3());
        invoice.setAttr4(invoiceInfo.getAttr4());
        invoice.setAttr5(invoiceInfo.getAttr5());
        invoice.setAttr6(invoiceInfo.getAttr6());
        invoice.setAttr7(invoiceInfo.getAttr7());
        invoice.setAttr8(invoiceInfo.getAttr8());
        invoice.setAttr9(invoiceInfo.getAttr9());
        invoice.setAttr10(invoiceInfo.getAttr10());
        invoice.setDescription(invoiceInfo.getDescription());       
        if (invoiceInfo.isMarkPaid()) {
            invoice.setInvoiceStatus(InvoiceStatus.PAID.toString());
            invoice.setPaymentDate(invoiceInfo.getPaymentDate());
        } 
        invoice.setAmount(invoiceInfo.getAmount());
        invoice.setAuthToken(UUID.randomUUID().toString());
        
        invoiceDAO.saveInvoice(invoice);
        autonumDAO.incAutoNum(AutoNumType.INVOICE.toString());

        //TODO - to be implimented.
        //if (invoiceInfo.isSendEmail()) {
        //}        
        return invoice.getId();
    }
    
    @Override
    public InvoiceInfo loadInvoice(long invoiceId) {
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        Invoice inv;
        
        inv = invoiceDAO.loadInvoice(invoiceId);
        invoiceInfo.setInvoiceNumber(inv.getInvoiceNumber());
        invoiceInfo.setDescription(inv.getDescription());
        invoiceInfo.setInvoiceDate(inv.getInvoiceDate());
        invoiceInfo.setDueDate(inv.getDueDate());
        invoiceInfo.setInvoiceStatus(inv.getInvoiceStatus());
        invoiceInfo.setAttr1(inv.getAttr1());
        invoiceInfo.setAttr2(inv.getAttr2());
        invoiceInfo.setAttr3(inv.getAttr3());
        invoiceInfo.setAttr4(inv.getAttr4());
        invoiceInfo.setAttr5(inv.getAttr5());
        invoiceInfo.setAttr6(inv.getAttr6());
        invoiceInfo.setAttr7(inv.getAttr7());
        invoiceInfo.setAttr8(inv.getAttr8());
        invoiceInfo.setAttr9(inv.getAttr9());
        invoiceInfo.setAttr10(inv.getAttr10());
        invoiceInfo.setAuthToken(inv.getAuthToken());
        invoiceInfo.setGroupName(inv.getGroupName());
        invoiceInfo.setPurchOrdNumber(inv.getPurchOrdNumber());
        invoiceInfo.setType(inv.getType());
        //invoice items
        invoiceInfo.setItemList(new ArrayList<>());
        for(Item item : inv.getItems()) {
            invoiceInfo.getItemList().add(new ItemInfo(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getQty()));
        }
        //invoice client
        if (inv.getClient() != null) {
            invoiceInfo.setClientInfo(new ClientInfo());
            invoiceInfo.setClientId(inv.getClient().getId());
            if (inv.getClient().getPhysicalAddress() != null) {
                invoiceInfo.getClientInfo().setAddress1(inv.getClient().getPhysicalAddress().getAddress1());
                invoiceInfo.getClientInfo().setAddress2(inv.getClient().getPhysicalAddress().getAddress2());
                invoiceInfo.getClientInfo().setCity(inv.getClient().getPhysicalAddress().getCity());
                invoiceInfo.getClientInfo().setState(inv.getClient().getPhysicalAddress().getState());
                invoiceInfo.getClientInfo().setPostCode(inv.getClient().getPhysicalAddress().getPostCode());
            }
            invoiceInfo.getClientInfo().setFirstName(inv.getClient().getFirstName());
            invoiceInfo.getClientInfo().setLastName(inv.getClient().getLastName());
            if (inv.getClient().getEmailAddress() != null) {
                invoiceInfo.getClientInfo().setEmailAddress(inv.getClient().getEmailAddress().getEmailAddress());
            }
        }        
        return invoiceInfo;
    }

    @Override
    public int getInvoicesCount(String invoiceClientNameFilter, InvoiceStatus invoiceStatusFilter) {
        return invoiceDAO.getInvoicesCount(invoiceClientNameFilter, invoiceStatusFilter);
    }
    
    @Override
    public int getOverdueInvoicesCount() {
        return invoiceDAO.getOverdueInvoicesCount();
    }    

    @Override
    public Boolean setInvoiceStatus(long id, InvoiceStatus invoiceStatus) {
        return invoiceDAO.setInvoiceStatus(id, invoiceStatus);
    }

    @Override
    public ViewInvoiceInfo loadViewInvoice(long invoiceId) {
        ViewInvoiceInfo viewInvoiceInfo = new ViewInvoiceInfo();
        Invoice inv;
        ArrayList<ConfigData> configData;
        HashMap<String, String> configDataHash;
        
        inv = invoiceDAO.loadInvoice(invoiceId);
        viewInvoiceInfo.getInvoiceInfo().setInvoiceNumber(inv.getInvoiceNumber());
        viewInvoiceInfo.getInvoiceInfo().setDescription(inv.getDescription());
        viewInvoiceInfo.getInvoiceInfo().setInvoiceDate(inv.getInvoiceDate());
        viewInvoiceInfo.getInvoiceInfo().setDueDate(inv.getDueDate());
        viewInvoiceInfo.getInvoiceInfo().setInvoiceStatus(inv.getInvoiceStatus());
        viewInvoiceInfo.getInvoiceInfo().setAttr1(inv.getAttr1());
        viewInvoiceInfo.getInvoiceInfo().setAttr2(inv.getAttr2());
        viewInvoiceInfo.getInvoiceInfo().setAttr3(inv.getAttr3());
        viewInvoiceInfo.getInvoiceInfo().setAttr4(inv.getAttr4());
        viewInvoiceInfo.getInvoiceInfo().setAttr5(inv.getAttr5());
        viewInvoiceInfo.getInvoiceInfo().setAttr6(inv.getAttr6());
        viewInvoiceInfo.getInvoiceInfo().setAttr7(inv.getAttr7());
        viewInvoiceInfo.getInvoiceInfo().setAttr8(inv.getAttr8());
        viewInvoiceInfo.getInvoiceInfo().setAttr9(inv.getAttr9());
        viewInvoiceInfo.getInvoiceInfo().setAttr10(inv.getAttr10());
        viewInvoiceInfo.getInvoiceInfo().setAuthToken(inv.getAuthToken());
        viewInvoiceInfo.getInvoiceInfo().setGroupName(inv.getGroupName());
        viewInvoiceInfo.getInvoiceInfo().setPurchOrdNumber(inv.getPurchOrdNumber());
        viewInvoiceInfo.getInvoiceInfo().setType(inv.getType());
        viewInvoiceInfo.getInvoiceInfo().setAmount(inv.getAmount());
        //invoice items
        viewInvoiceInfo.getInvoiceInfo().setItemList(new ArrayList<>());
        for(Item item : inv.getItems()) {
            viewInvoiceInfo.getInvoiceInfo().getItemList().add(new ItemInfo(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getQty()));
        }
        //invoice client
        if (inv.getClient() != null) {
            viewInvoiceInfo.getInvoiceInfo().setClientInfo(new ClientInfo());
            viewInvoiceInfo.getInvoiceInfo().setClientId(inv.getClient().getId());
            if (inv.getClient().getPhysicalAddress() != null) {
                viewInvoiceInfo.getInvoiceInfo().getClientInfo().setAddress1(inv.getClient().getPhysicalAddress().getAddress1());
                viewInvoiceInfo.getInvoiceInfo().getClientInfo().setAddress2(inv.getClient().getPhysicalAddress().getAddress2());
                viewInvoiceInfo.getInvoiceInfo().getClientInfo().setCity(inv.getClient().getPhysicalAddress().getCity());
                viewInvoiceInfo.getInvoiceInfo().getClientInfo().setState(inv.getClient().getPhysicalAddress().getState());
                viewInvoiceInfo.getInvoiceInfo().getClientInfo().setPostCode(inv.getClient().getPhysicalAddress().getPostCode());
            }
            viewInvoiceInfo.getInvoiceInfo().getClientInfo().setFirstName(inv.getClient().getFirstName());
            viewInvoiceInfo.getInvoiceInfo().getClientInfo().setLastName(inv.getClient().getLastName());
            if (inv.getClient().getEmailAddress() != null) {
                viewInvoiceInfo.getInvoiceInfo().getClientInfo().setEmailAddress(inv.getClient().getEmailAddress().getEmailAddress());
            }
        }   
        
        //load template from config.
        configData = (ArrayList<ConfigData>) configDAO.getConfigData(ConfigurationSection.InvoiceTemplates);
        configDataHash = new HashMap();
        for (ConfigData cdata : configData) { configDataHash.put(cdata.getKey(), cdata.getValue()); }       
        if (configDataHash.size() > 0) { 
            viewInvoiceInfo.setInvoiceTemplate(InvoiceTemplate.valueOf(configDataHash.get(InvoiceTemplateConfigItems.INVOICETEMPLATENAME.toString())));
        }
        
        //load business data from config
        configData = (ArrayList<ConfigData>) configDAO.getConfigData(ConfigurationSection.Business);       
        configDataHash = new HashMap();
        for (ConfigData cdata : configData) { configDataHash.put(cdata.getKey(), cdata.getValue()); }
        if (configDataHash.size() > 0) { 
            viewInvoiceInfo.getBusinessInfo().setCompanyName(configDataHash.get(BusinessConfigItems.BUSINESSNAME.toString()));
            viewInvoiceInfo.getBusinessInfo().setBusinessAddress1(configDataHash.get(BusinessConfigItems.BUSINESSADDRESS1.toString()));
            viewInvoiceInfo.getBusinessInfo().setBusinessAddress2(configDataHash.get(BusinessConfigItems.BUSINESSADDRESS2.toString()));
            viewInvoiceInfo.getBusinessInfo().setBusinessNumberLabel(convertNullToBlank(configDataHash.get(BusinessConfigItems.BUSINESSABNLABEL.toString())));                 
            viewInvoiceInfo.getBusinessInfo().setBusinessNumber(convertNullToBlank(configDataHash.get(BusinessConfigItems.BUSINESSABN.toString())));  
            
            viewInvoiceInfo.getBusinessInfo().setSuburb(configDataHash.get(BusinessConfigItems.BUSINESSCITY.toString()));
            viewInvoiceInfo.getBusinessInfo().setState(configDataHash.get(BusinessConfigItems.BUSINESSSTATE.toString()));
            viewInvoiceInfo.getBusinessInfo().setPostCode(convertNullToBlank(configDataHash.get(BusinessConfigItems.BUSINESSPOSTCODE.toString())));                 
            viewInvoiceInfo.getBusinessInfo().setPhone(configDataHash.get(BusinessConfigItems.BUSINESSPHONE.toString()));  
            viewInvoiceInfo.getBusinessInfo().setEmail(configDataHash.get(BusinessConfigItems.BUSINESSEMAIL.toString()));  
        }     
        
        //load appSettings data from config
        configData = (ArrayList<ConfigData>) configDAO.getConfigData(ConfigurationSection.ApplicationSettings);       
        configDataHash = new HashMap();
        for (ConfigData cdata : configData) { configDataHash.put(cdata.getKey(), cdata.getValue()); }       
        viewInvoiceInfo.setCurrencyCode(configDataHash.get(ApplicationSettingsItems.CURRENCY.toString()));
        viewInvoiceInfo.setTaxLabel(configDataHash.get(ApplicationSettingsItems.TAXLABEL.toString()));
        
        //load custom attribute configuration
        configData = (ArrayList<ConfigData>) configDAO.getConfigData(ConfigurationSection.CustomAttributes);       
        configDataHash = new HashMap();        
        for (ConfigData cdata : configData) { configDataHash.put(cdata.getKey(), cdata.getValue()); }
       
        viewInvoiceInfo.getCustomAttributes().setAttrib1(configDataHash.get(CustomAttribConfigItems.ATTR1.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib2(configDataHash.get(CustomAttribConfigItems.ATTR2.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib3(configDataHash.get(CustomAttribConfigItems.ATTR3.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib4(configDataHash.get(CustomAttribConfigItems.ATTR4.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib5(configDataHash.get(CustomAttribConfigItems.ATTR5.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib6(configDataHash.get(CustomAttribConfigItems.ATTR6.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib7(configDataHash.get(CustomAttribConfigItems.ATTR7.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib8(configDataHash.get(CustomAttribConfigItems.ATTR8.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib9(configDataHash.get(CustomAttribConfigItems.ATTR9.toString()));
        viewInvoiceInfo.getCustomAttributes().setAttrib10(configDataHash.get(CustomAttribConfigItems.ATTR10.toString())); 
     
        //load email template for invoice email.
        configData = (ArrayList<ConfigData>) configDAO.getConfigData(ConfigurationSection.Email);       
        configDataHash = new HashMap();        
        for (ConfigData cdata : configData) { configDataHash.put(cdata.getKey(), cdata.getValue()); }
        viewInvoiceInfo.setInvoiceEmailMessage(configDataHash.get(EmailConfigItems.EMAILINVOICETEMPLATE.toString()));
        
        return viewInvoiceInfo;
    }

    @Override
    public ArrayList<InvoiceInfo> getOverdueInvoiceInfoForClient(long clientId) {
        ArrayList<InvoiceInfo> allInvoiceInfo = new ArrayList<>();
        ArrayList<Invoice> invoiceList;
        InvoiceInfo info;
        ClientInfo client;
        
        invoiceList = invoiceDAO.getOverdueInvoiceInfoForClient(clientId);
        
        for(Invoice invoice : invoiceList) {
            info = new InvoiceInfo();
            info.setId(invoice.getId());
            info.setInvoiceNumber(invoice.getInvoiceNumber());
            info.setDescription(invoice.getDescription());
            info.setInvoiceDate(invoice.getInvoiceDate());
            info.setDueDate(invoice.getDueDate());
            info.setInvoiceStatus(invoice.getInvoiceStatus());
            info.setAmount(invoice.getAmount());
            //client
            client = new ClientInfo(invoice.getClient().getFirstName(), invoice.getClient().getLastName());
            info.setClientInfo(client);
            allInvoiceInfo.add(info);
        }
        return allInvoiceInfo;
    }
}
