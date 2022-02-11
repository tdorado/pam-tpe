package com.td.wallendar.dtos.response

import java.util.*

data class PaymentResponse(
        val id: Long,
        val from: ApplicationUserResponse,
        val to: ApplicationUserResponse,
        val amount: Double,
        val date: Date,
        val groupId: Long,
)