package com.td.wallendar.dtos.response;

import java.util.Set;

public class GroupResponse {
    private long id;
    private String title;
    private ApplicationUserResponse owner;
    private Set<ApplicationUserResponse> members;
    private Set<ChargeResponse> charges;
    private Set<DebtResponse> debts;
    private Set<PaymentResponse> payments;

    public GroupResponse(long id, String title, ApplicationUserResponse owner, Set<ApplicationUserResponse> members, Set<ChargeResponse> charges, Set<DebtResponse> debts, Set<PaymentResponse> payments) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.members = members;
        this.charges = charges;
        this.debts = debts;
        this.payments = payments;
    }

    public GroupResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ApplicationUserResponse getOwner() {
        return owner;
    }

    public void setOwner(ApplicationUserResponse owner) {
        this.owner = owner;
    }

    public Set<ApplicationUserResponse> getMembers() {
        return members;
    }

    public void setMembers(Set<ApplicationUserResponse> members) {
        this.members = members;
    }

    public Set<ChargeResponse> getCharges() {
        return charges;
    }

    public void setCharges(Set<ChargeResponse> charges) {
        this.charges = charges;
    }

    public Set<DebtResponse> getDebts() {
        return debts;
    }

    public void setDebts(Set<DebtResponse> debts) {
        this.debts = debts;
    }

    public Set<PaymentResponse> getPayments() {
        return payments;
    }

    public void setPayments(Set<PaymentResponse> payments) {
        this.payments = payments;
    }
}
