package com.td.wallendar.models

import java.io.Serializable
import java.util.*

data class Payment(
        val id: Long,
        private val from: ApplicationUser,
        val to: ApplicationUser,
        private val amount: Double,
        private val date: Date,
) : Serializable, GroupHistory {
    fun getFrom(): ApplicationUser {
        return from
    }

    override fun getGroupHistoryType(): GroupHistoryType {
        return GroupHistoryType.PAYMENT
    }

    // FIXME
    override fun getTitle(): String {
        return "Payment"
    }

    override fun getFromUser(): ApplicationUser {
        return from
    }

    override fun getToUser(): ApplicationUser {
        return to
    }

    override fun getAmount(): Double {
        return amount
    }

    override fun getDate(): Date {
        return date
    }

}