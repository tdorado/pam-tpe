package com.td.wallendar.service

import com.td.wallendar.dtos.request.AddGroupRequest
import com.td.wallendar.dtos.request.AddMemberByEmailRequest
import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.dtos.response.GroupResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupsService {
    @GET("/api/groups/allFromUser/{userId}")
    fun getGroupsByUserId(@Path("userId") userId: Long): Single<Response<MutableList<GroupResponse>>>
    @GET("/api/groups/{groupId}")
    fun getGroupById(@Path("groupId") groupId: Long): Single<Response<GroupResponse>>
    @POST("/api/groups/create")
    fun createGroup(@Body addGroupRequest: AddGroupRequest): Single<Response<GroupResponse>>
    @POST("/api/groups/{groupId}/addMemberByEmail")
    fun addMemberByEmail(@Path("groupId") groupId: Long, @Body addMemberByEmailRequest: AddMemberByEmailRequest): Single<Response<GroupResponse>>
    @POST("/api/groups/{groupId}/addMembers")
    fun addMembers(@Path("groupId") groupId: Long, @Body addMembersRequest: AddMembersRequest): Single<Response<Void>>
    @POST("/api/groups/{groupId}/addPayment")
    fun addPayment(@Path("groupId") groupId: Long, @Body addPaymentRequest: AddPaymentRequest): Single<Response<Void>>
}