package com.invoicebinder.client.service.login;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.AutoLoginViews;
import com.invoicebinder.shared.misc.FieldVerifier;
import com.invoicebinder.shared.misc.Utils;
import com.invoicebinder.shared.model.AuthenticationResult;
import com.invoicebinder.shared.model.AutoLoginProps;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 *
 * @author mon2
 */
public class LoginServiceClientImpl implements LoginServiceClientInt {
    private final LoginServiceAsync service;
    private final Main mainui;
    
    public LoginServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(LoginService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }

    @Override
    public void authenticateAutoLogin(AutoLoginProps loginProps, AutoLoginViews view) {
        this.service.authenticateAutoLogin(loginProps, view, new AutoLoginCallback(view));
    }

    @Override
    public void authenticateUser(String name, String password) {
        //validation
        AuthenticationResult result = new AuthenticationResult();
        
        if (FieldVerifier.isBlankUserName(name)) {
            result.setAuthenticated(false);
            result.setEmail(null);
            result.setUsername(null);
            result.setMessage("Username cannot be blank.");
            result.setTagname("login");
            mainui.updateLoginDialog((AuthenticationResult)result);
            return;
        }
        
        if (FieldVerifier.isBlankPassword(password)) {
            result.setAuthenticated(false);
            result.setEmail(null);
            result.setUsername(null);
            result.setMessage("Password cannot be blank.");
            result.setTagname("password");
            mainui.updateLoginDialog((AuthenticationResult)result);
            return;
        }
        
        if (!FieldVerifier.isValidUserName(name)) {
            result.setAuthenticated(false);
            result.setEmail(null);
            result.setUsername(null);
            result.setMessage("Username should be atleast 3 characters.");
            result.setTagname("login");
            mainui.updateLoginDialog((AuthenticationResult)result);
            return;
        }
        
        this.service.authenticateUser(name, password, Utils.getAppName(), new LoginCallback());
    }
    
    @Override
    public void getUserFromSession() {
        this.service.getUserFromSession(new SessionLoginCallback());
    }
    
    public void removeUserFromSession() {
        this.service.removeUserFromSession(new SessionLogoutCallback());
    }
    
    @Override
    public void sendForgotPasswordEmail(String login, Loading loading) {
        loading.show();
        loading.runAnimation();    
        this.service.sendForgotPasswordEmail(login, new ForgotPasswordEmailCallback());
    }
    
    // <editor-fold defaultstate="collapsed" desc="Callback Handlers">  
    private class LoginCallback implements AsyncCallback<AuthenticationResult> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(AuthenticationResult result) {
            mainui.updateLoginDialog((AuthenticationResult)result);
        }
    }
    private class AutoLoginCallback implements AsyncCallback<AuthenticationResult> {
        AutoLoginViews view;

        public AutoLoginCallback(AutoLoginViews view) {
            this.view = view;
        }
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(AuthenticationResult result) {
            mainui.displayAutoLoginView(result, this.view);
        }
    }
    private class SessionLoginCallback implements AsyncCallback {
        AuthenticationResult res;
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
            History.newItem(Views.sessionexpired.toString());
        }
        @Override
        public void onSuccess(Object result) {
            res = (AuthenticationResult)result;
            if (!res.isAuthenticated()) {
                History.newItem(Views.sessionexpired.toString());
            }
        }
    }
    private class SessionLogoutCallback implements AsyncCallback {
        AuthenticationResult res;
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            res = (AuthenticationResult)result;
            if (!res.isAuthenticated()) {
                History.newItem(Views.logout.toString());
            }
        }
    }
    private class ForgotPasswordEmailCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            mainui.updateForgotPasswordDialog((String)result);
        }       
    }
    // </editor-fold>    
}
