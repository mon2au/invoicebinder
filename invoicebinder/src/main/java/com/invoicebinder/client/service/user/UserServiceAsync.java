package com.invoicebinder.client.service.user;

import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.UserInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;

public interface UserServiceAsync {
    public void registerUser(UserInfo info,  AsyncCallback<Boolean> asyncCallback);
    public void getUserInfo(String userName, AsyncCallback<UserInfo> asyncCallback); 
    public void getAllUsersInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String userNameFilter, AsyncCallback<ArrayList<UserInfo>> asyncCallback);     
    public void deleteUsers(long[] id, AsyncCallback<Integer> asyncCallback);
    public void getUsersCount(String userNameFilter, AsyncCallback<Integer> asyncCallback);
}
