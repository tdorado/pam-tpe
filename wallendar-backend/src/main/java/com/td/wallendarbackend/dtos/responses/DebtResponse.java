package com.td.wallendarbackend.dtos.responses;

import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.DebtDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private boolean containsDetails;

    public DebtResponse(Debt debt) {
        this.id = debt.getId();
        this.from = new ApplicationUserResponse(debt.getFrom());
        this.to = new ApplicationUserResponse(debt.getTo());
        this.amount = debt.getAmount();
        this.groupId = debt.getGroup().getId();
    }

    public DebtResponse(Debt debt, Set<DebtDetail> details) {
        this(debt);
        this.containsDetails = !details.isEmpty();
    }
}
