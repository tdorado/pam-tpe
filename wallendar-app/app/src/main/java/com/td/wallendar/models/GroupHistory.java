package com.td.wallendar.models;

import java.util.Date;

// i dont like this name at all
public interface GroupHistory {
    User getFromUser();
    // If there is no toUser, then this activity belongs to a charge where it generates n to's
    User getToUser();
    Date getDate();
    double getAmount();
}
