package com.td.wallendar.dtos.response;

public class DebtResponse {
    private long id;
    private ApplicationUserResponse from;
    private ApplicationUserResponse to;
    private double amount;
    private long groupId;

    public DebtResponse() {
    }

    public DebtResponse(long id, ApplicationUserResponse from, ApplicationUserResponse to, double amount, long groupId) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.groupId = groupId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApplicationUserResponse getFrom() {
        return from;
    }

    public void setFrom(ApplicationUserResponse from) {
        this.from = from;
    }

    public ApplicationUserResponse getTo() {
        return to;
    }

    public void setTo(ApplicationUserResponse to) {
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

}
