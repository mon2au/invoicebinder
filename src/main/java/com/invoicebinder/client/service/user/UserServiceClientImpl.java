/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package com.invoicebinder.client.service.user;

import com.invoicebinder.client.ui.alert.Alert;
import com.invoicebinder.client.ui.alert.AlertLevel;
import com.invoicebinder.client.ui.controller.Main;
import com.invoicebinder.client.ui.controller.Views;
import com.invoicebinder.shared.misc.FieldVerifier;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.UserInfo;
import com.invoicebinder.shared.model.ValidationResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mon2
 */
public class UserServiceClientImpl implements UserServiceClientInt {
    private final UserServiceAsync service;
    private final Main mainui;
    
    public UserServiceClientImpl(String url, Main mainui) {
        System.out.print(url);
        this.service = GWT.create(UserService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
        endpoint.setServiceEntryPoint(url);
        this.mainui = mainui;
    }
    
    @Override
    public void registerUser(UserInfo info) {
        this.service.registerUser(info, new RegisterUserCallback());
    }
    
    @Override
    public void getUserInfo(String userName) {
        this.service.getUserInfo(userName, new LoadUserDetailsCallback());
    }
    
    @Override
    public void saveUserPassword(UserInfo info) {
        ValidationResult result = new ValidationResult();
        //database password
        if (FieldVerifier.isBlankField(info.getUserPassword())) {
            result.setMessage("Old password must be entered.");
            result.setTagname("txtOldPassword");
            mainui.getContainer().doValidation(Views.useraccountdetails, result);
            return;
        }
        
        //user password
        if (!info.getDatabasePassword().equals(info.getUserPassword())) {
            result.setMessage("Password is incorrect.");
            result.setTagname("txtOldPassword");
            mainui.getContainer().doValidation(Views.useraccountdetails, result);
            return;
        }
        
        //new password
        if (FieldVerifier.isBlankField(info.getUserNewPassword())) {
            result.setMessage("New password must be entered.");
            result.setTagname("txtNewPassword");
            mainui.getContainer().doValidation(Views.useraccountdetails, result);
            return;
        }
        
        //confirm password
        if (FieldVerifier.isBlankField(info.getUserConfirmPassword())) {
            result.setMessage("Confirm password must be entered.");
            result.setTagname("txtConfirmNewPassword");
            mainui.getContainer().doValidation(Views.useraccountdetails, result);
            return;
        }
        
        //confirm password
        if (!info.getUserNewPassword().equals(info.getUserConfirmPassword())) {
            result.setMessage("New password and confirm password do not match.");
            result.setTagname("txtConfirmNewPassword");
            mainui.getContainer().doValidation(Views.useraccountdetails, result);
            return;
        }
        
        this.service.registerUser(info, new SaveUserPasswordCallback());
    }
    
    @Override
    public void getAllUsers(int start, int length, ArrayList<GridColSortInfo> sortList, String userNameFilter, AsyncDataProvider<UserInfo> provider) {
        this.service.getAllUsersInfo(start, length,  sortList, userNameFilter, new GetUserListCallback(provider, start));
    }
    
    @Override
    public void deleteUsers(long[] ids) {
        this.service.deleteUsers(ids, new DeleteUserCallback());
    }
    
    @Override
    public void getUsersCount(String userNameFilter) {
        this.service.getUsersCount(userNameFilter, new GetUsersCountCallback());
    }
    
    // <editor-fold defaultstate="collapsed" desc="Callback Handlers">
    private class LoadUserDetailsCallback implements AsyncCallback<UserInfo> {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(UserInfo result) {
            mainui.getContainer().loadUserDetails(result);
        }
    }
    private class SaveUserPasswordCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        
        @Override
        public void onSuccess(Object result) {
            mainui.updatePasswordSaveStatus((Boolean)result);
        }
    }
    private class GetUsersCountCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            mainui.updateUsersCount((Integer)result);
        }
    }
    private class GetUserListCallback implements AsyncCallback {
        private final AsyncDataProvider<UserInfo> dataProvider;
        private final int start;
        
        public GetUserListCallback(AsyncDataProvider<UserInfo> provider, int start){
            this.dataProvider = provider;
            this.start = start;
        }
        
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            this.dataProvider.updateRowData(start,(List<UserInfo>)result);
        }
    }
    private class DeleteUserCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
            if ((Integer)result == 0) {
                mainui.getContainer().refreshUsers();
            } else {
                Alert.show("unable to delete.", AlertLevel.ERROR);
            }
        }
    }
    private class RegisterUserCallback implements AsyncCallback {
        @Override
        public void onFailure(Throwable caught) {
            Alert.show(caught.getMessage(), AlertLevel.ERROR);
        }
        @Override
        public void onSuccess(Object result) {
             History.newItem(Views.users.toString());            
        }
    }    
    // </editor-fold>
}
