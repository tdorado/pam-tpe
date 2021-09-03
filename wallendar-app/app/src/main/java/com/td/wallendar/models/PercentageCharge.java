package com.td.wallendar.models;

import android.util.Pair;

import java.util.List;

public class PercentageCharge extends Charge{
    private List<Pair<User, Double>> percentagePerUser;

    //Hay que verificar que la sumatoria de los percentagePerUser sea = 100.00
    public PercentageCharge(User owner, String title, List<User> debtors,
                            double amount, List<Pair<User, Double>> percentagePerUser) {
        super(owner, title, ChargeType.PERCENTAGE, debtors, amount);
        this.percentagePerUser = percentagePerUser;
    }

    public List<Pair<User, Double>> getPercentagePerUser() {
        return percentagePerUser;
    }

    public void setPercentagePerUser(List<Pair<User, Double>> percentagePerUser) {
        this.percentagePerUser = percentagePerUser;
    }
}
