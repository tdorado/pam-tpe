package com.td.wallendar.models

import java.io.Serializable

data class Group(
        private var id: Long = 0,
        private var title: String,
        private var owner: ApplicationUser,
        private var members: MutableSet<ApplicationUser>,
        private var charges: MutableSet<Charge>,
        private var debts: MutableSet<Debt>,
        private var payments: MutableSet<Payment>,
) : Serializable