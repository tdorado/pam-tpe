package com.td.wallendar.models;

import java.io.Serializable;
import java.util.Objects;

public class Debt implements Serializable {
    private long id;
    private ApplicationUser from;
    private ApplicationUser to;
    private double amount;
    private long groupId;

    public Debt(long id, ApplicationUser from, ApplicationUser to, double amount, long groupId) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.groupId = groupId;
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
        return Double.parseDouble(String.format("%.2f", amount));
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
