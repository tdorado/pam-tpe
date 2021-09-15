package com.td.wallendar.dtos.response;

public class DebtResponse {
    private String userFrom;
    private String userTo;
    private double amount;
    private boolean settledUp;

    public DebtResponse() {
    }

    public DebtResponse(String userFrom, String userTo, double amount, boolean settledUp) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
        this.settledUp = settledUp;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isSettledUp() {
        return settledUp;
    }

    public void setSettledUp(boolean settledUp) {
        this.settledUp = settledUp;
    }
}
