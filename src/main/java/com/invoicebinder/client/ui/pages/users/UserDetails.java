/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.users;

import com.invoicebinder.client.service.user.UserServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.notification.ValidationPopup;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.UserInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author msushil
 */
public class UserDetails extends Composite {
    
    private static final UserAccountDetailsUiBinder uiBinder = GWT.create(UserAccountDetailsUiBinder.class);
    @UiField HTMLPanel userAccountDetailsPanel;
    
    private final Button btnSave;
    private final UserServiceClientImpl userService;
    private final Main main;
    private final VerticalPanel mainPanel;
    private final Label lblHeader;
    private final Label lblPasswordChangeHeader;
    private final Hidden hdnId;
    private Label lblUserName;
    private Label lblFirstName;
    private Label lblLastName;
    private Label lblEmail;
    private Label lblLoginTimeStamp;
    private final Label lblChangePasswordResult;
    private final Hidden hdnPassword;
    
    private PasswordTextBox txtOldPassword;
    private PasswordTextBox txtNewPassword;
    private PasswordTextBox txtConfirmNewPassword;
    private final HorizontalPanel buttonPanel;
    
    private static final String LABELCOLWIDTH = "120px";
    
    public void populateUserDetails(UserInfo userInfo) {
        hdnId.setValue(String.valueOf(userInfo.getId()));
        lblUserName.setText(userInfo.getUserName());
        lblFirstName.setText(userInfo.getFirstName());
        lblLastName.setText(userInfo.getLastName());
        lblEmail.setText(userInfo.getPrimaryEmailAddr());
        lblLoginTimeStamp.setText(userInfo.getLoginTimeStamp().toString());
        hdnPassword.setValue(userInfo.getDatabasePassword());
    }

    public void updatePasswordSaveStatus(Boolean status) {        
        lblChangePasswordResult.setStyleName("message-box");
        lblChangePasswordResult.setWidth("190px");
        lblChangePasswordResult.setVisible(true);
        
        if (status) {
            hdnPassword.setValue(txtNewPassword.getValue());
            txtOldPassword.setValue("");
            txtNewPassword.setValue("");
            txtConfirmNewPassword.setValue("");               
            lblChangePasswordResult.addStyleName("success");
            lblChangePasswordResult.setText("Password has been changed successfully.");
        }
        else {
            lblChangePasswordResult.addStyleName("error");
            lblChangePasswordResult.setText("Error changing password.");
        }             
    }

    public void setValidationResult(ValidationResult result) {
        Element element;
        element = DOM.getElementById(result.getTagname());
        element.focus();
        ValidationPopup.Show(result.getMessage(), element.getAbsoluteRight(), element.getAbsoluteTop());
    }
    
    interface UserAccountDetailsUiBinder extends UiBinder<Widget, UserDetails> {
    }
    
    public UserDetails(Object main) {
        this.main = (Main)main;
        this.userService = new UserServiceClientImpl(GWT.getModuleBaseURL() + "services/user", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        this.mainPanel = new VerticalPanel();
        this.lblHeader = new Label();
        this.lblPasswordChangeHeader = new Label();
        this.btnSave = new Button();
        this.buttonPanel = new HorizontalPanel();
        this.hdnId = new Hidden();
        this.hdnPassword = new Hidden();
        this.lblChangePasswordResult = new Label();
        this.lblChangePasswordResult.setVisible(false);
        
        //set headers
        lblHeader.getElement().setInnerHTML("<h5><span>User Details</span></h5>");
        lblPasswordChangeHeader.getElement().setInnerHTML("<h6><span>Change Password</span></h6>");
        //assemble all components
        mainPanel.add(lblHeader);
        mainPanel.add(getUserAccountDetailsTable());
        mainPanel.add(lblPasswordChangeHeader);
        mainPanel.add(getChangePasswordTable());
        
        //buttons
        btnSave.setStyleName("appbutton-default");
        btnSave.setText("Save");
        buttonPanel.setSpacing(5);
        buttonPanel.add(btnSave);
        buttonPanel.add(lblChangePasswordResult);
        mainPanel.add(buttonPanel);
        mainPanel.setStyleName("container");
        userAccountDetailsPanel.add(mainPanel);
        
        //click handlers
        btnSave.addClickHandler(new DefaultClickHandler(this));
        
        //service
        this.userService.getUserInfo(this.main.getUserName());
    }
    
    // <editor-fold defaultstate="collapsed" desc="UI Component Tables">
    private FlexTable getUserAccountDetailsTable() {
        FlexTable table = new FlexTable();
        lblUserName = new Label();
        lblFirstName = new Label();
        lblLastName = new Label();
        lblEmail = new Label();
        lblLoginTimeStamp = new Label();
        
        table.getColumnFormatter().setWidth(0, LABELCOLWIDTH);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setHTML(1, 0, "");
        //user account information
        table.setHTML(2, 0, "User Name: ");
        table.setWidget(2, 1, lblUserName);
        table.setHTML(3, 0, "First Name: ");
        table.setWidget(3, 1, lblFirstName);
        table.setHTML(4, 0, "Last Name: ");
        table.setWidget(4, 1, lblLastName);
        table.setHTML(5, 0, "Email: ");
        table.setWidget(5, 1, lblEmail);
        table.setHTML(5, 0, "Last Login: ");
        table.setWidget(5, 1, lblLoginTimeStamp);
        return table;
    }
    
    private FlexTable getChangePasswordTable() {
        FlexTable table = new FlexTable();
        txtOldPassword = new PasswordTextBox();
        txtOldPassword.getElement().setId("txtOldPassword");
        txtNewPassword = new PasswordTextBox();
        txtNewPassword.getElement().setId("txtNewPassword");        
        txtConfirmNewPassword = new PasswordTextBox();
        txtConfirmNewPassword.getElement().setId("txtConfirmNewPassword");      
        
        table.getColumnFormatter().setWidth(0, LABELCOLWIDTH);
        table.setCellSpacing(Constants.PANEL_CELL_SPACING);
        table.setHTML(1, 0, "");
        //change password info
        table.setHTML(2, 0, "Old Password: ");
        table.setWidget(2, 1, txtOldPassword);
        table.setHTML(3, 0, "New Password: ");
        table.setWidget(3, 1, txtNewPassword);
        table.setHTML(4, 0, "Confirm Password: ");
        table.setWidget(4, 1, txtConfirmNewPassword);
        return table;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class DefaultClickHandler implements ClickHandler {
        private final UserDetails pageReference;
        
        public DefaultClickHandler(UserDetails reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                if (isDemoApplication()) return;                    
                this.pageReference.saveNewPassword();
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Save / Load">
    private void saveNewPassword() {        
        UserInfo info = new UserInfo();
        info.setId(new Long(this.hdnId.getValue()));
        info.setUserName(lblUserName.getText());
        info.setFirstName(lblFirstName.getText());
        info.setLastName(lblLastName.getText());
        info.setPrimaryEmailAddr(lblEmail.getText());
        info.setDatabasePassword(hdnPassword.getValue());
        info.setUserPassword(txtOldPassword.getText());
        info.setUserNewPassword(txtNewPassword.getText());
        info.setUserConfirmPassword(txtConfirmNewPassword.getText());
        userService.saveUserPassword(info);
    }
}
