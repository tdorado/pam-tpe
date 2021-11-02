package com.td.wallendar.models;

import java.util.Date;

public interface GroupHistory {
    GroupHistoryType getGroupHistoryType();

    String getTitle();

    ApplicationUser getFromUser();

    ApplicationUser getToUser();

    double getAmount();

    Date getDate();
}
