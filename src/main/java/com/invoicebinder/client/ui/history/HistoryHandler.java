/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.history;

import com.invoicebinder.client.ui.controller.Main;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.service.login.LoginServiceClientImpl;
import com.invoicebinder.client.ui.controller.ViewManager;
import com.google.gwt.core.client.GWT;

/**
 *
 * @author mon2
 */
public class HistoryHandler implements ValueChangeHandler<String>  {
    
    private final Main mainPanel;
    private final LoginServiceClientImpl loginService;
    
    public HistoryHandler(Main main) {
        this.mainPanel = main;
        this.loginService = new LoginServiceClientImpl(GWT.getModuleBaseURL() + "services/login", main);
    }
    
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String historyToken = event.getValue();

        //handle session.
        //1. if the session is valid the view should show authenticated context.
        //2. if the session is expired then the view show show standard context.
        if (!historyToken.equals(Views.sessionexpired.toString())) {
            if ((ViewManager.isViewAuthenticated(historyToken))) {
                if ((mainPanel != null) && (mainPanel.getIsInitialized())) { mainPanel.ChangeContextToAuthenticatedUser(); }
                loginService.getUserFromSession();
            }
            
            if ((ViewManager.isViewStandard(historyToken))) {
                if (mainPanel.getIsInitialized()) {
                    mainPanel.ChangeContextToNonAuthenticatedUser();
                }
            }

            if ((ViewManager.isViewAutoAuthenticated(historyToken))) {
                if (mainPanel.getIsInitialized()) {
                    mainPanel.ChangeContextToAutoAuthenticatedUser();
                }
            }
        }
        
        if (historyToken.equals(Views.sessionexpired.toString())) {
            if (mainPanel.getIsInitialized()) {
                mainPanel.Logout();
            }
            return;
        }
        if (historyToken.contains(Views.previewinvoice.toString())) {
            mainPanel.showPreviewInvoice();
            return;
        }      
        if(historyToken.equals(Views.login.toString())) {
            mainPanel.getContainer().clear();
            mainPanel.showLoginDialog(Boolean.FALSE);
            return;
        }
        if(historyToken.contains(Views.autologin.toString())) {
            mainPanel.getContainer().clear();
            mainPanel.performAutoLogin();
            return;
        }
        if(historyToken.equals(Views.logout.toString())) {
            LogoutSession();
            return;
        }
        
        if (historyToken.contains("/")) {
            historyToken = historyToken.split("/")[0];
        }
        
        mainPanel.show(Views.valueOf(historyToken));
    }
    
    private void LogoutSession() {
        loginService.removeUserFromSession();
        if (mainPanel.getIsInitialized()) {
            mainPanel.Logout();
        }
    }
}
