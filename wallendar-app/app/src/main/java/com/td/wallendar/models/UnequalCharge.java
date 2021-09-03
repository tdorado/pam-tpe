package com.td.wallendar.models;

import android.util.Pair;

import java.util.List;

public class UnequalCharge extends Charge{
    private List<Pair<User, Double>> amountPerUser;

    //Hay que verificar que la sumatoria de amountPerUser sea = amount
    public UnequalCharge(User owner, String title, List<User> debtors,
                            double amount, List<Pair<User, Double>> amountPerUser) {
        super(owner, title, ChargeType.UNEQUALLY, debtors, amount);
        this.amountPerUser = amountPerUser;
    }

    public List<Pair<User, Double>> getAmountPerUser() {
        return amountPerUser;
    }

    public void setAmountPerUser(List<Pair<User, Double>> amountPerUser) {
        this.amountPerUser = amountPerUser;
    }
}
