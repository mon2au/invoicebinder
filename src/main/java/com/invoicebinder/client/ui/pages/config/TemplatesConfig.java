/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.config;

import com.invoicebinder.client.service.config.ConfigServiceCallbacks;
import com.invoicebinder.client.service.config.ConfigServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.enums.config.InvoiceTemplateConfigItems;
import com.invoicebinder.shared.enums.invoice.InvoiceTemplate;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.ConfigData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mon2
 */
public class TemplatesConfig extends Composite {
    
    private static final TemplatesConfigUiBinder uiBinder = GWT.create(TemplatesConfigUiBinder.class);
    private final Main main;
    private final ConfigServiceClientImpl configService;
    private FlexTable tblInvoiceTemplateConfig;
    private final HorizontalPanel mainPanel;
    private final HorizontalPanel buttonPanel;
    private final Button btnActivate;
    private final Label lblSaveResult;
    private InvoiceTemplate selectedTemplate;
    private final Image templatePreview;
    
    @UiField HTMLPanel templatesConfigPanel;
    
    void setActiveInvoiceTemplate(ArrayList<ConfigData> arrayList) {
        String templateName;
        
        HashMap<String, String> templateData = new HashMap();
        for (ConfigData data : arrayList) {
            templateData.put(data.getKey(), data.getValue());
        }
        templateName = templateData.get(InvoiceTemplateConfigItems.INVOICETEMPLATENAME.toString());
        setActiveInvoiceTemplate(templateName);
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final TemplatesConfig pageReference;
        
        public DefaultClickHandler(TemplatesConfig reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnActivate) {
                if (isDemoApplication()) return;
                this.pageReference.saveInvoiceTemplateConfig();
                this.pageReference.setActiveInvoiceTemplate(selectedTemplate.toString());
                event.getNativeEvent().preventDefault();
            }
        }
    }
    
    interface TemplatesConfigUiBinder extends UiBinder<Widget, TemplatesConfig> {
    }
    
    public TemplatesConfig(Main main) {
        this.main = main;
        this.configService = new ConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/config", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        templatePreview = new Image();
        templatePreview.setStyleName("invoice-template-preview");
        mainPanel = new HorizontalPanel();
        buttonPanel = new HorizontalPanel();
        btnActivate = new Button();
        lblSaveResult = new Label();
        lblSaveResult.setVisible(false);               
        mainPanel.setWidth("100%");
        //buttons
        btnActivate.setStyleName("appbutton-default");
        btnActivate.setText("Activate");
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnActivate);
        buttonPanel.add(lblSaveResult);
        btnActivate.addClickHandler(new DefaultClickHandler(this));
        templatesConfigPanel.add(mainPanel);
        templatesConfigPanel.add(buttonPanel);
        configService.loadInvoiceTemplateName(ConfigServiceCallbacks.TemplateNameTargetPage.ConfigPage);
    }
    
    void updateStatus(Boolean result) {
        lblSaveResult.setStyleName("message-box");
        lblSaveResult.setWidth("190px");
        lblSaveResult.setVisible(true);
        
        if (result) {
            lblSaveResult.addStyleName("success");
            lblSaveResult.setText("Invoice template selection saved.");
        }
        else {
            lblSaveResult.addStyleName("error");
            lblSaveResult.setText("Error saving invoice template selection.");
        }
    }
    
    private FlexTable getInvoiceTemplatesConfigTable(String activateTemplateName) {
        FlexTable table = new FlexTable();
        Label lblHeader = new Label();
        VerticalPanel templateThumbnail;
        VerticalPanel thumbnailContents;
        FocusPanel thumbnailWrapper;
        Label lblTemplateName;
        Image templateImage;
        Integer row, col, maxCols, id;
        //set defaults
        String templateLabel;
        row = 1;
        col = 0;
        maxCols = 3;
        
        table.clear();
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        lblHeader.getElement().setInnerHTML("<h6><span>Invoice Templates</span></h6>");
        table.getFlexCellFormatter().setColSpan(0, 0, maxCols);
        table.getFlexCellFormatter().setColSpan(0, 1, maxCols);
        table.setWidget(0, 0, lblHeader);
        
        //setup all templates.
        for (InvoiceTemplate templateName : InvoiceTemplate.values()) {
            templateImage = new Image();
            templateImage.setUrl("images/invoicetemplates/" + templateName.toString().toLowerCase() + ".png");
            
            lblTemplateName = new Label();
            lblTemplateName.addStyleName("invoice-template");
            
            templateLabel = templateName.toString();
            if (templateName.toString().equals(activateTemplateName)) {
                templateLabel += " [Active]";
                lblTemplateName.setStyleName("invoice-template-active");                
            }
            else {
                lblTemplateName.setStyleName("invoice-template-inactive");                
            }
            
            lblTemplateName.setText(templateLabel);
            templateThumbnail = new VerticalPanel();
            thumbnailContents = new VerticalPanel();
            thumbnailWrapper = new FocusPanel();
            templateThumbnail.setBorderWidth(1);
            thumbnailContents.add(templateImage);
            thumbnailContents.add(lblTemplateName);
            templateThumbnail.add(thumbnailContents);
            thumbnailWrapper.add(templateThumbnail);
            thumbnailWrapper.setStyleName("invoice-template-container");
            thumbnailWrapper.addClickHandler(new DefaultTemplateClickHandler(thumbnailWrapper, templateName));
            table.setWidget(row, col, thumbnailWrapper);
            
            //manage grid.
            col++;
            if (col >= maxCols) {
                row++;
                col = 0;
            }
        }
        
        return table;
    }
    
    private void setActiveInvoiceTemplate(String templateName) {
        this.tblInvoiceTemplateConfig = getInvoiceTemplatesConfigTable(templateName); 
        mainPanel.clear();
        mainPanel.add(this.tblInvoiceTemplateConfig);  
        mainPanel.add(this.templatePreview);   
        setTemplateImage(templateName);
    }
    
    private void setTemplateImage(String templateName) {
        this.templatePreview.setUrl("images/invoicetemplates/" + templateName.toLowerCase() + "_full.png");         
    }
    
    private void saveInvoiceTemplateConfig() {
        ArrayList<ConfigData> lstData = new ArrayList<ConfigData>();
        ConfigData data;
        
        //Save template data.
        //1. Template Name
        data = new ConfigData(InvoiceTemplateConfigItems.INVOICETEMPLATENAME.toString(), ConfigurationSection.InvoiceTemplates.toString(), selectedTemplate.name());
        lstData.add(data);
        
        configService.saveConfigData(lstData, ConfigurationSection.InvoiceTemplates);
    }
    
    private class DefaultTemplateClickHandler implements ClickHandler {
        private final FocusPanel controlReference;
        private final InvoiceTemplate template;
        
        public DefaultTemplateClickHandler(FocusPanel reference, InvoiceTemplate template) {
            this.controlReference = reference;
            this.template = template;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            clearFocus();
            this.controlReference.setStyleName("invoice-template-container-selected");
            selectedTemplate = this.template;
            setTemplateImage(template.name());
        }
        
        private void clearFocus() {
            int row, col;
            FocusPanel item;
            
            for (row=0; row < tblInvoiceTemplateConfig.getRowCount();row++) {
                for (col=0; col < tblInvoiceTemplateConfig.getCellCount(row);col++) {
                    Widget childWidget = tblInvoiceTemplateConfig.getWidget(row, col);
                    if (childWidget != null) {
                        if (childWidget.getClass() == FocusPanel.class) {
                            item = (FocusPanel)childWidget;
                            item.setStyleName("invoice-template-container");
                        }
                    }
                }
            }
        }
    }
}
