package com.td.wallendar.models

import java.util.*

interface GroupHistory {
    open fun getGroupHistoryType(): GroupHistoryType
    open fun getTitle(): String
    open fun getFromUser(): ApplicationUser
    open fun getToUser(): ApplicationUser
    open fun getAmount(): Double
    open fun getDate(): Date
}