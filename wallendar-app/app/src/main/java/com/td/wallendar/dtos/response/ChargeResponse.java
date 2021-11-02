package com.td.wallendar.dtos.response;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ChargeResponse {
    private long id;
    private String title;
    private ApplicationUserResponse owner;
    private Set<ApplicationUserResponse> debtors;
    private double amount;
    private Date date;

    public ChargeResponse() {
    }

    public ChargeResponse(long id, String title, ApplicationUserResponse owner, Set<ApplicationUserResponse> debtors, double amount, Date date) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.debtors = debtors;
        this.amount = amount;
        this.date = date;
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

    public Set<ApplicationUserResponse> getDebtors() {
        return debtors;
    }

    public void setDebtors(Set<ApplicationUserResponse> debtors) {
        this.debtors = debtors;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
