package com.td.wallendar.models;

import java.util.Date;
import java.util.List;

public abstract class Charge implements GroupHistory {
    private Long id;
    private User owner;
    private String title;
    private ChargeType chargeType;
    private List<User> debtors;
    private double amount;
    private Date date;
    private Group group;

    public Charge(final User owner, final String title, final ChargeType chargeType,
                  final List<User> debtors, final double amount, final Date date, final Group group) {
        this.owner = owner;
        this.title = title;
        this.chargeType = chargeType;
        this.debtors = debtors;
        this.amount = amount;
        this.date = date;
        this.group = group;
    }

    public Charge() {
    }

    public Charge(final Long id, final User owner, final String title, final ChargeType chargeType,
                  final List<User> debtors, final double amount, final Date date) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.chargeType = chargeType;
        this.debtors = debtors;
        this.amount = amount;
        this.date = date;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public MoneyTransactionType getMoneyTransactionType() {
        return MoneyTransactionType.CHARGE;
    }

    @Override
    public User getFromUser() {
        return owner;
    }

    // If there is no toUser, then this activity belongs to a charge where it generates n to's
    @Override
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
