package com.td.wallendarbackend.dtos.responses;


import com.td.wallendarbackend.models.Charge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeResponse {
    private long id;
    private String title;
    private ApplicationUserResponse owner;
    private Set<ApplicationUserResponse> debtors;
    private double amount;
    private Date date;

    public ChargeResponse(Charge charge) {
        this.id = charge.getId();
        this.title = charge.getTitle();
        this.owner = new ApplicationUserResponse(charge.getOwner());
        this.debtors = charge.getDebtors().stream().map(ApplicationUserResponse::new).collect(Collectors.toSet());
        this.amount = charge.getAmount();
        this.date = charge.getDate();
    }
}
