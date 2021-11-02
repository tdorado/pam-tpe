package com.td.wallendar.service;

import com.td.wallendar.dtos.request.AddGroupRequest;
import com.td.wallendar.dtos.response.GroupResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupsService {
    @GET("/api/groups/allFromUser/{userId}")
    Observable<List<GroupResponse>> getGroupsByUserId(@Path("userId") final Long userId);

    @GET("/api/groups/{groupId}")
    Observable<GroupResponse> getGroupById(@Path("groupId") final Long groupId);

    @POST("/api/groups/create")
    Observable<GroupResponse> createGroup(@Body AddGroupRequest addGroupRequest);

    @POST("/api/groups/{groupId}/addMembers")
    Observable<GroupResponse> addMembers(@Path("groupId") final Long groupId);

    @POST("/api/groups/{groupId}/addPayment")
    Observable<GroupResponse> addPayment(@Path("groupId") final Long groupId);
}
