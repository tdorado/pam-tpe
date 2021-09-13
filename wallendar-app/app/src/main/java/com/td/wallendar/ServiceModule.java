package com.td.wallendar;

import com.td.wallendar.service.GroupsService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceModule {
    private GroupsService groupsService;

    public Retrofit init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
//        groupsService = retrofit.create(GroupsService.class);
    }

    public GroupsService getGroupsService() {
        return groupsService;
    }
}
