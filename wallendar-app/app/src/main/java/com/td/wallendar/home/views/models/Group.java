package com.td.wallendar.home.views.models;

import java.util.List;

public class Group {
    private String groupName;
    private List<User> users;
    private List<Charge> charges;
    private List<Debt> debts;
    private List<Payment> payments;

    public Group(String groupName, List<User> users, List<Charge> charges, List<Debt> debts, List<Payment> payments) {
        this.groupName = groupName;
        this.users = users;
        this.charges = charges;
        this.debts = debts;
        this.payments = payments;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
