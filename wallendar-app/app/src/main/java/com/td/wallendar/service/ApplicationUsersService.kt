package com.td.wallendar.service

import com.td.wallendar.dtos.request.AddApplicationUserRequest
import com.td.wallendar.dtos.request.AddUserAliasRequest
import com.td.wallendar.dtos.request.LoginRequest
import com.td.wallendar.dtos.response.ApplicationUserResponse
import com.td.wallendar.dtos.response.LoginResponse
import com.td.wallendar.dtos.response.UserAliasResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ApplicationUsersService {
    @POST("/api/authenticate")
    fun authenticateUser(@Body loginRequest: LoginRequest): Single<Response<LoginResponse>>
    @POST("/api/users/create")
    fun createUser(@Body addApplicationUserRequest: AddApplicationUserRequest): Single<Response<ApplicationUserResponse>>
    @GET("/api/users/{id}")
    fun getUser(@Path("id") userId: Long): Single<Response<ApplicationUserResponse>>
    @GET("/api/users/findByEmail")
    fun getUserByEmail(@Query("email") email: String): Single<Response<ApplicationUserResponse>>
    @POST("/api/user/{id}/createAlias")
    fun createAlias(@Path("id") userId: Long, @Body addUserAliasRequest: AddUserAliasRequest): Single<Response<UserAliasResponse>>
    @GET("/api/users/{id}/getAliases")
    fun getUserAliases(@Path("id") userId: Long): Single<Response<MutableList<UserAliasResponse>>>
}