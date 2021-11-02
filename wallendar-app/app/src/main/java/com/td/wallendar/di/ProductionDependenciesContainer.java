package com.td.wallendar.di;

import android.content.Context;

import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

public class ProductionDependenciesContainer implements DependenciesContainer {

    private final DependenciesModule dependenciesModule;

    private SchedulerProvider schedulerProvider;
    private GroupsRepository groupsRepository;
    private ChargesRepository chargesRepository;
    private DebtsRepository debtsRepository;

    public ProductionDependenciesContainer(final Context context) {
        dependenciesModule = new DependenciesModule(context);
    }


    @Override
    public Context getApplicationContext() {
        return dependenciesModule.getApplicationContext();
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
            groupsRepository = dependenciesModule.provideGroupsRepository();
        }
        return groupsRepository;
    }

    @Override
    public ChargesRepository getChargesRepository() {
        if (chargesRepository == null) {
            chargesRepository = dependenciesModule.provideChargesRepository();
        }
        return chargesRepository;
    }

    @Override
    public DebtsRepository getDebtsRepository() {
        if (debtsRepository == null) {
            debtsRepository = dependenciesModule.provideDebtsRepository();
        }
        return debtsRepository;
    }
}
