package com.td.wallendar.models;

import java.util.Date;
import java.util.List;

public class EqualCharge extends Charge {

    public EqualCharge(final User owner,
                       final String title,
                       final List<User> debtors,
                       final double amount,
                       final Date date) {
        super(owner, title, ChargeType.EQUALLY, debtors, amount, date);
    }
}
