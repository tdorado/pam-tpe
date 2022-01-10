package com.td.wallendar.dtos.response

data class DebtResponse(
        val id: Long,
        val from: ApplicationUserResponse,
        val to: ApplicationUserResponse,
        val amount: Double,
        val groupId: Long,
)