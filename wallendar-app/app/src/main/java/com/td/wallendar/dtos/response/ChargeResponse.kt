package com.td.wallendar.dtos.response

import java.util.*

class ChargeResponse(
        val id: Long,
        val title: String,
        val owner: ApplicationUserResponse,
        val debtors: MutableSet<ApplicationUserResponse>,
        val amount: Double,
        val date: Date,
)
