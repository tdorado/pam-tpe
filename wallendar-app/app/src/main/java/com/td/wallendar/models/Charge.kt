package com.td.wallendar.models

import java.io.Serializable
import java.util.*

data class Charge(
        val id: Long = 0,
        private val title: String,
        val owner: ApplicationUser,
        val debtors: MutableSet<ApplicationUser>,
        private val amount: Double,
        private val date: Date,
        val chargeType: ChargeType = ChargeType.EQUAL,
) : Serializable, GroupHistory {
    override fun getGroupHistoryType(): GroupHistoryType {
        return GroupHistoryType.CHARGE
    }

    override fun getTitle(): String {
        return title
    }

    override fun getFromUser(): ApplicationUser {
        return owner
    }

    // FIXME
    override fun getToUser(): ApplicationUser {
        return owner
    }

    override fun getAmount(): Double {
        return amount
    }

    override fun getDate(): Date {
        return date
    }
}