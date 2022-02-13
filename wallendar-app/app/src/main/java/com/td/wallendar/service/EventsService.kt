package com.td.wallendar.service

import com.td.wallendar.dtos.request.AddEventRequest
import com.td.wallendar.dtos.request.AddMemberByEmailRequest
import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.dtos.response.EventResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsService {
    @GET("/api/events/allFromUser/{userId}")
    fun getEventsByUserId(@Path("userId") userId: Long): Single<Response<MutableList<EventResponse>>>
    @GET("/api/events/{groupId}")
    fun getEventById(@Path("groupId") groupId: Long): Single<Response<EventResponse>>
    @POST("/api/events/create")
    fun createEvent(@Body addEventRequest: AddEventRequest): Single<Response<EventResponse>>
    @POST("/api/events/{eventId}/addMemberByEmail")
    fun addMemberByEmail(@Path("eventId") groupId: Long, @Body addMemberByEmailRequest: AddMemberByEmailRequest): Single<Response<EventResponse>>
    @POST("/api/events/{eventId}/addMembers")
    fun addMembers(@Path("eventId") groupId: Long, @Body addMembersRequest: AddMembersRequest): Single<Response<Void>>
    @POST("/api/events/{eventId}/addPayment")
    fun addPayment(@Path("eventId") groupId: Long, @Body addPaymentRequest: AddPaymentRequest): Single<Response<Void>>
}