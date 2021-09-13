package com.td.wallendar.service;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.dtos.response.GroupsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupsService {
    @GET("/groups")
    Observable<GroupsResponse> getGroupsByUserId(@Query("user_id") final String userId);

    @GET("/hello")
    Observable<String> test();

    @GET("/groups/{groupId}")
    Observable<GroupResponse> getGroupById(@Path("group_id") final String groupId);
}
