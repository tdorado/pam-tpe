package com.td.wallendar.service;

import com.td.wallendar.dtos.request.AddApplicationUserRequest;
import com.td.wallendar.dtos.request.AddUserAliasRequest;
import com.td.wallendar.dtos.response.ApplicationUserResponse;
import com.td.wallendar.dtos.response.UserAliasResponse;
import com.td.wallendar.models.UserAlias;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApplicationUsersService {

    @POST("/api/users/create")
    Single<Response<ApplicationUserResponse>> createUser(@Body AddApplicationUserRequest addApplicationUserRequest);

    @GET("/api/users/{id}")
    Single<Response<ApplicationUserResponse>> getUser(@Path("id") long userId);

    @POST("/api/user/{id}/createAlias")
    Single<Response<UserAliasResponse>> createAlias(@Path("id") long userId, @Body AddUserAliasRequest addUserAliasRequest);

    @GET("/api/users/{id}/getAliases")
    Single<Response<List<UserAlias>>> getUserAliases(@Path("id") long userId);
}
