package com.td.wallendarbackend.dtos.responses;

import com.td.wallendarbackend.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserResponse {
    private long id;
    private String email;
    private String firstName;
    private String lastName;

    public ApplicationUserResponse(ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.email = applicationUser.getEmail();
        this.firstName = applicationUser.getFirstName();
        this.lastName = applicationUser.getLastName();
    }
}