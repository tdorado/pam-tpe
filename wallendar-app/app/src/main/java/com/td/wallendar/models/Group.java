package com.td.wallendar.models;

import java.util.List;

public class Group {
    private String title;
    private User owner;
    private List<User> users;
    private List<Charge> charges;
    private List<MoneyTransaction> moneyTransactions;

    public Group(String title, User owner, List<User> users, List<Charge> charges, List<MoneyTransaction> moneyTransactions) {
        this.title = title;
        this.owner = owner;
        this.users = users;
        this.charges = charges;
        this.moneyTransactions = moneyTransactions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public List<MoneyTransaction> getMoneyTransactions() {
        return moneyTransactions;
    }

    public void setMoneyTransactions(List<MoneyTransaction> moneyTransactions) {
        this.moneyTransactions = moneyTransactions;
    }
}
