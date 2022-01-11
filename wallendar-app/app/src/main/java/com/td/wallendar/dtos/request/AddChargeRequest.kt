package com.td.wallendar.dtos.request

data class AddChargeRequest (
    val title: String ,
    val ownerId: Long ,
    val amount: Double)
