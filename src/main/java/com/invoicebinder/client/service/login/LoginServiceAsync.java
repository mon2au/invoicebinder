package com.invoicebinder.client.service.login;

import com.invoicebinder.shared.enums.AutoLoginViews;
import com.invoicebinder.shared.model.AuthenticationResult;
import com.invoicebinder.shared.model.AutoLoginProps;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface LoginServiceAsync {
    public void authenticateUser(String name, String password, String appName, AsyncCallback<AuthenticationResult> callback);
    public void authenticateAutoLogin(AutoLoginProps loginProps, AutoLoginViews view, AsyncCallback<AuthenticationResult> callback);
    public void sendForgotPasswordEmail(String login, AsyncCallback callback);
    public void getUserFromSession(AsyncCallback callback);
    public void removeUserFromSession(AsyncCallback callback);
}
