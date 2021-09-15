package com.td.wallendar.dtos.response;

public class BalanceResponse {
    private String user;
    private double amount;

    public BalanceResponse(String user, double amount) {
        this.user = user;
        this.amount = amount;
    }

    public BalanceResponse() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
