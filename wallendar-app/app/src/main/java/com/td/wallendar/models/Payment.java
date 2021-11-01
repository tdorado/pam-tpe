package com.td.wallendar.models;

import java.util.Date;
import java.util.Objects;


public class Payment {
    private long id;
    private ApplicationUser from;
    private ApplicationUser to;
    private double amount;
    private Date date;

    public Payment(long id, ApplicationUser from, ApplicationUser to, double amount, Date date) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
