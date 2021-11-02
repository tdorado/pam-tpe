package com.td.wallendar.service;


import com.td.wallendar.dtos.response.DebtResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DebtsServices {
    @GET("/users/{user_id}/total_debts")
    Observable<List<DebtResponse>> getTotalDebtsByUserId(@Path("user_id") final Long userId);
}
