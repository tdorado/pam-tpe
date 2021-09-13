package com.td.wallendar;

import android.app.Application;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceModule.init();
    }

}
