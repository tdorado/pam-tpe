package com.td.wallendar.repositories

import com.td.wallendar.models.Debt
import com.td.wallendar.repositories.interfaces.DebtsRepository
import com.td.wallendar.service.DebtsService
import com.td.wallendar.utils.mappers.DebtMapper
import com.td.wallendar.utils.networking.RetrofitUtils
import io.reactivex.Single

class DebtsRepositoryImpl(private val debtsService: DebtsService) : DebtsRepository {
    override fun getTotalDebtsByUserId(userId: Long): Single<MutableList<Debt>> {
        return RetrofitUtils
                .performRequest(debtsService.getTotalDebtsByUserId(userId))
                .map { DebtMapper.toModel(it) }
    }
}