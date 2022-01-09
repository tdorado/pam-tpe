package com.td.wallendar.models

import java.io.Serializable

data class Debt(
        private var id: Long,
        private var from: ApplicationUser,
        private var to: ApplicationUser,
        private var amount: Double,
        private var groupId: Long,
) : Serializable {

}