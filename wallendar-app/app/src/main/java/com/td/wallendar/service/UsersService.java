package com.td.wallendar.service;

import com.td.wallendar.dtos.request.PayDebtRequest;
import com.td.wallendar.dtos.response.PayDebtResponse;
import com.td.wallendar.dtos.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsersService {

    @GET("/users/{user_email}")
    Call<UserResponse> getUserByEmail(@Path("user_email") final String userEmail);

    @POST("/user/debts")
    Call<PayDebtResponse> payDebt(@Body final PayDebtRequest payDebtRequest);
}
