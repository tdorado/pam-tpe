package com.td.wallendar.dtos.response

import com.td.wallendar.models.UserAlias

class UserAliasResponse(userAlias: UserAlias) {
    private var id: Long = 0
    private var alias: String

    init {
        id = userAlias.id
        alias = userAlias.alias
    }

    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun getAlias(): String{
        return alias
    }

    fun setAlias(alias: String) {
        this.alias = alias
    }
}