package com.td.wallendar.models

import java.io.Serializable

data class UserAlias(
        private var id: Long,
        private var alias: String,
) : Serializable 