package com.invoicebinder.client.service.user;

import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.UserInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;

@RemoteServiceRelativePath("/invoicebinder/services/user")
public interface UserService extends RemoteService {
    public Boolean registerUser(UserInfo info);
    public UserInfo getUserInfo(String username); 
    public ArrayList<UserInfo> getAllUsersInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String userNameFilter); 
    public int deleteUsers(long[] id); 
    public int getUsersCount(String userNameFilter);       
}
