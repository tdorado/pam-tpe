package com.td.wallendar.models;

import java.util.List;

public class Group {
    private Long id;
    private String title;
    private User owner;
    private List<User> users;
    private List<Charge> charges;
    private List<MoneyTransaction> moneyTransactions;

    public Group(final Long id,
                 final String title,
                 final User owner,
                 final List<User> users,
                 final List<Charge> charges,
                 final List<MoneyTransaction> moneyTransactions) {
        this.title = title;
        this.owner = owner;
        this.users = users;
        this.charges = charges;
        this.moneyTransactions = moneyTransactions;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
