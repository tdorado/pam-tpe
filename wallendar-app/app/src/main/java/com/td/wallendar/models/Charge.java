package com.td.wallendar.models;

import java.util.List;

public abstract class Charge {
    private User owner;
    private String title;
    private ChargeType chargeType;
    private List<User> debtors;
    private double amount;

    public Charge(User owner, String title, ChargeType chargeType, List<User> debtors, double amount) {
        this.owner = owner;
        this.title= title;
        this.chargeType = chargeType;
        this.debtors = debtors;
        this.amount = amount;
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
}
