package com.td.wallendar.dtos.request;

import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Charge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddChargeRequest {
    private String owner;
    private String title;
    private String chargeType;
    private List<String> debtors;
    private double amount;
    private Date date;

    public AddChargeRequest(String owner, String title, String chargeType, List<String> debtors, double amount, Date date) {
        this.owner = owner;
        this.title = title;
        this.chargeType = chargeType;
        this.debtors = debtors;
        this.amount = amount;
        this.date = date;
    }

    public AddChargeRequest() {
    }

    public static AddChargeRequest from(final Charge charge) {
        final List<String> debtors = new ArrayList<>();
        for (ApplicationUser u : charge.getDebtors()) {
            debtors.add(u.getEmail());
        }
        return new AddChargeRequest(
                charge.getOwner().getEmail(),
                charge.getTitle(),
                charge.getChargeType().name(),
                debtors,
                charge.getAmount(),
                charge.getDate()
        );
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public List<String> getDebtors() {
        return debtors;
    }

    public void setDebtors(List<String> debtors) {
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
