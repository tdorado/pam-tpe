package com.td.wallendar.dtos.response

import java.util.*

data class EventResponse(
        val id: Long,
        val title: String,
        val owner: ApplicationUserResponse,
        val members: MutableSet<ApplicationUserResponse>,
        val charges: MutableSet<ChargeResponse>,
        val debts: MutableSet<DebtResponse>,
        val payments: MutableSet<PaymentResponse>,
        val date: Date
)