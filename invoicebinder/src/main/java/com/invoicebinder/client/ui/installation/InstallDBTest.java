/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.installation;

import com.invoicebinder.client.service.installation.InstallServiceClientImpl;
import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.enums.ServiceResponseStatus;
import com.invoicebinder.shared.misc.Constants;
import com.invoicebinder.shared.model.DBConnectionInfo;
import com.invoicebinder.shared.model.ServerValidationResult;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author mon2
 */
public class InstallDBTest extends Composite {
    
    private static final InstallDBUiBinder uiBinder = GWT.create(InstallDBUiBinder.class);
    private final Loading loading;      
    @UiField TextBox mysqlHost;
    @UiField TextBox mysqlUser;
    @UiField IntegerBox mysqlPort;
    @UiField PasswordTextBox mysqlPassword;
    @UiField Button nextBtn;
    @UiField Label title;
    @UiField Image logoImage;
    @UiField Label lblInstallMessages;
    
    private final Main main;
    private final InstallServiceClientImpl installService;
    private static final String TESTCONNECTION_BTN_LABEL = "Test Connection";
    private static final String NEXT_BTN_LABEL = "Next";
    
    public void updateValidationStatus(ValidationResult result) {
        Element element;
        
        element = DOM.getElementById(result.getTagname());
        element.focus();
        ValidationPopup.Show(result.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }
    
    public void updateDBTestResult(ServerValidationResult result) {
        this.loading.hide();        
        this.lblInstallMessages.setText(result.getMessage());
        
        if (result.getStatus() == ServiceResponseStatus.SUCCESS) {
            nextBtn.setText(NEXT_BTN_LABEL);
            this.lblInstallMessages.setStyleName("message-successvisible");
            this.setFieldsReadOnly();
        }
        else {
            this.lblInstallMessages.setStyleName("message-errorvisible");            
        }
        Window.scrollTo(0, 0);        
    }
    
    interface InstallDBUiBinder extends UiBinder<Widget, InstallDBTest> {
    }
    
    public InstallDBTest(Main main) {
        this.main = main;
        this.loading = new Loading();         
        this.installService = new InstallServiceClientImpl(GWT.getModuleBaseURL() + "services/install", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        logoImage.setUrl("images/invoicebindersmall.png");
        title.setText(Constants.TITLE);
        title.setStyleName("installpage-producttitle");
        nextBtn.addClickHandler(new DefaultClickHandler(this));
        nextBtn.setText(TESTCONNECTION_BTN_LABEL);
        
        //set ids.
        mysqlHost.getElement().setId("mysqlHost");
        mysqlUser.getElement().setId("mysqlUser");
        mysqlPort.getElement().setId("mysqlPort");
        mysqlPassword.getElement().setId("mysqlPassword");
        
        //set format and default
        mysqlPort.getElement().setAttribute("type", "number");
        mysqlPort.setText("3306");
        mysqlHost.setFocus(true);
    }
    
    private class DefaultClickHandler implements ClickHandler {
        private final InstallDBTest pageReference;
        
        public DefaultClickHandler(InstallDBTest reference) {
            this.pageReference = reference;
        }
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == nextBtn) {
                if (TESTCONNECTION_BTN_LABEL.equals(nextBtn.getText())){
                    DBConnectionInfo info = new DBConnectionInfo();
                    
                    info.setHostName(mysqlHost.getText());
                    info.setLogin(mysqlUser.getText());
                    info.setPassword(mysqlPassword.getText());
                    info.setPort(mysqlPort.getText());
                    
                    installService.testDBConnection(info, loading);
                }
                
                if (NEXT_BTN_LABEL.equals(nextBtn.getText())) {
                    //set the required database connection values
                    this.pageReference.main.getInstallInfo().setDatabaseInfo(
                            new DBConnectionInfo(mysqlHost.getText(), 
                            mysqlUser.getText(),
                            mysqlPassword.getText(),
                            mysqlPort.getText()));
                    
                    History.newItem(Views.installappconfig.toString());
                    event.getNativeEvent().preventDefault();
                }
            }
        }
    }
    
    private void setFieldsReadOnly() {
        this.mysqlHost.setReadOnly(true);
        this.mysqlPassword.setReadOnly(true);
        this.mysqlUser.setReadOnly(true);
        this.mysqlPort.setReadOnly(true);
    }
}
