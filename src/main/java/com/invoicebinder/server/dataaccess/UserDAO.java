package com.invoicebinder.server.dataaccess;

import static com.invoicebinder.core.exception.ExceptionManager.getFormattedExceptionMessage;
import com.invoicebinder.core.exception.ExceptionType;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.user.User;
import com.invoicebinder.shared.model.GridColSortInfo;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends BaseDAO<User,Long> {
    
    public UserDAO() {
        super(User.class);
    }
    
    @SuppressWarnings("unchecked")
    public User findUser(String login) {
        User user = null;
        Session session = sf.getCurrentSession();
        
        try {
            //check by username
            user = (User) session
                    .createQuery("from User u where u.username=:username")
                    .setString("username", login).uniqueResult();
            
            //check if found by username
            if (user != null) {
                return user;
            }
            
            //check by email
            user = (User) sf.getCurrentSession()
                    .createQuery("select u from User u inner join u.primaryEmailId pid where pid.emailAddress=:username")
                    .setString("username", login).uniqueResult();            

            //check if found by email
            if (user != null) {
                return user;
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(UserDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                                      
            throw e;
        }
        return user;
    }
    
    @SuppressWarnings("unchecked")
    public User authenticateUser(String login, String password, String appName) {
        User user = null;
        Session session;
        
        session = sf.getCurrentSession();
        try {
            //authenticate with username, password
            user = (User) session
                    .createQuery("from User u where u.username=:username and u.password=:password")
                    .setString("username", login).setString("password", password).uniqueResult();
            
            //check if authentication successful
            if (user != null) {
                //update login timestamp
                user.setLogintimestamp(new Date());
                session.saveOrUpdate(user);
                return user;
            }
            
            //authenticate with email, password
            user = (User) sf.getCurrentSession()
                    .createQuery("select u from User u inner join u.primaryEmailId pid where pid.emailAddress=:username and " +
                            "u.password=:password")
                    .setString("username", login).setString("password", password).uniqueResult();
            
            
            //check if authentication successful
            if (user != null) {
                //update login timestamp
                user.setLogintimestamp(new Date());
                session.saveOrUpdate(user);
                return user;
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(UserDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));            
            throw e;
        }
        
        return user;
    }
    
    @SuppressWarnings("unchecked")
    public Boolean registerUser(User user) {
        Session session;
        Boolean result;
        result = false;
        
        try {
            session = sf.getCurrentSession();
            session.saveOrUpdate(user.getPrimaryEmailId());
            session.saveOrUpdate(user);
            result = true;
            
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(UserDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                     
            throw e;
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public void setPassword(String userName, String password) {
        User user;
        Session session = sf.getCurrentSession();        
        try {
          //check by username
            user = (User) session
                    .createQuery("from User u where u.username=:username")
                    .setString("username", userName).uniqueResult();
            
            //check if found by username
            if (user == null) {
            //check by email
            user = (User) sf.getCurrentSession()
                    .createQuery("select u from User u inner join u.primaryEmailId pid where pid.emailAddress=:username")
                    .setString("username", userName).uniqueResult();   
            }

            //check if found by email
            if (user != null) {
                user.setPassword(password);
                session.saveOrUpdate(user);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(UserDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));                    
            throw e;
        }
    }
    
    @SuppressWarnings("unchecked")    
    public ArrayList<User> getAllUsersInfo(int start, int length, ArrayList<GridColSortInfo> sortList, String userNameFilter) {
        ArrayList<User> userList = null;
        String sql;
        
        try {
            sql = "Select u from User u where u.id >= :start ";
            
            if (!userNameFilter.isEmpty()) {
                sql += "and u.username LIKE '%" + userNameFilter + "%' ";
            }
            sql += "order by u.username";
            
            userList = (ArrayList<User>) sf.getCurrentSession()
                    .createQuery(sql)
                    .setParameter("start", (long) start)
                    .setMaxResults(length).list();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(UserDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e)); 
            throw e;
        }
        
        return userList;
    }
    
    @SuppressWarnings("unchecked")
    public int deleteUsers(long[] ids) {
        int result = 0;
        User user;
        Session session;
        
        try {
            session = sf.getCurrentSession();
            
            for (int i=0;i<ids.length;i++) {
                
                user = (User)session.get(User.class, ids[i]);
                session.delete(user);
            }
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(UserDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e)); 
            throw e;
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public int getUsersCount(String userNameFilter) {
        Long value;
        int count = 0;
        String sql;
        
        try {
            sql = "Select count(u) from User u ";
            
            if (!userNameFilter.isEmpty()) {
                sql += "where u.username LIKE '%" + userNameFilter + "%' ";
            }
            
            value = (Long) sf.getCurrentSession()
                    .createQuery(sql).uniqueResult();
            count = value.intValue();
        } catch (HibernateException e) {
            ServerLogManager.writeErrorLog(UserDAO.class, getFormattedExceptionMessage(ExceptionType.DataAccessException, e));  
            throw e;
        }
        return count;
    }
}
