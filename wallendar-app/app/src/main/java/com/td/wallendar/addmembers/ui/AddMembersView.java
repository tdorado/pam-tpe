package com.td.wallendar.addmembers.ui;

import java.lang.reflect.Member;
import java.util.Set;

public interface AddMembersView {
    void onMembersAddedSuccessfully(long groupId);

    void onMembersAddedWithError();
}
