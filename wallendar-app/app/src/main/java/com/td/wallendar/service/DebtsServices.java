package com.td.wallendar.service;


import com.td.wallendar.dtos.response.DebtResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DebtsServices {
    @GET("/api/users/{userId}/getDebts")
    Observable<List<DebtResponse>> getTotalDebtsByUserId(@Path("userId") final Long userId);
}
