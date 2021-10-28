package com.td.wallendar.models;

import java.util.Objects;

public class Debt {
    private long id;
    private ApplicationUser from;
    private ApplicationUser to;
    private double amount;
    private Debt reverseDebt;
    private Group group;

    public Debt(ApplicationUser from, ApplicationUser to, Group group) {
        this.from = from;
        this.to = to;
        this.amount = 0;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt debt = (Debt) o;
        return id == debt.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApplicationUser getFrom() {
        return from;
    }

    public void setFrom(ApplicationUser from) {
        this.from = from;
    }

    public ApplicationUser getTo() {
        return to;
    }

    public void setTo(ApplicationUser to) {
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Debt getReverseDebt() {
        return reverseDebt;
    }

    public void setReverseDebt(Debt reverseDebt) {
        this.reverseDebt = reverseDebt;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
