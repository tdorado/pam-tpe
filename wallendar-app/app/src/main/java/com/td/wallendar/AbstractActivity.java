package com.td.wallendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.login.ui.LoginActivity;

public abstract class AbstractActivity extends AppCompatActivity {
    public final static String LOGIN_SHARED_PREFERENCES = "LOGIN_SHARED_PREFERENCES";
    private final static String LOGGED_USER_ID = "LOGGED_USER_ID";

    private final static long loggedOutValue = -1;

    private SharedPreferences loginSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
        loginSharedPreferences = dependenciesContainer.getLoginSharedPreferences();
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

        //cuando deslogea llevame al login
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public long getLoggedUserId() {
        long value = loginSharedPreferences.getLong(LOGGED_USER_ID, loggedOutValue);

        if(value == loggedOutValue){
            //si no esta logeado, llevame al login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return value;
    }
}
