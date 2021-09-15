package com.td.wallendar.service;


import com.td.wallendar.dtos.response.TotalDebtsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DebtsServices {
    @GET("/users/{event_id}/total_debts")
    Observable<TotalDebtsResponse> getTotalDebtsByUserId(@Path("user_id") final Long userId);
}
