package com.td.wallendar.di;

import static com.td.wallendar.AbstractActivity.LOGIN_SHARED_PREFERENCES;

import android.content.Context;
import android.content.SharedPreferences;

import com.td.wallendar.repositories.ApplicationUsersRepositoryImpl;
import com.td.wallendar.repositories.ChargesRepositoryImpl;
import com.td.wallendar.repositories.DebtsRepositoryImpl;
import com.td.wallendar.repositories.GroupsRepositoryImpl;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.service.ApplicationUsersService;
import com.td.wallendar.service.ChargesService;
import com.td.wallendar.service.DebtsService;
import com.td.wallendar.service.GroupsService;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

    /* default */ SharedPreferences provideLoginSharedPreferences() {
        return applicationContext.getSharedPreferences(LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    /* default */ Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public GroupsService provideGroupsService(Retrofit retrofit) {
        return retrofit.create(GroupsService.class);
    }

    public DebtsService provideDebtsService(Retrofit retrofit) {
        return retrofit.create(DebtsService.class);
    }

    public ChargesService provideChargesService(Retrofit retrofit) {
        return retrofit.create(ChargesService.class);
    }

    public ApplicationUsersService provideApplicationUsersService(Retrofit retrofit) {
        return retrofit.create(ApplicationUsersService.class);
    }

    public GroupsRepository provideGroupsRepository(GroupsService groupsService) {
        return new GroupsRepositoryImpl(groupsService);
    }

    public ChargesRepository provideChargesRepository(ChargesService chargesService) {
        return new ChargesRepositoryImpl(chargesService);
    }

    public DebtsRepository provideDebtsRepository(DebtsService debtsService) {
        return new DebtsRepositoryImpl(debtsService);
    }

    public ApplicationUsersRepository provideApplicationUsersRepository(ApplicationUsersService applicationUsersService) {
        return new ApplicationUsersRepositoryImpl(applicationUsersService);
    }

}
