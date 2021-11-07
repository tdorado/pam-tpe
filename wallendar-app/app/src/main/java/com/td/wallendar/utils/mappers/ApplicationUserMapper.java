package com.td.wallendar.utils.mappers;

import com.td.wallendar.dtos.response.ApplicationUserResponse;
import com.td.wallendar.models.ApplicationUser;

import java.util.HashSet;
import java.util.Set;

public class ApplicationUserMapper {

    public static ApplicationUser toModel(ApplicationUserResponse applicationUserResponse) {
        return new ApplicationUser(
                applicationUserResponse.getId(),
                applicationUserResponse.getEmail(),
                applicationUserResponse.getFirstName(),
                applicationUserResponse.getLastName(),
                applicationUserResponse.getPhoneNumber()
        );
    }

    public static Set<ApplicationUser> toModel(Set<ApplicationUserResponse> applicationUserResponses) {
        Set<ApplicationUser> applicationUsers = new HashSet<>();
        for (ApplicationUserResponse applicationUserResponse : applicationUserResponses) {
            applicationUsers.add(toModel(applicationUserResponse));
        }
        return applicationUsers;
    }
}
