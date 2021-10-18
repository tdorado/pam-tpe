package com.td.wallendarbackend.dtos.responses;

import com.td.wallendarbackend.models.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {
    private long id;
    private String title;
    private ApplicationUserResponse owner;
    private Set<ApplicationUserResponse> members;
    private Set<ChargeResponse> charges;
    private Set<DebtResponse> debts;
    private Set<PaymentResponse> payments;

    public GroupResponse(Group group) {
        this.id = group.getId();
        this.title = group.getTitle();
        this.owner = new ApplicationUserResponse(group.getOwner());
        this.members = group.getMembers().stream().map(ApplicationUserResponse::new).collect(Collectors.toSet());
        if(group.getCharges() != null) {
            this.charges = group.getCharges().stream().map(ChargeResponse::new).collect(Collectors.toSet());
        }
        else{
            this.charges = Collections.emptySet();
        }
        if(group.getDebts() != null) {
            this.debts = group.getDebts().stream().map(DebtResponse::new).collect(Collectors.toSet());
        }
        else{
            this.debts = Collections.emptySet();
        }
        if(group.getPayments() != null) {
            this.payments = group.getPayments().stream().map(PaymentResponse::new).collect(Collectors.toSet());
        }
        else{
            this.payments = Collections.emptySet();
        }
    }
}
