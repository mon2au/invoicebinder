package com.invoicebinder.client.service.login;

import com.invoicebinder.shared.enums.AutoLoginViews;
import com.invoicebinder.shared.model.AuthenticationResult;
import com.invoicebinder.shared.model.AutoLoginProps;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("/invoicebinder/services/login")
public interface LoginService extends RemoteService {
    AuthenticationResult authenticateUser(String name, String password, String appName);
    AuthenticationResult authenticateAutoLogin(AutoLoginProps loginProps, AutoLoginViews view);
    String sendForgotPasswordEmail(String login);
    AuthenticationResult getUserFromSession();
    void removeUserFromSession();
}
