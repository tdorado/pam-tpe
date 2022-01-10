package com.td.wallendar.repositories.interfaces

import com.td.wallendar.dtos.request.AddApplicationUserRequest
import com.td.wallendar.dtos.request.AddUserAliasRequest
import com.td.wallendar.dtos.request.LoginRequest
import com.td.wallendar.dtos.response.LoginResponse
import com.td.wallendar.models.ApplicationUser
import com.td.wallendar.models.UserAlias
import io.reactivex.Single

interface ApplicationUsersRepository {
    fun authenticateUser(loginRequest: LoginRequest): Single<LoginResponse>
    fun createUser(addApplicationUserRequest: AddApplicationUserRequest): Single<ApplicationUser>
    fun getUser(userId: Long): Single<ApplicationUser>
    fun getUserByEmail(email: String): Single<ApplicationUser>
    fun createAlias(userId: Long, addUserAliasRequest: AddUserAliasRequest): Single<UserAlias>
    fun getUserAliases(userId: Long): Single<MutableList<UserAlias>>
}