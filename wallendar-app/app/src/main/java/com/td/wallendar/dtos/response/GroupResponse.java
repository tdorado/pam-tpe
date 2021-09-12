package com.td.wallendar.dtos.response;

import java.util.Date;
import java.util.List;

public class GroupResponse {
    private String title;
    private String owner;
    private Date date;
    private List<String> users;
    private List<ChargeResponse> charges;
    private List<BalanceResponse> balances;


    public GroupResponse(String title, String owner, Date date, List<String> users, List<ChargeResponse> charges, List<BalanceResponse> balances) {
        this.title = title;
        this.owner = owner;
        this.date = date;
        this.users = users;
        this.charges = charges;
        this.balances = balances;
    }

    public GroupResponse() {
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

    public List<ChargeResponse> getCharges() {
        return charges;
    }

    public void setCharges(List<ChargeResponse> charges) {
        this.charges = charges;
    }

    public List<BalanceResponse> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceResponse> balances) {
        this.balances = balances;
    }
}
