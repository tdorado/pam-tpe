package com.td.wallendar.service;

import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.dtos.response.ChargeResponse;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChargesService {

    @POST("/api/groups/{id}/addCharge")
    Observable<ChargeResponse> addCharge(@Path("id") final long groupId, @Body final AddChargeRequest addChargeRequest);

}
