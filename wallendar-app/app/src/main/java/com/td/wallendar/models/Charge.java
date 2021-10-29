package com.td.wallendar.models;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Charge implements GroupHistory {
    private long id;
    private String title;
    private ApplicationUser owner;
    private Set<ApplicationUser> debtors;
    private double amount;
    private Date date;
    private ChargeType chargeType = ChargeType.EQUALLY;

    public Charge(String title, ApplicationUser owner, Set<ApplicationUser> debtors, double amount, Date date) {
        this.title = title;
        this.owner = owner;
        this.debtors = debtors;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return id == charge.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public MoneyTransactionType getMoneyTransactionType() {
        return MoneyTransactionType.CHARGE;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public ApplicationUser getFromUser() {
        return getOwner();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ApplicationUser getOwner() {
        return owner;
    }

    public void setOwner(ApplicationUser owner) {
        this.owner = owner;
    }

    public Set<ApplicationUser> getDebtors() {
        return debtors;
    }

    public void setDebtors(Set<ApplicationUser> debtors) {
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
