package com.td.wallendar.dtos.request;


public class AddChargeRequest {
    private String title;
    private long ownerId;
    private double amount;

    public AddChargeRequest() {
    }

    public AddChargeRequest(String title, long ownerId, double amount) {
        this.title = title;
        this.ownerId = ownerId;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
