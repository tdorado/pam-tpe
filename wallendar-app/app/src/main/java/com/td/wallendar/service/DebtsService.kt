package com.td.wallendar.service

import com.td.wallendar.dtos.response.DebtResponse
import com.td.wallendar.models.DebtDetail
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DebtsService {
    @GET("/api/users/{userId}/getDebts")
    fun getTotalDebtsByUserId(@Path("userId") userId: Long): Single<Response<MutableList<DebtResponse>>>

    @GET("/api/debts/{debtId}/details")
    fun getDebtDetail(@Path("debtId") debtId: Long): Single<Response<MutableList<DebtDetail>>>
}