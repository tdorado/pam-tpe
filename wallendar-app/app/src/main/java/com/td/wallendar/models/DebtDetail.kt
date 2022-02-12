package com.td.wallendar.models

import java.util.*

data class DebtDetail(
        val amount: Double,
        val date: Date,
        val description: String,
)