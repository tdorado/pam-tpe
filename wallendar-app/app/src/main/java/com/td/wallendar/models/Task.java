package com.td.wallendar.models;

public class Task {
    private User userAssigned;
    private String title;
    private double amount;

    public Task(User userAssigned, String title, double amount) {
        this.userAssigned = userAssigned;
        this.amount = amount;
    }

    public User getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(User userAssigned) {
        this.userAssigned = userAssigned;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
