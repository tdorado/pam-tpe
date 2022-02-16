package com.td.wallendar.service

import com.td.wallendar.dtos.request.AddChargeRequest
import com.td.wallendar.dtos.response.ChargeResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ChargesService {
    @POST("/api/groups/{id}/addCharge")
    fun addCharge(@Path("id") groupId: Long, @Body addChargeRequest: AddChargeRequest): Single<Response<ChargeResponse>>
    @POST("/api/events/{id}/addCharge")
    fun addChargeToEvent(@Path("id") groupId: Long, @Body addChargeRequest: AddChargeRequest): Single<Response<ChargeResponse>>
}