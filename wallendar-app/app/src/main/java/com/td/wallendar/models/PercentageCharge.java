package com.td.wallendar.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PercentageCharge extends Charge {
    private Map<User, Double> percentagePerUser;

    //Hay que verificar que la sumatoria de los percentagePerUser sea = 100.00
    public PercentageCharge(final User owner, final String title, final List<User> debtors,
                            final double amount, final Map<User, Double> percentagePerUser,
                            final Date date) {
        super(owner, title, ChargeType.PERCENTAGE, debtors, amount, date);
        this.percentagePerUser = percentagePerUser;
    }

    public Map<User, Double> getPercentagePerUser() {
        return percentagePerUser;
    }

    public void setPercentagePerUser(Map<User, Double> percentagePerUser) {
        this.percentagePerUser = percentagePerUser;
    }
}
