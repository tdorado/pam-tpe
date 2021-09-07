package com.td.wallendar.models;

import java.util.Date;

public class Debt implements GroupHistory {
    private User from;
    private User to;
    private double amount;
    private Date date;
    private boolean settledUp;
    private Charge associatedCharge;

    public Debt(User from, User to, double amount, Date date,
                boolean settledUp, Charge associatedCharge) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
        this.settledUp = settledUp;
        this.associatedCharge = associatedCharge;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public User getFromUser() {
        return getFrom();
    }

    @Override
    public User getToUser() {
        return getTo();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSettledUp() {
        return settledUp;
    }

    public void setSettledUp(boolean settledUp) {
        this.settledUp = settledUp;
    }

    public Charge getAssociatedCharge() {
        return associatedCharge;
    }

    public void setAssociatedCharge(Charge associatedCharge) {
        this.associatedCharge = associatedCharge;
    }
}
