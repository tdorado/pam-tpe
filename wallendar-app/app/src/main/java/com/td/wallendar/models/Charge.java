package com.td.wallendar.models;

import java.util.List;

public class Charge {
    private User owner;
    private String title;
    private PaymentDivision paymentDivision;
    private List<User> debtors;
    private double amount;

    public Charge(User owner, String title, PaymentDivision paymentDivision, List<User> debtors, double amount) {
        this.owner = owner;
        this.paymentDivision = paymentDivision;
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

    public PaymentDivision getPaymentDivision() {
        return paymentDivision;
    }

    public void setPaymentDivision(PaymentDivision paymentDivision) {
        this.paymentDivision = paymentDivision;
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
