package com.td.wallendar.models;

import java.util.List;

public class EqualCharge extends Charge {

    public EqualCharge(User owner, String title, List<User> debtors, double amount) {
        super(owner, title, ChargeType.EQUALLY, debtors, amount);
    }
}
