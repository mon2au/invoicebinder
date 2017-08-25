/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.config;

import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.widget.globaladdress.GlobalAddressPanel;
import com.invoicebinder.shared.enums.config.BusinessConfigItems;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Constants.EMPTY_STRING;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.ConfigData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.invoicebinder.shared.misc.Utils;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;
import gwtupload.client.SingleUploader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mon2
 */
public class BusinessConfig extends Composite {
    
    private static final BusinessConfigUiBinder uiBinder = GWT.create(BusinessConfigUiBinder.class);
    @UiField HTMLPanel businessConfigPanel;
    private final GlobalAddressPanel pnlBusinessAddress;
    
    private TextBox txtBusinessName;
    private TextBox txtABN;
    private TextBox txtABNLabel;
    private TextBox txtPhone;
    private TextBox txtFax;
    private TextBox txtEmail;
    
    private final HorizontalPanel buttonPanel;
    private final HorizontalPanel mainPanel;
    private final VerticalPanel leftPanel;
    private final HorizontalPanel logoNoticePanel;
    private final HorizontalPanel businessInfoNoticePanel;
    private final Button btnSave;
    private final Main main;
    private final ConfigServiceClientImpl configService;
    private final FlexTable tblBusinessConfig;
    private final FlexTable tblBusinessAddress;
    private final FlexTable tblOtherBusinessSettings;
    private final Label lblSaveResult;    
    private final SingleUploader fileUploadLogo = new SingleUploader();
    private final SimplePanel pnlLogoImage;
    private final Image imgLogo;
    private final Label lblMessage;
    
    private void toggleBusinessNumberVisibility() {
        if (txtABN.getText().isEmpty()) {
            txtABNLabel.setEnabled(false);
            txtABNLabel.setText(EMPTY_STRING);
        }
        else {
            txtABNLabel.setEnabled(true);
        }
    }
    
    interface BusinessConfigUiBinder extends UiBinder<Widget, BusinessConfig> {
    }
    
    public BusinessConfig(Main main) {
        this.main = main;
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);         
        initWidget(uiBinder.createAndBindUi(this));
        this.pnlLogoImage = new SimplePanel();
        this.imgLogo = new Image();
        this.lblMessage = new Label();
        this.pnlBusinessAddress = new GlobalAddressPanel(false);
        this.buttonPanel = new HorizontalPanel();
        this.mainPanel = new HorizontalPanel();
        this.leftPanel = new VerticalPanel();
        this.logoNoticePanel = new HorizontalPanel();
        this.businessInfoNoticePanel = new HorizontalPanel();
        //all tables
        this.tblBusinessConfig = getBusinessConfigTable();
        this.tblBusinessAddress = getBusinessAddressTable();
        this.tblOtherBusinessSettings = getOtherBusinessSettingsTable();
        this.lblSaveResult = new Label();
        this.lblSaveResult.setVisible(false);
        
        mainPanel.setWidth("100%");
        leftPanel.add(this.tblBusinessConfig);
        leftPanel.add(this.tblBusinessAddress);
        mainPanel.add(this.leftPanel);
        mainPanel.add(this.tblOtherBusinessSettings);
        mainPanel.setCellWidth(this.tblBusinessConfig, "50%");
        mainPanel.setCellWidth(this.tblOtherBusinessSettings, "50%");
        businessConfigPanel.add(mainPanel);
        
        imgLogo.setUrl("logo/logo.png");
        pnlLogoImage.add(imgLogo);
        
        //buttons
        btnSave = new Button();
        btnSave.setStyleName("appbutton-default");
        btnSave.setText("Save");
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSave);
        buttonPanel.add(lblSaveResult);
        businessConfigPanel.add(buttonPanel);
        btnSave.addClickHandler(new DefaultClickHandler(this));
        fileUploadLogo.addOnFinishUploadHandler(new DefaultUploadHandler(this));
        fileUploadLogo.setStyleName(""); //clear the default styles.
        fileUploadLogo.getElement().getElementsByTagName("button").getItem(0).setClassName("appbutton-default");
        txtABN.addBlurHandler(new DefaultBlurHandler(this));
        txtABN.addKeyPressHandler(new DefaultKeyPressHandler(this));
       
        configService.loadConfigData(ConfigurationSection.Business);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Save Load">
    public void setBusinessData(ArrayList<ConfigData> arrayList) {
        HashMap<String, String> businessConfigData = new HashMap();
        for (ConfigData data : arrayList) {
            businessConfigData.put(data.getKey(), data.getValue());
        }
       
        txtBusinessName.setText(businessConfigData.get(BusinessConfigItems.BUSINESSNAME.toString()));
        txtABN.setText(businessConfigData.get(BusinessConfigItems.BUSINESSABN.toString()));
        txtABNLabel.setText(businessConfigData.get(BusinessConfigItems.BUSINESSABNLABEL.toString()));       
        txtPhone.setText(businessConfigData.get(BusinessConfigItems.BUSINESSPHONE.toString()));
        txtEmail.setText(businessConfigData.get(BusinessConfigItems.BUSINESSEMAIL.toString()));
        txtFax.setText(businessConfigData.get(BusinessConfigItems.BUSINESSFAX.toString()));
        //business address
        pnlBusinessAddress.setAddressDetails(businessConfigData.get(BusinessConfigItems.BUSINESSCOUNTRYCODE.toString()),
                                            businessConfigData.get(BusinessConfigItems.BUSINESSSTATE.toString()),
                                            businessConfigData.get(BusinessConfigItems.BUSINESSADDRESS1.toString()),
                                            businessConfigData.get(BusinessConfigItems.BUSINESSADDRESS2.toString()),
                                            businessConfigData.get(BusinessConfigItems.BUSINESSPOSTCODE.toString()),
                                            businessConfigData.get(BusinessConfigItems.BUSINESSCITY.toString()));  
        
        pnlBusinessAddress.loadAddressData();
        toggleBusinessNumberVisibility();
    }
    private void saveBusinessConfig() {
        ArrayList<ConfigData> lstData = new ArrayList<ConfigData>();
        ConfigData data;
        
        //Save business data.
        //Business Name
        data = new ConfigData(BusinessConfigItems.BUSINESSNAME.toString(), ConfigurationSection.Business.toString(), txtBusinessName.getText());
        lstData.add(data);
        //Business Number
        data = new ConfigData(BusinessConfigItems.BUSINESSABN.toString(), ConfigurationSection.Business.toString(), txtABN.getText());
        lstData.add(data);
        //Business Number Label
        data = new ConfigData(BusinessConfigItems.BUSINESSABNLABEL.toString(), ConfigurationSection.Business.toString(), txtABNLabel.getText());
        lstData.add(data);
        //Address 1
        data = new ConfigData(BusinessConfigItems.BUSINESSADDRESS1.toString(), ConfigurationSection.Business.toString(), pnlBusinessAddress.getAddress1());
        lstData.add(data);
        //Address 2
        data = new ConfigData(BusinessConfigItems.BUSINESSADDRESS2.toString(), ConfigurationSection.Business.toString(), pnlBusinessAddress.getAddress2());
        lstData.add(data);
        //City
        data = new ConfigData(BusinessConfigItems.BUSINESSCITY.toString(), ConfigurationSection.Business.toString(), pnlBusinessAddress.getCity());
        lstData.add(data);
        //State
        data = new ConfigData(BusinessConfigItems.BUSINESSSTATE.toString(), ConfigurationSection.Business.toString(), pnlBusinessAddress.getState());
        lstData.add(data);
        //Country
        data = new ConfigData(BusinessConfigItems.BUSINESSCOUNTRYCODE.toString(), ConfigurationSection.Business.toString(), pnlBusinessAddress.getCountryCode());
        lstData.add(data);        
        //PostCode
        data = new ConfigData(BusinessConfigItems.BUSINESSPOSTCODE.toString(), ConfigurationSection.Business.toString(), pnlBusinessAddress.getPostCode());
        lstData.add(data); 
        //Phone
        data = new ConfigData(BusinessConfigItems.BUSINESSPHONE.toString(), ConfigurationSection.Business.toString(), txtPhone.getValue());
        lstData.add(data);
        //Fax
        data = new ConfigData(BusinessConfigItems.BUSINESSFAX.toString(), ConfigurationSection.Business.toString(), txtFax.getValue());
        lstData.add(data);
        //Email
        data = new ConfigData(BusinessConfigItems.BUSINESSEMAIL.toString(), ConfigurationSection.Business.toString(), txtEmail.getValue());
        lstData.add(data);
        configService.saveConfigData(lstData, ConfigurationSection.Business);
    }  
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="UI Component Tables">
    private FlexTable getBusinessConfigTable() {
        FlexTable table = new FlexTable();
        Label lblBusinessConfigHeader = new Label();
        
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        txtBusinessName = new TextBox();
        txtABN = new TextBox();
        txtABNLabel = new TextBox();
        txtPhone = new TextBox();
        txtEmail = new TextBox();
        txtFax = new TextBox();
        
        lblBusinessConfigHeader.getElement().setInnerHTML("<h6><span>Business Details</span></h6>");
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);
        table.setWidget(0, 0, lblBusinessConfigHeader);
        //business settings  information
        table.setHTML(1, 0, "Business Name:");
        table.setWidget(1, 1, txtBusinessName);
        table.setHTML(2, 0, "Business Number:");
        table.setWidget(2, 1, txtABN);
        table.setHTML(3, 0, "Business Number Label:");
        table.setWidget(3, 1, txtABNLabel);
        table.setHTML(4, 0, "Phone:");
        table.setWidget(4, 1, txtPhone);
        table.setHTML(5, 0, "Fax:");
        table.setWidget(5, 1, txtFax);
        table.setHTML(6, 0, "Email:");
        table.setWidget(6, 1, txtEmail);
        
        table.getFlexCellFormatter().setColSpan(7, 0, 2);
        table.getFlexCellFormatter().setColSpan(7, 1, 2);
        businessInfoNoticePanel.add(new Label("The business information entered above will appear on your invoices. Please enter all details as you want them to appear on your invoice."));
        businessInfoNoticePanel.setStyleName("message-box");
        businessInfoNoticePanel.addStyleName("notice");
        businessInfoNoticePanel.setWidth("440px");
        table.setWidget(7, 0, businessInfoNoticePanel);
        
        return table;
    }
    private FlexTable getBusinessAddressTable() {
        Label lblBusinessAddressHeader = new Label();
        FlexTable table = new FlexTable();
      
        
        lblBusinessAddressHeader.getElement().setInnerHTML("<h6><span>Business Address</span></h6>");
        
        table.setWidget(0, 0, lblBusinessAddressHeader);
        table.getFlexCellFormatter().setWidth(0, 0, "160px");
        table.setWidget(1, 0, pnlBusinessAddress);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        return table;
    }
    private FlexTable getOtherBusinessSettingsTable() {
        Label lblBusinessLogoHeader = new Label();
        FlexTable table = new FlexTable();
        
        lblBusinessLogoHeader.getElement().setInnerHTML("<h6><span>Business Logo</span></h6>");
        pnlLogoImage.addStyleName("business-logo");
        
        table.getFlexCellFormatter().setColSpan(0, 0, 2);
        table.getFlexCellFormatter().setColSpan(0, 1, 2);
        table.setWidget(0, 0, lblBusinessLogoHeader);
        table.setWidget(1, 0, fileUploadLogo);
        table.setWidget(2, 0, pnlLogoImage);
        table.setWidget(3, 0, lblMessage);
        
        table.getFlexCellFormatter().setColSpan(4, 0, 2);
        table.getFlexCellFormatter().setColSpan(4, 1, 2);
        logoNoticePanel.add(new Label("The logo will appear on your invoices. The logo must be of size " + String.valueOf(Constants.LOGO_IMAGE_WIDTH) + "px by " + String.valueOf(Constants.LOGO_IMAGE_HEIGHT) + "px. Please enter all details as you want them to appear on your invoice."));
        logoNoticePanel.setStyleName("message-box");
        logoNoticePanel.addStyleName("notice");
        logoNoticePanel.setWidth("440px");
        table.setWidget(4, 0, logoNoticePanel);
        return table;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Callback Updates">
    void updateStatus(Boolean status) {
        lblSaveResult.setStyleName("message-box");
        lblSaveResult.setWidth("190px");
        lblSaveResult.setVisible(true);
        
        if (status) {
            lblSaveResult.addStyleName("success");
            lblSaveResult.setText("Business configuration settings have been saved.");
        }
        else {
            lblSaveResult.addStyleName("error");
            lblSaveResult.setText("Error saving business configuration settings.");
        }
    }
    public GlobalAddressPanel getPnlBusinessAddress() {
        return this.pnlBusinessAddress;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class DefaultClickHandler implements ClickHandler {
        private final BusinessConfig pageReference;
        
        public DefaultClickHandler(BusinessConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                if (Utils.isDemoApplication()) return;
                this.pageReference.saveBusinessConfig();
                event.getNativeEvent().preventDefault();
            }
        }
    }
    private class DefaultUploadHandler implements IUploader.OnFinishUploaderHandler {
        BusinessConfig pageReference;
        
        private DefaultUploadHandler(BusinessConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onFinish(IUploader uploader) {
            if (uploader.getStatus() == Status.SUCCESS) {
                PreloadedImage preloadedImage = new PreloadedImage(uploader.fileUrl(), showImage);
                
                // The server sends useful information to the client by default
                UploadedInfo info = uploader.getServerInfo();
                lblMessage.setText(info.message);
            }
        }
        
        // Attach an image to the pictures viewer
        private final OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
            @Override
            public void onLoad(PreloadedImage image) {
                pageReference.pnlLogoImage.clear();
                pageReference.pnlLogoImage.add(image);
            }
        };
        
    }
    private class DefaultBlurHandler implements BlurHandler {
        private final BusinessConfig pageReference;
        
        public DefaultBlurHandler(BusinessConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onBlur(BlurEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == txtABN) {
                pageReference.toggleBusinessNumberVisibility();
            }
        }
    }
    private class DefaultKeyPressHandler implements KeyPressHandler {
        private final BusinessConfig pageReference;
        
        public DefaultKeyPressHandler(BusinessConfig reference) {
            this.pageReference = reference;
        }
        @Override
        public void onKeyPress(KeyPressEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == txtABN) {
                pageReference.toggleBusinessNumberVisibility();
            }
        }
        
    }
    // </editor-fold>
}