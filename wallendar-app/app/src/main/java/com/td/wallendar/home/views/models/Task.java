package com.td.wallendar.home.views.models;

public class Task {
    private User userAssigned;
    private double amount;

    public Task(User userAssigned, double amount) {
        this.userAssigned = userAssigned;
        this.amount = amount;
    }

    public User getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(User userAssigned) {
        this.userAssigned = userAssigned;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
