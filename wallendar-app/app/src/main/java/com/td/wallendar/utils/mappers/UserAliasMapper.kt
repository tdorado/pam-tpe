package com.td.wallendar.utils.mappers

import com.td.wallendar.dtos.response.UserAliasResponse
import com.td.wallendar.models.UserAlias
import java.util.*

object UserAliasMapper {
    fun toModel(userAliasResponse: UserAliasResponse): UserAlias{
        return UserAlias(userAliasResponse.getId(), userAliasResponse.getAlias())
    }

    fun toModel(userAliasResponses: MutableList<UserAliasResponse>): MutableList<UserAlias>{
        val userAliases: MutableList<UserAlias> = ArrayList()
        for (userAliasResponse in userAliasResponses) {
            userAliases.add(toModel(userAliasResponse))
        }
        return userAliases
    }
}