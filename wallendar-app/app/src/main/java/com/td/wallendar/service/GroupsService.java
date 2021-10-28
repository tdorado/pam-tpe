package com.td.wallendar.service;

import com.td.wallendar.dtos.response.GroupResponse;
import com.td.wallendar.dtos.response.GroupsResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupsService {
    @GET("/api/groups/allFromUser/{userId}")
    Observable<List<GroupResponse>> getGroupsByUserId(@Path("userId") final Long userId);

    @GET("/api/groups/{groupId}")
    Observable<GroupResponse> getGroupById(@Path("groupId") final Long groupId);

    @POST("/api/groups/create")
    Observable<String> createGroup();

    @POST("/api/groups/{id}/addMembers")
    Observable<String> addMembers();

    @POST("/api/groups/{id}/addCharge")
    Observable<String> addCharge();

    @POST("/api/groups/{id}/addPayment")
    Observable<String> addPayment();
}
