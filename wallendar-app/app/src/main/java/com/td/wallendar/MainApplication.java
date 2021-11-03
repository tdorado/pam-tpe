package com.td.wallendar;

import android.app.Application;

import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.utils.login.LoginUtils;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //TODO hacer el seteo de esto con un login, ahora lo dejo fijo
        final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
        final LoginUtils loginUtils = dependenciesContainer.getLoginUtils();
        loginUtils.setLoggedUserId(1L);
    }

}
