package com.td.wallendar.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.service.ChargesService;
import com.td.wallendar.service.DebtsService;
import com.td.wallendar.service.GroupsService;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import retrofit2.Retrofit;

public class ProductionDependenciesContainer implements DependenciesContainer {

    private final DependenciesModule dependenciesModule;

    private SchedulerProvider schedulerProvider;
    private SharedPreferences loginSharedPreferences;
    private Retrofit retrofit;
    private GroupsRepository groupsRepository;
    private ChargesRepository chargesRepository;
    private DebtsRepository debtsRepository;
    private GroupsService groupsService;
    private ChargesService chargesService;
    private DebtsService debtsService;

    public ProductionDependenciesContainer(final Context context) {
        dependenciesModule = new DependenciesModule(context);
    }


    @Override
    public Context getApplicationContext() {
        return dependenciesModule.getApplicationContext();
    }

    @Override
    public SharedPreferences getLoginSharedPreferences() {
        if (loginSharedPreferences == null) {
            loginSharedPreferences = dependenciesModule.provideLoginSharedPreferences();
        }
        return loginSharedPreferences;
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        if (schedulerProvider == null) {
            schedulerProvider = dependenciesModule.provideScheduler();
        }
        return schedulerProvider;
    }

    @Override
    public GroupsRepository getGroupsRepository() {
        if (groupsRepository == null) {
            groupsRepository = dependenciesModule.provideGroupsRepository(getGroupsService());
        }
        return groupsRepository;
    }

    @Override
    public ChargesRepository getChargesRepository() {
        if (chargesRepository == null) {
            chargesRepository = dependenciesModule.provideChargesRepository(getChargesService());
        }
        return chargesRepository;
    }

    @Override
    public DebtsRepository getDebtsRepository() {
        if (debtsRepository == null) {
            debtsRepository = dependenciesModule.provideDebtsRepository(getDebtsService());
        }
        return debtsRepository;
    }

    @Override
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = dependenciesModule.provideRetrofit();
        }
        return retrofit;
    }

    @Override
    public GroupsService getGroupsService() {
        if (groupsService == null) {
            groupsService = dependenciesModule.provideGroupsService(getRetrofit());
        }
        return groupsService;
    }

    @Override
    public ChargesService getChargesService() {
        if (chargesService == null) {
            chargesService = dependenciesModule.provideChargesService(getRetrofit());
        }
        return chargesService;
    }

    @Override
    public DebtsService getDebtsService() {
        if (debtsService == null) {
            debtsService = dependenciesModule.provideDebtsService(getRetrofit());
        }
        return debtsService;
    }
}
