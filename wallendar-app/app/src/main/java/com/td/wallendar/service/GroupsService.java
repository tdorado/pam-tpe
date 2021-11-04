package com.td.wallendar.service;

import com.td.wallendar.dtos.request.AddGroupRequest;
import com.td.wallendar.dtos.request.AddMemberByEmailRequest;
import com.td.wallendar.dtos.request.AddMembersRequest;
import com.td.wallendar.dtos.request.AddPaymentRequest;
import com.td.wallendar.dtos.response.GroupResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupsService {
    @GET("/api/groups/allFromUser/{userId}")
    Single<Response<List<GroupResponse>>> getGroupsByUserId(@Path("userId") final long userId);

    @GET("/api/groups/{groupId}")
    Single<Response<GroupResponse>> getGroupById(@Path("groupId") final long groupId);

    @POST("/api/groups/create")
    Single<Response<GroupResponse>> createGroup(@Body AddGroupRequest addGroupRequest);

    @POST("/api/groups/{groupId}/addMemberByEmail")
    Single<Response<GroupResponse>> addMemberByEmail(@Path("groupId") long groupId, @Body AddMemberByEmailRequest addMemberByEmailRequest);

    @POST("/api/groups/{groupId}/addMembers")
    Single<Response<GroupResponse>> addMembers(@Path("groupId") final long groupId, @Body AddMembersRequest addMembersRequest);

    @POST("/api/groups/{groupId}/addPayment")
    Single<Response<GroupResponse>> addPayment(@Path("groupId") final long groupId, @Body AddPaymentRequest addPaymentRequest);
}
