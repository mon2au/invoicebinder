package com.invoicebinder.client.ui.pages.unrestricted;

import com.invoicebinder.shared.model.AuthenticationResult;
import com.invoicebinder.client.service.login.LoginServiceClientImpl;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.invoicebinder.shared.misc.Utils;

public class LoginBox extends DialogBox {
    private static final LoginBoxUiBinder uiBinder = GWT
            .create(LoginBoxUiBinder.class);

    public void setCloseVisible(Boolean showCloseButton) {
        this.cancelLoginButton.setVisible(showCloseButton);
        this.cancelForgotButton.setVisible(showCloseButton);
    }
    
    interface LoginBoxUiBinder extends UiBinder<Widget, LoginBox> {
    }
    
    @UiField Button loginButton;
    @UiField Button forgotPasswordButton;
    @UiField Button cancelLoginButton;
    @UiField Button cancelForgotButton;
    @UiField TextBox login;
    @UiField TextBox password;
    @UiField TextBox userNameOrEmail;
    @UiField Label loginMessage;
    @UiField Label forgotPasswordMessage;
    @UiField Anchor forgotPassword;
    @UiField Anchor returnToLogin;
    @UiField HTMLPanel loginPanel;
    @UiField HTMLPanel forgotPasswordPanel;
    private final LoginServiceClientImpl loginService;
    private final Main mainPanel;
    private final Loading loading;
    
    public LoginBox(Object main){
        this.loginService = new LoginServiceClientImpl(GWT.getModuleBaseURL() + "services/login", (Main)main);
        this.setSize("500px", "300px");
        this.add(uiBinder.createAndBindUi(this));
        this.setModal(true);
        this.setGlassEnabled(true);
        this.setStyleName(""); //disable gwt styles.
        showLogin();
        this.loading = new Loading();
        login.getElement().setId("login");
        password.getElement().setId("password");
        password.addKeyDownHandler(new DefaultKeyDownHandler(this));
        userNameOrEmail.addKeyDownHandler(new DefaultKeyDownHandler(this));
        loginButton.getElement().setId("loginbutton");
        loginButton.addClickHandler(new LoginClickHandler());
        forgotPasswordButton.getElement().setId("loginbutton");
        forgotPasswordButton.addClickHandler(new ForgotPasswordButtonClickHandler(this));
        cancelLoginButton.getElement().setId("cancelbutton");
        cancelLoginButton.addClickHandler(new CancelClickHandler(this));
        cancelForgotButton.getElement().setId("cancelbutton");
        cancelForgotButton.addClickHandler(new CancelClickHandler(this));
        forgotPassword.setText("Forgot your password.");
        forgotPassword.setStyleName("light-links");
        forgotPassword.addClickHandler(new ForgotPasswordClickHandler(this));
        returnToLogin.setText("Back to login.");
        returnToLogin.setStyleName("light-links");
        returnToLogin.addClickHandler(new ReturnToLoginClickHandler(this));
        loginMessage.getElement().setId("message");
        mainPanel = (Main)main;
        this.hide();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Callback Updates">
    public void updateLoginDialog (AuthenticationResult result) {
        if (result.isAuthenticated()) {
            this.mainPanel.setUserEmail(result.getEmail());
            this.mainPanel.setUserName(result.getUsername());
            this.mainPanel.hideLoginDialog();
            History.newItem(Views.dashboard.toString());
        }
        else {
            loginMessage.setText(result.getMessage());
            DOM.getElementById(result.getTagname()).focus();
        }
    }
    public void updateForgotPasswordDialog(String result) {
        loading.hide();
        forgotPasswordMessage.setText(result);
        clearForgotPassword();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class LoginClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            loginService.authenticateUser(login.getText(), password.getText());
        }
    }
    private class ForgotPasswordButtonClickHandler implements ClickHandler {
        private final LoginBox loginBoxReference;
        public ForgotPasswordButtonClickHandler(LoginBox loginReference) {
            this.loginBoxReference = loginReference;
        }
        @Override
        public void onClick(ClickEvent event) {
            this.loginBoxReference.forgotPasswordMessage.setText("");
            loginService.sendForgotPasswordEmail(userNameOrEmail.getText(), this.loginBoxReference.loading);
        }
    }
    private class DefaultKeyDownHandler implements KeyDownHandler {
        LoginBox loginBoxReference;
        
        public DefaultKeyDownHandler(LoginBox loginBox) {
            this.loginBoxReference = loginBox;            
        }
                
        @Override
        public void onKeyDown(KeyDownEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                if (loginPanel.isVisible()) {
                    loginService.authenticateUser(login.getText(), password.getText());
                }
                
                if (forgotPasswordPanel.isVisible()) {
                    this.loginBoxReference.forgotPasswordMessage.setText("");
                    loginService.sendForgotPasswordEmail(userNameOrEmail.getText(), this.loginBoxReference.loading);                    
                }
            }
        }
    }
    private class CancelClickHandler implements ClickHandler {
        private final LoginBox loginBoxReference;
        public CancelClickHandler(LoginBox loginReference) {
            this.loginBoxReference = loginReference;
        }
        @Override
        public void onClick(ClickEvent event) {
            this.loginBoxReference.loginMessage.setText("");
            this.loginBoxReference.forgotPasswordMessage.setText("");
            this.loginBoxReference.showLogin();
            this.loginBoxReference.hide();
        }
    }
    private class ForgotPasswordClickHandler implements ClickHandler {
        private final LoginBox loginBoxReference;
        public ForgotPasswordClickHandler(LoginBox loginReference) {
            this.loginBoxReference = loginReference;
        }
        @Override
        public void onClick(ClickEvent event) {
            if (Utils.isDemoApplication()) return;
            this.loginBoxReference.showForgotPassword();
        }
    }
    private class ReturnToLoginClickHandler implements ClickHandler {
        private final LoginBox loginBoxReference;
        public ReturnToLoginClickHandler(LoginBox loginReference) {
            this.loginBoxReference = loginReference;
        }
        @Override
        public void onClick(ClickEvent event) {
            this.loginBoxReference.forgotPasswordMessage.setText("");
            this.loginBoxReference.showLogin();
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="UI Management">
    public void setLoginFocus() {
        this.login.setFocus(true);
    }
    private void showForgotPassword() {
        this.loginPanel.setVisible(false);
        this.forgotPasswordPanel.setVisible(true);
    }
    private void showLogin() {
        this.loginPanel.setVisible(true);
        this.forgotPasswordPanel.setVisible(false);
    }
    public void clearLogin() {
        this.login.setText("");
        this.password.setText("");
    }
    public void clearForgotPassword() {
        this.userNameOrEmail.setText("");
    }
    // </editor-fold>
}
