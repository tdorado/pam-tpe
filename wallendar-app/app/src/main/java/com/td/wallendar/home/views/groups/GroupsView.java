package com.td.wallendar.home.views.groups;

import com.td.wallendar.home.views.models.Group;

import java.util.List;

public interface GroupsView {
    void bind(final GroupsAdapter groupsAdapter);
    void listGroups(final List<Group> groups);
    void enterGroup(final Group group);
}
