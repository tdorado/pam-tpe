package com.td.wallendar.utils.mappers

import com.td.wallendar.dtos.response.DebtResponse
import com.td.wallendar.models.Debt
import java.util.*

object DebtMapper {
    fun toModel(debtResponse: DebtResponse): Debt {
        return Debt(
                debtResponse.id,
                ApplicationUserMapper.toModel(debtResponse.from),
                ApplicationUserMapper.toModel(debtResponse.to),
                debtResponse.amount,
                debtResponse.groupId
        )
    }

    fun toModel(debtResponses: MutableSet<DebtResponse>): MutableSet<Debt> {
        val debts: MutableSet<Debt> = HashSet()
        for (debtResponse in debtResponses) {
            debts.add(toModel(debtResponse))
        }
        return debts
    }

    fun toModel(debtResponses: MutableList<DebtResponse>): MutableList<Debt> {
        val debts: MutableList<Debt> = ArrayList()
        for (debtResponse in debtResponses) {
            debts.add(toModel(debtResponse))
        }
        return debts
    }
}