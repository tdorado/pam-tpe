package com.td.wallendar.models

import java.io.Serializable
import java.util.*

data class Event(
        override val id: Long,
        override val title: String,
        override val owner: ApplicationUser,
        override val members: MutableSet<ApplicationUser>,
        override val charges: MutableSet<Charge>,
        override val debts: MutableSet<Debt>,
        override val payments: MutableSet<Payment>,
        val date: Date,
) : Serializable, Group(id, title, owner, members, charges, debts, payments)