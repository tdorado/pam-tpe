package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.UserResponse;
import com.td.wallendar.models.User;

public class UserMapper {
    public static User toModel(UserResponse userResponse){
        User user = new User();
        user.setEmail(userResponse.getEmail());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setCellphone(userResponse.getCellphone());

        return user;
    }
}
