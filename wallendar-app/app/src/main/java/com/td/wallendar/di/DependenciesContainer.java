package com.td.wallendar.di;

import android.content.Context;

import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

public interface DependenciesContainer {
    Context getApplicationContext();

    SchedulerProvider getSchedulerProvider();

    GroupsRepository getGroupsRepository();

    ChargesRepository getChargesRepository();

    DebtsRepository getDebtsRepository();
}
