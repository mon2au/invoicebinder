/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invoicebinder.client.service.user;

import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.UserInfo;
import com.google.gwt.view.client.AsyncDataProvider;

import java.util.ArrayList;

/**
 *
 * @author msushil
 */
public interface UserServiceClientInt {
    void registerUser(UserInfo info);
    void getUserInfo(String userName);   
    void saveUserPassword(UserInfo info);
    void getAllUsers(int start, int length, ArrayList<GridColSortInfo> sortList, String userNameFilter, AsyncDataProvider<UserInfo> provider);
    void deleteUsers(long[] ids);  
    void getUsersCount(String userNameFilter);       
}
