package com.td.wallendar.service;

import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.dtos.response.ChargeResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChargesService {

    @POST("/api/groups/{id}/addCharge")
    Single<Response<ChargeResponse>> addCharge(@Path("id") final long groupId, @Body final AddChargeRequest addChargeRequest);

}
