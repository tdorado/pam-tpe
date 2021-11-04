package com.td.wallendar.repositories;

import com.td.wallendar.dtos.request.AddApplicationUserRequest;
import com.td.wallendar.dtos.request.AddUserAliasRequest;
import com.td.wallendar.dtos.request.FindApplicationUserByEmailRequest;
import com.td.wallendar.dtos.request.LoginRequest;
import com.td.wallendar.dtos.response.LoginResponse;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.UserAlias;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.service.ApplicationUsersService;
import com.td.wallendar.utils.mappers.ApplicationUserMapper;
import com.td.wallendar.utils.mappers.UserAliasMapper;
import com.td.wallendar.utils.networking.RetrofitUtils;

import java.util.List;

import io.reactivex.Single;

public class ApplicationUsersRepositoryImpl implements ApplicationUsersRepository {

    private final ApplicationUsersService applicationUsersService;

    public ApplicationUsersRepositoryImpl(ApplicationUsersService applicationUsersService) {
        this.applicationUsersService = applicationUsersService;
    }

    @Override
    public Single<LoginResponse> authenticateUser(LoginRequest loginRequest) {
        return RetrofitUtils.performRequest(applicationUsersService.authenticateUser(loginRequest));
    }

    @Override
    public Single<ApplicationUser> createUser(AddApplicationUserRequest addApplicationUserRequest) {
        return RetrofitUtils.performRequest(applicationUsersService.createUser(addApplicationUserRequest)).map(ApplicationUserMapper::toModel);
    }

    @Override
    public Single<ApplicationUser> getUser(long userId) {
        return RetrofitUtils.performRequest(applicationUsersService.getUser(userId)).map(ApplicationUserMapper::toModel);
    }

    @Override
    public Single<ApplicationUser> getUserByEmail(FindApplicationUserByEmailRequest findApplicationUserByEmailRequest) {
        return RetrofitUtils.performRequest(applicationUsersService.getUserByEmail(findApplicationUserByEmailRequest))
                .map(ApplicationUserMapper::toModel);
    }

    @Override
    public Single<UserAlias> createAlias(long userId, AddUserAliasRequest addUserAliasRequest) {
        return RetrofitUtils.performRequest(applicationUsersService.createAlias(userId, addUserAliasRequest)).map(UserAliasMapper::toModel);
    }

    @Override
    public Single<List<UserAlias>> getUserAliases(long userId) {
        return RetrofitUtils.performRequest(applicationUsersService.getUserAliases(userId)).map(UserAliasMapper::toModel);
    }
}
