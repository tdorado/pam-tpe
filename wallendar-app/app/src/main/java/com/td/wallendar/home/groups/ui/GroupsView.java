package com.td.wallendar.home.groups.ui;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.AbstractView;
import com.td.wallendar.home.ui.HomeView;
import com.td.wallendar.models.Group;

import java.util.List;

public interface GroupsView extends AbstractView {
    void bind(final ExtendedFloatingActionButton addChargeFAB, final HomeView homeView);

    void listGroups(final List<Group> groups);

    void enterGroup(final Long groupId);
}
