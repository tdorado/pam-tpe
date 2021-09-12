package com.td.wallendar.service;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.dtos.response.GroupsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GroupsService {
    @GET("/groups?user_id={user_id}")
    Call<GroupsResponse> getGroupsByUserId(@Path("user_id") final String userId);

    @GET("/groups/{groupId}")
    Call<GroupResponse> getGroupById(@Path("group_id") final String groupId);
}
