package com.td.wallendarbackend.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequest {

    @NotNull
    @NotEmpty
    private String title;

    private long ownerId;

    private double amount;
}
