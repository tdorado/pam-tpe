package com.td.wallendar.dtos.response

open class GroupResponse(
        open val id: Long,
        open val title: String,
        open val owner: ApplicationUserResponse,
        open val members: MutableSet<ApplicationUserResponse>,
        open val charges: MutableSet<ChargeResponse>,
        open val debts: MutableSet<DebtResponse>,
        open val payments: MutableSet<PaymentResponse>,
)