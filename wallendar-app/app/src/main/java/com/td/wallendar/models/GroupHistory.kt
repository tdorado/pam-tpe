package com.td.wallendar.models

import java.util.*

interface GroupHistory {
    fun getGroupHistoryType(): GroupHistoryType
    fun getTitle(): String
    fun getFromUser(): ApplicationUser
    fun getToUser(): ApplicationUser
    fun getAmount(): Double
    fun getDate(): Date
}