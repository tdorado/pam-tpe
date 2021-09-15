package com.td.wallendar.addcharge.ui;

import com.td.wallendar.AbstractView;
import com.td.wallendar.models.Group;

import java.util.List;

public interface AddChargeView extends AbstractView {
    void onGroupsLoadError();

    void chargeError();

    void chargeAddedOk();

    void addGroups(List<Group> groups);

    void setSelectedGroup(Long groupId);
}
