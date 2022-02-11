package com.td.wallendar.models

import java.io.Serializable

data class ApplicationUser(
        val id: Long = 0,
        val email: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val phoneNumber: String,
) : Serializable {

}