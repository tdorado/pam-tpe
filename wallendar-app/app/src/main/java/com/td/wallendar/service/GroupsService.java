package com.td.wallendar.service;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.dtos.response.GroupsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupsService {
    @GET("/groups/allFromUser/{userId}")
    Observable<GroupsResponse> getGroupsByUserId(@Path("userId") final Long userId);

    @GET("/groups/{groupId}")
    Observable<GroupResponse> getGroupById(@Path("groupId") final Long groupId);

    @POST("/groups/create")
    Observable<String> createGroup();

    @POST("/groups/{id}/addMembers")
    Observable<String> addMembers();

    @POST("/groups/{id}/addCharge")
    Observable<String> addCharge();

    @POST("/groups/{id}/addPayment")
    Observable<String> addPayment();
}
