/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.client.ui.pages.users;

import com.invoicebinder.client.service.user.UserServiceClientImpl;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.enums.user.UserStatus;
import com.invoicebinder.shared.misc.Constants;
import static com.invoicebinder.shared.misc.Utils.getParamFromHref;
import static com.invoicebinder.shared.misc.Utils.isDemoApplication;
import com.invoicebinder.shared.model.UserInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author msushil
 */
public class NewUser extends Composite {
    
    private static final NewUserUiBinder uiBinder = GWT.create(NewUserUiBinder.class);
    @UiField HTMLPanel newUserPanel;
    private Button btnSave;
    private Button btnCancel;
    private final Label lblHeader;
    private final Label lblUserHeader;
    private final FlexTable mainTable;
    private TextBox txtUserName;
    private TextBox txtFirstName;
    private TextBox txtLastName;
    private TextBox txtPassword;
    private TextBox txtConfirmPassword;
    private TextBox txtEmail;
    
    private final VerticalPanel mainPanel;
    private final   UserServiceClientImpl userService;
    private final Main main;
    private int userId = 0;
    
    interface NewUserUiBinder extends UiBinder<Widget, NewUser> {
    }
    
    public NewUser(Main main) {
        this.main = main;
        this.userService = new UserServiceClientImpl(GWT.getModuleBaseURL() + "services/user", this.main);
        initWidget(uiBinder.createAndBindUi(this));
        this.mainPanel = new VerticalPanel();
        this.lblHeader = new Label();
        this.lblUserHeader = new Label();
        this.userId = Integer.parseInt(getParamFromHref("userId"));
        mainTable = getUserTable();
        //set all headers
        lblHeader.getElement().setInnerHTML("<h5><span>New User</span></h5>");
        lblUserHeader.getElement().setInnerHTML("<h6><span>User Details</span></h6>");
        
        //add all panels
        mainPanel.add(lblHeader);
        mainPanel.add(lblUserHeader);
        mainPanel.add(mainTable);
        mainPanel.add(buttonsPanel());
        mainPanel.setStyleName("container");
        newUserPanel.add(mainPanel);
        
        if (userId != 0) {
            loadUser(userId);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Save / Load">
    private void saveUser() {
        UserInfo user = new UserInfo();
        if (this.userId != 0) {
            user.setId((long)this.userId);
        }
        
        user.setUserName(txtUserName.getText());
        user.setFirstName(txtFirstName.getText());
        user.setLastName(txtLastName.getText());
        user.setUserStatus(UserStatus.ACTIVE.toString());
        user.setUserPassword(txtPassword.getText());
        user.setUserConfirmPassword(txtConfirmPassword.getText());
        user.setPrimaryEmailAddr(txtEmail.getText());
        userService.registerUser(user);
    }
    
    private void loadUser(int userId) {
        //userService.getUserInfo(userId);
    }
    
    public void populateUserInfo(UserInfo info) {
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="UI Component Tables and Panels">
    private FlexTable getUserTable() {
        FlexTable userTable = new FlexTable();
        txtUserName = new TextBox();
        txtFirstName = new TextBox();
        txtLastName = new TextBox();
        txtPassword = new TextBox();
        txtConfirmPassword = new TextBox();
        txtEmail = new TextBox();
        
        userTable.setCellSpacing(Constants.PANEL_CELL_SPACING);
        userTable.setPixelSize(Constants.FORM_WIDTH, Constants.FORM_ROW_HEIGHT);
        
        userTable.setHTML(0, 0, "User Name");
        userTable.setWidget(0, 1, txtUserName);
        userTable.setHTML(1, 0, "Email Address");
        userTable.setWidget(1, 1, txtEmail);
        userTable.setHTML(2, 0, "First Name");
        userTable.setWidget(2, 1, txtFirstName);
        userTable.setHTML(3, 0, "Fast Name");
        userTable.setWidget(3, 1, txtLastName);
        userTable.setHTML(4, 0, "Password");
        userTable.setWidget(4, 1, txtPassword);
        userTable.setHTML(5, 0, "Confirm Password");
        userTable.setWidget(5, 1, txtConfirmPassword);
        return userTable;
    }
    
    private HorizontalPanel buttonsPanel() {
        HorizontalPanel panel = new HorizontalPanel();
        btnSave = new Button();
        btnCancel = new Button();
        
        btnSave.setStyleName("appbutton-default");
        btnCancel.setStyleName("appbutton-default");
        btnSave.setText("Save");
        btnCancel.setText("Cancel");
        panel.setSpacing(Constants.PANEL_CELL_SPACING);
        btnSave.addClickHandler(new DefaultClickHandler(this));
        btnCancel.addClickHandler(new DefaultClickHandler(this));
        panel.add(btnSave);
        panel.add(btnCancel);
        return panel;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Event Handlers">
    private class DefaultClickHandler implements ClickHandler {
        private final NewUser pageReference;
        
        public DefaultClickHandler(NewUser reference) {
            this.pageReference = reference;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if (sender == btnSave) {
                if (isDemoApplication()) return;                   
                this.pageReference.saveUser();
            }
            else if (sender == btnCancel) {
                History.newItem(Views.users.toString());
                event.getNativeEvent().preventDefault();
            }
        }
    }
    // </editor-fold>
    
}
