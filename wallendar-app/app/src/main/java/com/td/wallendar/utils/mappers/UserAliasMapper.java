package com.td.wallendar.utils.mappers;

import com.td.wallendar.dtos.response.UserAliasResponse;
import com.td.wallendar.models.UserAlias;

import java.util.ArrayList;
import java.util.List;

public class UserAliasMapper {

    public static UserAlias toModel(UserAliasResponse userAliasResponse) {
        return new UserAlias(userAliasResponse.getId(), userAliasResponse.getAlias());
    }

    public static List<UserAlias> toModel(List<UserAliasResponse> userAliasResponses) {
        List<UserAlias> userAliases = new ArrayList<>();
        for (UserAliasResponse userAliasResponse : userAliasResponses) {
            userAliases.add(toModel(userAliasResponse));
        }
        return userAliases;
    }
}
