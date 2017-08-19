package com.invoicebinder.client.service.login;

import com.invoicebinder.client.ui.alert.Loading;
import com.invoicebinder.shared.enums.AutoLoginViews;
import com.invoicebinder.shared.model.AutoLoginProps;

/**
 *
 * @author mon2
 */
public interface LoginServiceClientInt {
    void authenticateUser(String name, String password);
    void authenticateAutoLogin(AutoLoginProps loginProps, AutoLoginViews view);
    void getUserFromSession();
    void sendForgotPasswordEmail(String login, Loading loading);
}
