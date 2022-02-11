package com.td.wallendar.models

import java.io.Serializable

data class Debt(
        val id: Long,
        val from: ApplicationUser,
        val to: ApplicationUser,
        val amount: Double,
        val groupId: Long,
) : Serializable {

}