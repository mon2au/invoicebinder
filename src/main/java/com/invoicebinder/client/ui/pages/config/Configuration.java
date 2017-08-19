/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.ui.pages.config;

import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.model.ConfigData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;


/**
 *
 * @author mon2
 */
public class Configuration extends Composite {
    
    private static final ConfigurationUiBinder uiBinder = GWT.create(ConfigurationUiBinder.class);
    private final TabLayoutPanel tabPanel;
    private final BusinessConfig bConfig;
    private final EmailConfig eConfig;
    private final PaymentConfig pConfig;
    private final CustomAttribConfig cConfig;
    private final SocialMediaConfig mConfig;
    private final ApplicationConfig aConfig;
    private final TemplatesConfig tConfig;
    private final SystemInfo sInfo;
    
    @UiField HTMLPanel settingsPanel;
    
    public EmailConfig getEmailConfigPanel() {
        return eConfig;
    }

    public void setActiveTab(ConfigurationSection section) {
        int index = getIndexForTab(section);
        tabPanel.selectTab(index);
    }
    
    private int getIndexForTab(ConfigurationSection section) {
        int index = 0;
        String[] tabTitles = enumNameToStringArray(ConfigurationSection.values());
        for (int i=0;i<tabTitles.length;i++) {
            if (tabTitles[i].equals(section.toString())) {
                index = i;
                break;
            }            
        }
        
        return index;
    }
    
    public void setConfiguration(ConfigurationSection section, ArrayList<ConfigData> arrayList) {
        switch(section){
            case Business:bConfig.setBusinessData(arrayList);
                break;
            case Email:eConfig.setConfigData(arrayList);
                break;
            case Payments:pConfig.setPaymentConfigData(arrayList);
                break;
            case CustomAttributes:cConfig.setCustomAttrData(arrayList);
                break;
            case SocialMedia:mConfig.setSocialMediaData(arrayList);
                break;
            case ApplicationSettings:aConfig.setAppSettingsData(arrayList);
                break;
        }
    }
    
    public void updateConfigurationStatus(ConfigurationSection section, Boolean status) {
        switch(section){
            case Business:bConfig.updateStatus(status);
                break;
            case Email:eConfig.updateStatus(status);
                break;
            case Payments:pConfig.updateStatus(status);
                break;
            case CustomAttributes:cConfig.updateStatus(status);
                break;
            case SocialMedia:mConfig.updateStatus(status);
                break;
            case ApplicationSettings:aConfig.updateStatus(status);
                break;
            case InvoiceTemplates: tConfig.updateStatus(status);
                break;
            case SystemInformation:
                    break;
            default:
                    break;
        }        
    }

    public void updateEmailTemplateStatus(Boolean status) {
        eConfig.updateTemplateStatus(status);
    }
    
    public void setConfigPageSettings() {
        tabPanel.remove(mConfig);
    }

    public void updateTestEmail(Boolean result) {
        this.eConfig.updateTestEmailResult(result);
    }

    public void setSystemInfo(com.invoicebinder.shared.model.SystemInfo systemInfo) {
        this.sInfo.setSystemInfo(systemInfo);
    }

    public void setActiveInvoiceTemplate(ArrayList<ConfigData> arrayList) {
        this.tConfig.setActiveInvoiceTemplate(arrayList);
    }
  
    interface ConfigurationUiBinder extends UiBinder<Widget, Configuration> {
    }
    
    public static <T extends Enum<T>> String[] enumNameToStringArray(T[] values) {
        int i = 0;
        String[] result = new String[values.length];
        for (T value: values) {
            result[i++] = value.name();
        }
        return result;
    }
    
    public Configuration(Main main) {
        initWidget(uiBinder.createAndBindUi(this));
        tabPanel = new TabLayoutPanel(2.5, Unit.EM);
        tabPanel.setHeight("1024px");
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);

        // Add tabs
        String[] tabTitles = enumNameToStringArray(ConfigurationSection.values());
        int tabCount = 0;
        
        //business tab
        bConfig = new BusinessConfig(main);
        tabPanel.add(bConfig, tabTitles[tabCount++]);
        
        // email tab
        eConfig = new EmailConfig(main);
        tabPanel.add(eConfig, tabTitles[tabCount++]);
        
        // payments tab
        pConfig = new PaymentConfig(main);
        tabPanel.add(pConfig, tabTitles[tabCount++]);
        
        // custom attributes tab
        cConfig = new CustomAttribConfig(main);
        tabPanel.add(cConfig, tabTitles[tabCount++]);
               
        // application settings tab
        aConfig = new ApplicationConfig(main);
        tabPanel.add(aConfig, tabTitles[tabCount++]);
                
        // templates tab
        tConfig = new TemplatesConfig(main);
        tabPanel.add(tConfig, tabTitles[tabCount++]);
        
        // system info tab
        sInfo = new SystemInfo(main);
        tabPanel.add(sInfo, tabTitles[tabCount++]);
                
        // social media tab
        mConfig = new SocialMediaConfig(main);
        tabPanel.add(mConfig, tabTitles[tabCount++]);
               
        // site templates tab
        //sConfig = new SiteTemplateConfig();
        //tabPanel.add(sConfig, tabTitles[tabCount++]);

        //cleanup styles.
        for (int i=0;i<tabPanel.getWidgetCount();i++)  {
            tabPanel.getTabWidget(i).setStyleName("tab-panel-tab-label");
            tabPanel.getTabWidget(i).getParent().addStyleName("tab-panel-tab");
            tabPanel.getWidget(i).setStyleName("tab-panel-content");
        }
        
        // Return the content
        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("cwTabPanel");
        settingsPanel.setHeight("100%");
        settingsPanel.add(tabPanel);
    }
}