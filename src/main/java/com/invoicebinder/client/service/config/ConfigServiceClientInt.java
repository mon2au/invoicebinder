/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoicebinder.client.service.config;

import com.invoicebinder.shared.enums.config.ConfigurationSection;
import com.invoicebinder.shared.model.ConfigData;

import java.util.ArrayList;

public interface ConfigServiceClientInt {
    void saveConfigData(ArrayList<ConfigData> config, ConfigurationSection section);
    void loadConfigData(ConfigurationSection section);
    void loadBusinessConfigDataForNewInvoicePage();  
    void loadCustomAttrConfigDataForNewInvoicePage();      
    void loadAppSettingConfigDataForDashboardPage();
    void loadInvoiceTemplateName(ConfigServiceCallbacks.TemplateNameTargetPage page);
    void loadPaymentConfig();
}
