package com.td.wallendar.models

import java.io.Serializable

open class Group(
        open val id: Long,
        open val title: String,
        open val owner: ApplicationUser,
        open val members: MutableSet<ApplicationUser>,
        open val charges: MutableSet<Charge>,
        open val debts: MutableSet<Debt>,
        open val payments: MutableSet<Payment>,
) : Serializable