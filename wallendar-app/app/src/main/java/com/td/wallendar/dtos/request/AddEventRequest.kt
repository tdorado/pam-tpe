package com.td.wallendar.dtos.request

import java.util.*

data class AddEventRequest(
        override val title: String,
        override val ownerId: Long,
        val date: Date,
) : AddGroupRequest(title, ownerId)