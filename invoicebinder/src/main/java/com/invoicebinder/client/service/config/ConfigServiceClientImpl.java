/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.config;

import com.invoicebinder.client.logger.ClientLogManager;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.model.ConfigData;
import com.invoicebinder.shared.model.SaveConfigResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import java.util.ArrayList;

public class ConfigServiceClientImpl implements ConfigServiceClientInt {
    private final ConfigServiceAsync service;
    private final Main mainui;
    
    public ConfigServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(ConfigService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    } 

    @Override
    public void saveConfigData(ArrayList<ConfigData> config, ConfigurationSection section) {
        this.service.saveConfigData(config, section, new SaveConfigDataCallback());
    }

    @Override
    public void loadConfigData(ConfigurationSection section) {
        this.service.loadConfigData(section, new LoadDataForConfigPageCallback(section));
    }
    
    @Override
    public void loadBusinessConfigDataForNewInvoicePage() {
        this.service.loadConfigData(ConfigurationSection.Business, new LoadBusinessConfigDataForNewInvoicePageCallback());
    }

    @Override
    public void loadCustomAttrConfigDataForNewInvoicePage() {
        this.service.loadConfigData(ConfigurationSection.CustomAttributes, new LoadCustomAttrConfigDataForNewInvoicePageCallback());
    }

    public void loadSocialMediaConfigForHomePage() {
       this.service.loadConfigData(ConfigurationSection.SocialMedia, new LoadSocialMediaConfigForHomePageCallback());
    }

    public void loadBusinessConfigDataForHomePage() {
       this.service.loadConfigData(ConfigurationSection.Business, new LoadBusinessConfigForHomePageCallback());
    }

    public void loadApplicationSettingsForGettingLogoutPrefs() {
        this.service.loadConfigData(ConfigurationSection.ApplicationSettings, new LoadApplicationSettingsForGettingLogoutPrefsCallback());
    }

    @Override
    public void loadAppSettingConfigDataForDashboardPage() {
        this.service.loadConfigData(ConfigurationSection.ApplicationSettings, new LoadApplicationConfigDataForDashboardPageCallback());
    }

    @Override
    public void loadInvoiceTemplateName(ConfigServiceCallbacks.TemplateNameTargetPage page) {
        this.service.loadConfigData(ConfigurationSection.InvoiceTemplates, new LoadInvoiceTemplateNameCallback(page));
    }

    @Override
    public void loadPaymentConfig() {
        this.service.loadConfigData(ConfigurationSection.Payments, new LoadPaymentConfigCallback());
    }

    private class LoadInvoiceTemplateNameCallback implements AsyncCallback<ArrayList<ConfigData>> {
        ConfigServiceCallbacks.TemplateNameTargetPage page;

        public LoadInvoiceTemplateNameCallback(ConfigServiceCallbacks.TemplateNameTargetPage page) {
            this.page = page;
        }

        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            mainui.updateInvoiceTemplateName(page, result);
        }        
    }

    private class LoadCustomAttrConfigDataForNewInvoicePageCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateCustomAttrInNewInvoicePage(ConfigurationSection.CustomAttributes, (ArrayList<ConfigData>)result);
        }
    }

    private class LoadApplicationSettingsForGettingLogoutPrefsCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.performLogout(ConfigurationSection.ApplicationSettings, (ArrayList<ConfigData>)result);
        }        
    }
 
    private class LoadApplicationSettingsForApplicationSettingsPage implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateApplicationSettingsConfig(ConfigurationSection.ApplicationSettings, (ArrayList<ConfigData>)result);
        }        
    }
    
    private class LoadSocialMediaConfigForHomePageCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateHomePageConfig(ConfigurationSection.SocialMedia, (ArrayList<ConfigData>)result);
        }
    }

    private class LoadPaymentConfigCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            //TODO
            //mainui.updateHomePageConfig(ConfigurationSection.Business, (ArrayList<ConfigData>)result);
        }
    }

    private class LoadBusinessConfigForHomePageCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateHomePageConfig(ConfigurationSection.Business, (ArrayList<ConfigData>)result);
        }    
    }
    
    private class LoadApplicationConfigDataForDashboardPageCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateApplicationConfigForDashboard(ConfigurationSection.ApplicationSettings, (ArrayList<ConfigData>)result);
        }    
    }
  
    private class LoadBusinessConfigDataForNewInvoicePageCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateNewInvoiceConfig(ConfigurationSection.Business, (ArrayList<ConfigData>)result);
        }
    }
    
    private class SaveConfigDataCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateConfigurationPagesStatus((SaveConfigResult)result);
        }
    }    

    private class LoadDataForConfigPageCallback implements AsyncCallback {
        private final ConfigurationSection section;
        
        public LoadDataForConfigPageCallback(ConfigurationSection section) {
            this.section = section;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
            mainui.updateConfigurationPagesConfig(section, (ArrayList<ConfigData>)result);
        }
    }
}
