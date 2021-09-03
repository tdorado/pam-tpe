package com.td.wallendar.models;

import java.util.Date;

public class MoneyTransaction {
    private User from;
    private User to;
    private double amount;
    private Date date;
    private MoneyTransactionType moneyTransactionType;

    public MoneyTransaction(User from, User to, double amount, Date date,
                            MoneyTransactionType moneyTransactionType) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
        this.moneyTransactionType = moneyTransactionType;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MoneyTransactionType getMoneyTransactionType() {
        return moneyTransactionType;
    }

    public void setMoneyTransactionType(MoneyTransactionType moneyTransactionType) {
        this.moneyTransactionType = moneyTransactionType;
    }
}
