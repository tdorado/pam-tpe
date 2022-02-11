package com.td.wallendar.models

import java.io.Serializable

data class Group(
        val id: Long,
        val title: String,
        val owner: ApplicationUser,
        val members: MutableSet<ApplicationUser>,
        val charges: MutableSet<Charge>,
        val debts: MutableSet<Debt>,
        val payments: MutableSet<Payment>,
) : Serializable