package com.td.wallendar.di;

import android.content.Context;

import com.td.wallendar.repositories.ChargesRepositoryImpl;
import com.td.wallendar.repositories.DebtsRepositoryImpl;
import com.td.wallendar.repositories.GroupsRepositoryImpl;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

public class DependenciesModule {
    private final Context applicationContext;

    /* default */ DependenciesModule(Context context) {
        applicationContext = context.getApplicationContext();
    }

    /* default */ Context getApplicationContext() {
        return applicationContext;
    }

    /* default */ SchedulerProvider provideScheduler() {
        return new AndroidSchedulerProvider();
    }

    public GroupsRepository provideGroupsRepository() {
        return new GroupsRepositoryImpl();
    }

    public ChargesRepository provideChargesRepository() {
        return new ChargesRepositoryImpl();
    }

    public DebtsRepository provideDebtsRepository() {
        return new DebtsRepositoryImpl();
    }
}
