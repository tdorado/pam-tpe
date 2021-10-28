package com.td.wallendar.dtos.response;

import java.util.Date;

public class PaymentResponse {
    private long id;
    private ApplicationUserResponse from;
    private ApplicationUserResponse to;
    private double amount;
    private Date date;
    private long groupId;

    public PaymentResponse(long id, ApplicationUserResponse from, ApplicationUserResponse to, double amount, Date date, long groupId) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
        this.groupId = groupId;
    }

    public PaymentResponse() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
