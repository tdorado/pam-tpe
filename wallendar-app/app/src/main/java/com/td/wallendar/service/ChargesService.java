package com.td.wallendar.service;

import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.dtos.response.AddChargeResponse;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChargesService {

    @POST("/charges")
    Observable<AddChargeResponse> addCharge(@Body final AddChargeRequest addChargeRequest);

}
