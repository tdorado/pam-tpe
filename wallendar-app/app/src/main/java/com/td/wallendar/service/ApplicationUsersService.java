package com.td.wallendar.service;

import com.td.wallendar.dtos.request.AddApplicationUserRequest;
import com.td.wallendar.dtos.request.AddUserAliasRequest;
import com.td.wallendar.dtos.request.LoginRequest;
import com.td.wallendar.dtos.response.ApplicationUserResponse;
import com.td.wallendar.dtos.response.LoginResponse;
import com.td.wallendar.dtos.response.UserAliasResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApplicationUsersService {

    @POST("/api/authenticate")
    Single<Response<LoginResponse>> authenticateUser(@Body LoginRequest loginRequest);

    @POST("/api/users/create")
    Single<Response<ApplicationUserResponse>> createUser(@Body AddApplicationUserRequest addApplicationUserRequest);

    @GET("/api/users/{id}")
    Single<Response<ApplicationUserResponse>> getUser(@Path("id") long userId);

    @GET("/api/users/findByEmail")
    Single<Response<ApplicationUserResponse>> getUserByEmail(@Query("email") String email);

    @POST("/api/user/{id}/createAlias")
    Single<Response<UserAliasResponse>> createAlias(@Path("id") long userId, @Body AddUserAliasRequest addUserAliasRequest);

    @GET("/api/users/{id}/getAliases")
    Single<Response<List<UserAliasResponse>>> getUserAliases(@Path("id") long userId);
}
