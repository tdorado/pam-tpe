package com.td.wallendar.dtos.request

data class AddApplicationUserRequest(
        val email: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val phoneNumber: String,
)
