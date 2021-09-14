package com.td.wallendar.dtos.response;

import java.util.Date;
import java.util.List;

public class EventResponse {
    private String title;
    private String owner;
    private Date date;
    private List<String> users;
    private List<String> tasks;
    private List<ChargeResponse> charges;
    private List<DebtResponse> balances;

    public EventResponse() {
    }

    public EventResponse(String title, String owner, Date date, List<String> users, List<String> tasks, List<ChargeResponse> charges, List<DebtResponse> balances) {
        this.title = title;
        this.owner = owner;
        this.date = date;
        this.users = users;
        this.tasks = tasks;
        this.charges = charges;
        this.balances = balances;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public List<ChargeResponse> getCharges() {
        return charges;
    }

    public void setCharges(List<ChargeResponse> charges) {
        this.charges = charges;
    }

    public List<DebtResponse> getBalances() {
        return balances;
    }

    public void setBalances(List<DebtResponse> balances) {
        this.balances = balances;
    }

}
