package com.td.wallendar.dtos.request

data class AddEventRequest(
        val title: String,
        val ownerId: Long,
        val date: String,
)