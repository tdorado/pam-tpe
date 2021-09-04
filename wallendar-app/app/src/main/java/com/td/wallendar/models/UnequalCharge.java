package com.td.wallendar.models;

import java.util.List;
import java.util.Map;

public class UnequalCharge extends Charge {
    private Map<User, Double> amountPerUser;

    //Hay que verificar que la sumatoria de amountPerUser sea = amount
    public UnequalCharge(User owner, String title, List<User> debtors,
                         double amount, Map<User, Double> amountPerUser) {
        super(owner, title, ChargeType.UNEQUALLY, debtors, amount);
        this.amountPerUser = amountPerUser;
    }

    public Map<User, Double> getAmountPerUser() {
        return amountPerUser;
    }

    public void setAmountPerUser(Map<User, Double> amountPerUser) {
        this.amountPerUser = amountPerUser;
    }
}
