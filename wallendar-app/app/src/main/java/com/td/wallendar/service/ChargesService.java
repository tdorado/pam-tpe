package com.td.wallendar.service;

import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.dtos.response.AddChargeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChargesService {

    @POST("/charges")
    Call<AddChargeResponse> addCharge(@Body final AddChargeRequest addChargeRequest);

}
