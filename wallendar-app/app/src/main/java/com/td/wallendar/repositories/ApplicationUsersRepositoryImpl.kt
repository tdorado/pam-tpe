package com.td.wallendar.repositories

import com.td.wallendar.dtos.request.AddApplicationUserRequest
import com.td.wallendar.dtos.request.AddUserAliasRequest
import com.td.wallendar.dtos.request.LoginRequest
import com.td.wallendar.dtos.response.LoginResponse
import com.td.wallendar.models.ApplicationUser
import com.td.wallendar.models.UserAlias
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository
import com.td.wallendar.service.ApplicationUsersService
import com.td.wallendar.utils.mappers.ApplicationUserMapper
import com.td.wallendar.utils.mappers.UserAliasMapper
import com.td.wallendar.utils.networking.RetrofitUtils
import io.reactivex.Single

class ApplicationUsersRepositoryImpl(private val applicationUsersService: ApplicationUsersService) : ApplicationUsersRepository {
    override fun authenticateUser(loginRequest: LoginRequest): Single<LoginResponse> {
        return RetrofitUtils.performRequest(applicationUsersService.authenticateUser(loginRequest))
    }

    override fun createUser(addApplicationUserRequest: AddApplicationUserRequest): Single<ApplicationUser> {
        return RetrofitUtils.performRequest(applicationUsersService.createUser(addApplicationUserRequest)).map { ApplicationUserMapper.toModel(it) }
    }

    override fun getUser(userId: Long): Single<ApplicationUser> {
        return RetrofitUtils.performRequest(applicationUsersService.getUser(userId)).map { ApplicationUserMapper.toModel(it) }
    }

    override fun getUserByEmail(email: String): Single<ApplicationUser> {
        return RetrofitUtils.performRequest(applicationUsersService.getUserByEmail(email))
                .map { ApplicationUserMapper.toModel(it) }
    }

    override fun createAlias(userId: Long, addUserAliasRequest: AddUserAliasRequest): Single<UserAlias> {
        return RetrofitUtils.performRequest(applicationUsersService.createAlias(userId, addUserAliasRequest)).map { UserAliasMapper.toModel(it) }
    }

    override fun getUserAliases(userId: Long): Single<MutableList<UserAlias>> {
        return RetrofitUtils.performRequest(applicationUsersService.getUserAliases(userId)).map { UserAliasMapper.toModel(it) }
    }
}