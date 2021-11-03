package com.td.wallendar.utils.login;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginUtils {

    public final static String LOGGED_USER_ID_SHARED_PREFERENCES = "LOGGED_USER_ID_SP";
    private final static String LOGGED_USER_ID = "LOGGED_USER_ID";

    private final static long loggedOutValue = -1;

    private final SharedPreferences loginSharedPreferences;

    public LoginUtils(SharedPreferences loginSharedPreferences) {
        this.loginSharedPreferences = loginSharedPreferences;
    }

    public void setLoggedUserId(long value) {
        Editor editor = loginSharedPreferences.edit();
        editor.putLong(LOGGED_USER_ID, value);
        editor.apply();
    }

    public void logout(){
        Editor editor = loginSharedPreferences.edit();
        editor.putLong(LOGGED_USER_ID, loggedOutValue);
        editor.apply();
    }

    public long getLoggedUserId() throws UserNotLoggedInException {
        long value = loginSharedPreferences.getLong(LOGGED_USER_ID, loggedOutValue);

        if(value == loggedOutValue){
            throw new UserNotLoggedInException();
        }

        return value;
    }

}
