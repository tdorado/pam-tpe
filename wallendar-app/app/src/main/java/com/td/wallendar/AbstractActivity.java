package com.td.wallendar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.utils.login.LoginUtils;
import com.td.wallendar.utils.login.UserNotLoggedInException;

public abstract class AbstractActivity extends AppCompatActivity {
    private LoginUtils loginUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
        loginUtils = dependenciesContainer.getLoginUtils();
    }

    public LoginUtils getLoginUtils() {
        return loginUtils;
    }

    public long getLoggedUserId() {
        try {
            return getLoginUtils().getLoggedUserId();
        } catch (UserNotLoggedInException e) {
            //TODO si no esta logeado, llevame al login
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
        }
        return -1;
    }
}
