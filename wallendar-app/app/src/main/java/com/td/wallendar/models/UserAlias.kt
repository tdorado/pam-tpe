package com.td.wallendar.models

import java.io.Serializable

data class UserAlias(
        val id: Long,
        val alias: String,
) : Serializable