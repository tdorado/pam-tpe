package com.td.wallendar.addcharge.ui;

import com.td.wallendar.models.Group;

import java.util.List;

public interface AddChargeView {

    void onGroupsLoadOk(List<Group> groups);

    void onGroupsLoadError();

    void chargeAddedOk();

    void chargeError();

    void setSelectedGroup(final Long groupId);
}
