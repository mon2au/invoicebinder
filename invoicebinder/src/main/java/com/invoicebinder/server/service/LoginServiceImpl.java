package com.invoicebinder.server.service;

import com.invoicebinder.invoicebindercore.misc.PasswordGenerator;
import com.invoicebinder.client.service.login.LoginService;
import com.invoicebinder.server.dataaccess.ClientDAO;
import com.invoicebinder.server.dataaccess.InvoiceDAO;
import com.invoicebinder.server.dataaccess.UserDAO;
import com.invoicebinder.server.logger.ServerLogManager;
import com.invoicebinder.shared.entity.invoice.Invoice;
import com.invoicebinder.shared.entity.user.User;
import com.invoicebinder.shared.enums.AutoLoginViews;
import com.invoicebinder.shared.enums.client.ClientStatus;
import com.invoicebinder.shared.model.AuthenticationResult;
import com.invoicebinder.shared.model.AutoLoginProps;
import com.invoicebinder.shared.model.MailInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Transactional(rollbackFor = RuntimeException.class)
@Service("login")
public class LoginServiceImpl extends RemoteServiceServlet implements
        LoginService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private InvoiceDAO invoiceDAO;

    @Override
    public AuthenticationResult authenticateUser(String login, String password, String appName) {
        AuthenticationResult result = new AuthenticationResult();
        User user;

        ServerLogManager.writeInformationLog(LoginServiceImpl.class, "User " + login + " authenticating.");

        user = userDAO.authenticateUser(login, password, appName);
        
        if (user != null) {
            storeUserInSession(user);
            result.setUsername(user.getUsername());
            result.setEmail(user.getPrimaryEmailId().getEmailAddress());
            result.setAuthenticated(true);
            ServerLogManager.writeInformationLog(LoginServiceImpl.class, "User " + login + " authenticated successfully.");
            
            //get the count of clients.
            result.setClientCount(clientDAO.getClientsCount("", ClientStatus.ALL));
        }
        else {
            result.setAuthenticated(false);
            result.setMessage("Invalid login/password combination.");
        }
        
        return result;
    }

    @Override
    public AuthenticationResult authenticateAutoLogin(AutoLoginProps loginProps, AutoLoginViews view) {
        AuthenticationResult result = new AuthenticationResult();
        Invoice invoice;

        invoice = invoiceDAO.authenticateInvoice(loginProps.getInvoiceAmount(), loginProps.getInvoiceAuthToken(), loginProps.getInvoiceNumber());

        if (invoice != null) {
            result.setAuthenticated(true);
            ServerLogManager.writeInformationLog(LoginServiceImpl.class, "Invoice " + invoice + " authenticated successfully.");
        }
        else {
            result.setAuthenticated(false);
            result.setMessage("Incorrect invoice information.");
        }

        return result;
    }

    @Override
    public AuthenticationResult getUserFromSession() {
        User user;
        AuthenticationResult result = new AuthenticationResult();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        
        if (requestAttributes != null) {
            HttpServletRequest req = requestAttributes.getRequest();
            HttpSession session = req.getSession(true);
            Object userObj = session.getAttribute("user");
            if (userObj != null && userObj instanceof User)
            {
                user = (User) userObj;
                result.setUsername(user.getUsername());
                result.setAuthenticated(true);
            }
        }
        return result;
    }
    
    @Override
    public void removeUserFromSession() {
        deleteUserFromSession();
    }

    @Override
    public String sendForgotPasswordEmail(String login) {
        String result;       
        User user = userDAO.findUser(login);
        MailServiceImpl mail = new MailServiceImpl(); 
        MailInfo info = new MailInfo();
        String password;      
        
        if (user != null) {
            //generate new password and save it
            password = PasswordGenerator.generatePassword();
            userDAO.setPassword(login, password);
            //send email with new password
            info.setRecipientEmail(user.getPrimaryEmailId().getEmailAddress());
            info.setSubject("New Password Request");
            info.setNewPassword(password);
            
            if (mail.sendForgotPasswordMail(info)) {
                result = "A new password has been generated and sent to your email. Please check your email and return to the login dialog to login.";           
            }
            else {
                result = "An error has occurred while sending email.";
            }
        }
        else {
            result = "The system is unable to find you in the database. Make sure to enter the correct username or email address.";
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Utility Methods">  
    private void storeUserInSession(User user) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest req = requestAttributes.getRequest();
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
        }
    }
    private void deleteUserFromSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest req = requestAttributes.getRequest();
            HttpSession session = req.getSession(true);
            session.removeAttribute("user");
        }
    }
    // </editor-fold>       
}
