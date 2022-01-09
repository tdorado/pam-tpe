package com.td.wallendar.models

import java.io.Serializable

data class ApplicationUser(
        private var id: Long = 0,
        private var email: String,
        private var password: String,
        private var firstName: String,
        private var lastName: String,
        private var phoneNumber: String,
) : Serializable {

}