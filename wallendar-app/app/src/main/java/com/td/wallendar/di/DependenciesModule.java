package com.td.wallendar.di;

import android.content.Context;

import com.td.wallendar.repositories.ChargesRepositoryImpl;
import com.td.wallendar.repositories.DebtsRepositoryImpl;
import com.td.wallendar.repositories.GroupsRepositoryImpl;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.service.ChargesService;
import com.td.wallendar.service.DebtsService;
import com.td.wallendar.service.GroupsService;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import retrofit2.Retrofit;

public class DependenciesModule {
    private final Context applicationContext;
    private final Retrofit retrofit = ServiceModule.getRetrofit();

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
        return new GroupsRepositoryImpl(retrofit.create(GroupsService.class));
    }

    public ChargesRepository provideChargesRepository() {
        return new ChargesRepositoryImpl(retrofit.create(ChargesService.class));
    }

    public DebtsRepository provideDebtsRepository() {
        return new DebtsRepositoryImpl(retrofit.create(DebtsService.class));
    }
}
