package com.td.wallendar.models;

import java.util.Date;

public interface GroupHistory {
    MoneyTransactionType getMoneyTransactionType();
    String getTitle();
    ApplicationUser getFromUser();
    double getAmount();
    Date getDate();
}
