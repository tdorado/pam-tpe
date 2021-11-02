package com.td.wallendar.service;


import com.td.wallendar.dtos.response.DebtResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DebtsService {
    @GET("/api/users/{userId}/getDebts")
    Single<Response<List<DebtResponse>>> getTotalDebtsByUserId(@Path("userId") final Long userId);
}