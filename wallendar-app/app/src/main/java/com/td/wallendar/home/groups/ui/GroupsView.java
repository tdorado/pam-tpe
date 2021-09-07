package com.td.wallendar.home.groups.ui;

import com.td.wallendar.AbstractView;
import com.td.wallendar.home.groups.GroupsAdapter;
import com.td.wallendar.models.Group;

import java.util.List;

public interface GroupsView extends AbstractView {
    void bind();

    void listGroups(final List<Group> groups);

    void enterGroup(final Group group);
}
