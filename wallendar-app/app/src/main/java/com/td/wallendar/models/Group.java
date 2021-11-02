package com.td.wallendar.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


public class Group implements Serializable {
    private long id;
    private String title;
    private ApplicationUser owner;
    private Set<ApplicationUser> members;
    private Set<Charge> charges;
    private Set<Debt> debts;
    private Set<Payment> payments;

    public Group() {
    }

    public Group(long id, String title, ApplicationUser owner, Set<ApplicationUser> members, Set<Charge> charges, Set<Debt> debts, Set<Payment> payments) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.members = members;
        this.charges = charges;
        this.debts = debts;
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id;
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

    public String getTitle() {
        return title;
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

    public Set<ApplicationUser> getMembers() {
        return members;
    }

    public void setMembers(Set<ApplicationUser> members) {
        this.members = members;
    }

    public Set<Charge> getCharges() {
        return charges;
    }

    public void setCharges(Set<Charge> charges) {
        this.charges = charges;
    }

    public Set<Debt> getDebts() {
        return debts;
    }

    public void setDebts(Set<Debt> debts) {
        this.debts = debts;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
