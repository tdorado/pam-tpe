package com.td.wallendar.repositories.interfaces

import com.td.wallendar.models.Debt
import com.td.wallendar.models.DebtDetail
import io.reactivex.Single

interface DebtsRepository {
    fun getTotalDebtsByUserId(userId: Long): Single<MutableList<Debt>>
    fun getDebtDetail(debtId: Long): Single<MutableList<DebtDetail>>
}