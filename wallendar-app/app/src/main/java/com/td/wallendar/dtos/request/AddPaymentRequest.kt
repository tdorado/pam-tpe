package com.td.wallendar.dtos.request

data class AddPaymentRequest(val fromUserId: Long, val toUserId: Long, val amount: Double) 