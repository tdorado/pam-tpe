package com.td.wallendar.models

import java.io.Serializable
import java.util.*

data class Charge(
        private var id: Long = 0,
        private var title: String,
        private var owner: ApplicationUser,
        private var debtors: MutableSet<ApplicationUser>,
        private var amount: Double,
        private var date: Date,
        private val chargeType: ChargeType = ChargeType.EQUAL,
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