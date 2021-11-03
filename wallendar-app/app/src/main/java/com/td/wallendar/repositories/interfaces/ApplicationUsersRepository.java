package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.dtos.request.AddApplicationUserRequest;
import com.td.wallendar.dtos.request.AddUserAliasRequest;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.UserAlias;

import java.util.List;

import io.reactivex.Single;

public interface ApplicationUsersRepository {

    Single<ApplicationUser> createUser(AddApplicationUserRequest addApplicationUserRequest);

    Single<ApplicationUser> getUser(long userId);

    Single<UserAlias> createAlias(long userId, AddUserAliasRequest addUserAliasRequest);

    Single<List<UserAlias>> getUserAliases(long userId);
}
