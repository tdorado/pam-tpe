package com.td.wallendar;

import android.app.Application;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO hacer el seteo de esto con un login, ahora lo dejo fijo
        ApplicationUserModule.setLoggedUserId(getApplicationContext(), 1);
        ServiceModule.init();
    }

}
