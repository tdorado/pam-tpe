package com.td.wallendar.login.ui;

public interface LoginView {
    void loginSuccessful(long userId, String phoneNumber);

    void loginFailed();
}
