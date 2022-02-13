package com.td.wallendar.dtos.response

import java.util.*

data class EventResponse(
        override val id: Long,
        override val title: String,
        override val owner: ApplicationUserResponse,
        override val members: MutableSet<ApplicationUserResponse>,
        override val charges: MutableSet<ChargeResponse>,
        override val debts: MutableSet<DebtResponse>,
        override val payments: MutableSet<PaymentResponse>,
        val date: Date
) : GroupResponse(id, title, owner, members, charges, debts, payments) {
}