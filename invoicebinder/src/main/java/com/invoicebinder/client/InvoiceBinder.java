package com.invoicebinder.client;

import com.invoicebinder.client.logger.ClientLogManager;
import com.invoicebinder.client.service.startup.startupconfig.StartupConfigServiceClientImpl;
import com.invoicebinder.client.service.startup.startupcontext.StartupContextServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.history.HistoryHandler;
import com.invoicebinder.client.ui.widget.miscui.LoadingPanel;
import com.invoicebinder.shared.enums.startup.HomePageSettings;
import com.invoicebinder.shared.misc.Utils;
import com.invoicebinder.shared.model.AppSettingsInfo;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class InvoiceBinder implements EntryPoint {
    private Main main;

    @Override
    public void onModuleLoad() {
        //show loading panel.
        Utils.setupClientLogging();        
        ClientLogManager.writeLog("Application start.");
        LoadingPanel loading = new LoadingPanel();
        RootPanel.get().clear();
        RootPanel.get().add(loading);
        
        //init.      
        StartupContextServiceClientImpl startupContextService = new StartupContextServiceClientImpl(GWT.getModuleBaseURL() + "services/startupcontext", this.main);
        this.main = new Main(Utils.getAppName());
        HistoryHandler history = new HistoryHandler(main);
        History.addValueChangeHandler(history);
        Window.enableScrolling(true);
        Window.setMargin("0px");    
        startupContextService.isAppContextAvailable(this);
    }
    
    public void setAppContextAvailable(final Boolean response) {
        
        if (response) {
            StartupConfigServiceClientImpl startupConfigService = new StartupConfigServiceClientImpl(GWT.getModuleBaseURL() + "services/startupconfig");
            startupConfigService.loadApplicationSettings(this);
        }
        else {
            initializeApplication();            
            main.applyAppLandingSession(HomePageSettings.INSTALLPAGE);
        }
    }
    
    public void setApplicationSettings(final AppSettingsInfo settings) {        
        initializeApplication();
        main.applyApplicationSettings(settings);
    }
    
    private void initializeApplication() {
        main.applyStyle();
        RootPanel.get().clear();
        RootPanel.get().add(main);
    }
}
