package com.td.wallendar.utils.mappers

import com.td.wallendar.dtos.response.ApplicationUserResponse
import com.td.wallendar.models.ApplicationUser
import java.util.*

object ApplicationUserMapper {
    fun toModel(applicationUserResponse: ApplicationUserResponse): ApplicationUser {
        return ApplicationUser(
                applicationUserResponse.id,
                applicationUserResponse.email,
                "fake password", // FIXME
                applicationUserResponse.firstName,
                applicationUserResponse.lastName,
                applicationUserResponse.phoneNumber
        )
    }

    fun toModel(applicationUserResponses: MutableSet<ApplicationUserResponse>): MutableSet<ApplicationUser> {
        val applicationUsers: MutableSet<ApplicationUser> = HashSet()
        for (applicationUserResponse in applicationUserResponses) {
            applicationUsers.add(toModel(applicationUserResponse))
        }
        return applicationUsers
    }
}