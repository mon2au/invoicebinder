package com.invoicebinder.server.service;

import com.invoicebinder.client.service.user.UserService;
import com.invoicebinder.server.dataaccess.UserDAO;
import com.invoicebinder.shared.entity.contact.Email;
import com.invoicebinder.shared.entity.user.User;
import com.invoicebinder.shared.model.GridColSortInfo;
import com.invoicebinder.shared.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;

@SuppressWarnings("serial")
@Transactional(rollbackFor = Exception.class)
@Service("user")
public class UserServiceImpl extends RemoteServiceServlet implements
        UserService {
       
    @Autowired(required=false)
    private UserDAO userDAO;
    
    @Override
    public Boolean registerUser(UserInfo info) {
        User user = new User();
        Email email = new Email();
        String actualPassword;
        
        if (info.getUserNewPassword().isEmpty()) {
            actualPassword = info.getDatabasePassword();
        }
        else {
            actualPassword = info.getUserNewPassword();
        }
        
        //set id if this is an update.
        if (info.getId() != 0) {
            user.setId(info.getId());     
        }
        user.setUsername(info.getUserName());
        user.setFirstname(info.getFirstName());
        user.setLastname(info.getLastName());
        user.setPassword(actualPassword);
        email.setEmailAddress(info.getPrimaryEmailAddr());
        user.setPrimaryEmailId(email);
       
        return userDAO.registerUser(user);
    }

    @Override
    public UserInfo getUserInfo(String userName) {
        UserInfo info = new UserInfo();
        User user;
        
        if (userName != null) {
            user = userDAO.findUser(userName);
            
            info.setId(user.getId());
            info.setUserName(user.getUsername());
            info.setFirstName(user.getFirstname());
            info.setLastName(user.getLastname());
            info.setPrimaryEmailAddr(user.getPrimaryEmailId().getEmailAddress());
            info.setDatabasePassword(user.getPassword());
            info.setLoginTimeStamp(user.getLogintimestamp());
        }
        return info;
    }
    
    @Override
    public ArrayList<UserInfo> getAllUsersInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String userNameFilter) {
        ArrayList<UserInfo> allUserInfo = new ArrayList<UserInfo>();
        ArrayList<User> userList;
        UserInfo info;
        
        userList = userDAO.getAllUsersInfo(start, length, sortList, userNameFilter);
        
        for(User user : userList) {
            info = new UserInfo();
            info.setId(user.getId());
            info.setUserName(user.getUsername());
            info.setFirstName(user.getFirstname());
            info.setLastName(user.getLastname());
            info.setPrimaryEmailAddr(user.getPrimaryEmailId().getEmailAddress());
            info.setLoginTimeStamp(user.getLogintimestamp());
            allUserInfo.add(info);
        }
        return allUserInfo;
    }
    
    @Override
    public int deleteUsers(long[] ids) {
        return userDAO.deleteUsers(ids);
    }

    @Override
    public int getUsersCount(String userNameFilter) {
        return userDAO.getUsersCount(userNameFilter);
    }
}
