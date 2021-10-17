package com.td.wallendarbackend.dtos.responses;

import com.td.wallendarbackend.models.Debt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtResponse {
    private long id;
    private ApplicationUserResponse from;
    private ApplicationUserResponse to;
    private double amount;
    private long groupId;

    public DebtResponse(Debt debt) {
        this.id = debt.getId();
        this.from = new ApplicationUserResponse(debt.getFrom());
        this.to = new ApplicationUserResponse(debt.getTo());
        this.amount = debt.getAmount();
        this.groupId = debt.getGroup().getId();
    }
}
