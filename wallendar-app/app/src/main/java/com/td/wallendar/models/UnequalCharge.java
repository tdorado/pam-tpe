package com.td.wallendar.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UnequalCharge extends Charge {
    private Map<User, Double> amountPerUser;

    //Hay que verificar que la sumatoria de amountPerUser sea = amount
    public UnequalCharge(final Long id, final User owner, final String title, final List<User> debtors,
                         final double amount, final Map<User, Double> amountPerUser,
                         final Date date) {
        super(id, owner, title, ChargeType.UNEQUALLY, debtors, amount, date);
        this.amountPerUser = amountPerUser;
    }

    public UnequalCharge() {

    }

    public Map<User, Double> getAmountPerUser() {
        return amountPerUser;
    }

    public void setAmountPerUser(Map<User, Double> amountPerUser) {
        this.amountPerUser = amountPerUser;
    }
}
