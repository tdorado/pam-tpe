package com.td.wallendar.models;

import java.util.Date;
import java.util.List;

public abstract class Charge implements GroupHistory {
    private User owner;
    private String title;
    private ChargeType chargeType;
    private List<User> debtors;
    private double amount;
    private Date date;

    public Charge(final User owner, final String title, final ChargeType chargeType,
                  final List<User> debtors, final double amount, final Date date) {
        this.owner = owner;
        this.title = title;
        this.chargeType = chargeType;
        this.debtors = debtors;
        this.amount = amount;
        this.date = date;
    }

    public User getFromUser() {
        return owner;
    }

    // If there is no toUser, then this activity belongs to a charge where it generates n to's
    public User getToUser() {
        return null;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public List<User> getDebtors() {
        return debtors;
    }

    public void setDebtors(List<User> debtors) {
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
