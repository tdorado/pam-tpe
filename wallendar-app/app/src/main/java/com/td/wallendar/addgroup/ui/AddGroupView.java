package com.td.wallendar.addgroup.ui;

import com.td.wallendar.models.Group;

public interface AddGroupView {
    void onGroupCreated(final Group group);

    void onGroupCreatedWithErrors();
}
