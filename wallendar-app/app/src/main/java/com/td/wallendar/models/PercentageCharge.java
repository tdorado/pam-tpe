package com.td.wallendar.models;

import java.util.List;
import java.util.Map;

public class PercentageCharge extends Charge {
    private Map<User, Double> percentagePerUser;

    //Hay que verificar que la sumatoria de los percentagePerUser sea = 100.00
    public PercentageCharge(User owner, String title, List<User> debtors,
                            double amount, Map<User, Double> percentagePerUser) {
        super(owner, title, ChargeType.PERCENTAGE, debtors, amount);
        this.percentagePerUser = percentagePerUser;
    }

    public Map<User, Double> getPercentagePerUser() {
        return percentagePerUser;
    }

    public void setPercentagePerUser(Map<User, Double> percentagePerUser) {
        this.percentagePerUser = percentagePerUser;
    }
}
