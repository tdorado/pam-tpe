package com.td.wallendarbackend.dtos.responses;

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
}