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
    public void loadCustomAttrConfigDataForNewInvoicePage() {
        this.service.loadConfigData(ConfigurationSection.CustomAttributes, new LoadCustomAttrConfigDataForNewInvoicePageCallback());
    }

    @Override
    public void loadSocialMediaConfigForHomePage() {
       this.service.loadConfigData(ConfigurationSection.SocialMedia, new LoadSocialMediaConfigForHomePageCallback());
    }

    @Override
    public void loadBusinessConfigData(ConfigServiceCallbacks.BusinessConfigTargetPage page) {
       this.service.loadConfigData(ConfigurationSection.Business, new LoadBusinessConfigDataCallback(page));
    }

    @Override
    public void loadEmailConfigData(ConfigServiceCallbacks.EmailConfigTargetPage page) {
        this.service.loadConfigData(ConfigurationSection.Email, new LoadEmailConfigDataCallback(page));
    }

    @Override
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

        private LoadInvoiceTemplateNameCallback(ConfigServiceCallbacks.TemplateNameTargetPage page) {
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

    private class LoadCustomAttrConfigDataForNewInvoicePageCallback implements AsyncCallback<ArrayList<ConfigData>> {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            mainui.updateCustomAttrInNewInvoicePage(ConfigurationSection.CustomAttributes, result);
        }
    }

    private class LoadApplicationSettingsForGettingLogoutPrefsCallback implements AsyncCallback<ArrayList<ConfigData>> {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            mainui.performLogout(ConfigurationSection.ApplicationSettings, result);
        }        
    }
    
    private class LoadSocialMediaConfigForHomePageCallback implements AsyncCallback<ArrayList<ConfigData>> {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            mainui.getContainer().updateSocialMediaConfigForHomePage(ConfigurationSection.SocialMedia, result);
        }
    }

    private class LoadPaymentConfigCallback implements AsyncCallback<ArrayList<ConfigData>> {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            //TODO
            //mainui.updateHomePageConfig(ConfigurationSection.Business, result);
        }
    }

    private class LoadBusinessConfigDataCallback implements AsyncCallback<ArrayList<ConfigData>> {
        private ConfigServiceCallbacks.BusinessConfigTargetPage page;

        private LoadBusinessConfigDataCallback(ConfigServiceCallbacks.BusinessConfigTargetPage page) {
            this.page = page;
        }

        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            mainui.updateBusinessConfigData(ConfigurationSection.Business, page, result);
        }    
    }

    private class LoadEmailConfigDataCallback implements AsyncCallback<ArrayList<ConfigData>> {
        private ConfigServiceCallbacks.EmailConfigTargetPage page;

        private LoadEmailConfigDataCallback(ConfigServiceCallbacks.EmailConfigTargetPage page) {
            this.page = page;
        }

        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            mainui.updateEmailConfigData(page, result);
        }
    }

    private class LoadApplicationConfigDataForDashboardPageCallback implements AsyncCallback<ArrayList<ConfigData>> {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            mainui.updateApplicationConfigForDashboard(ConfigurationSection.ApplicationSettings, result);
        }    
    }

    private class SaveConfigDataCallback implements AsyncCallback<SaveConfigResult> {
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(SaveConfigResult result) {
            mainui.updateConfigurationPagesStatus(result);
        }
    }    

    private class LoadDataForConfigPageCallback implements AsyncCallback<ArrayList<ConfigData>> {
        private final ConfigurationSection section;
        
        private LoadDataForConfigPageCallback(ConfigurationSection section) {
            this.section = section;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            ClientLogManager.writeLog(caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<ConfigData> result) {
            mainui.updateConfigurationPagesConfig(section, result);
        }
    }
}
