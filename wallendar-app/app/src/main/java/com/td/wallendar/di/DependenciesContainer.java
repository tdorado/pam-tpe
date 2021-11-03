package com.td.wallendar.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.service.ChargesService;
import com.td.wallendar.service.DebtsService;
import com.td.wallendar.service.GroupsService;
import com.td.wallendar.utils.login.LoginUtils;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import retrofit2.Retrofit;

public interface DependenciesContainer {
    Context getApplicationContext();

    SharedPreferences getLoginSharedPreferences();

    SchedulerProvider getSchedulerProvider();

    GroupsRepository getGroupsRepository();

    ChargesRepository getChargesRepository();

    DebtsRepository getDebtsRepository();

    Retrofit getRetrofit();

    GroupsService getGroupsService();

    ChargesService getChargesService();

    DebtsService getDebtsService();

    LoginUtils getLoginUtils();
}
