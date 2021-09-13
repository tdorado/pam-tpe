package com.td.wallendar;

import com.td.wallendar.service.GroupsService;

import retrofit2.Retrofit;

public class ServiceModule {
    private static GroupsService groupsService;

    public static void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .build();

        groupsService = retrofit.create(GroupsService.class);
    }

    public static GroupsService getGroupsService() {
        return groupsService;
    }
}
