package com.td.wallendar.service;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.dtos.response.GroupsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupsService {
    @GET("/groups")
    Call<GroupsResponse> getGroupsByUserId(@Query("user_id") final Long userId);

    @GET("/groups/{groupId}")
    Call<GroupResponse> getGroupById(@Path("group_id") final String groupId);
}
