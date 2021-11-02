package com.td.wallendar.home.groups.ui;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.home.groups.GroupAdapter;

public interface GroupsView {
    void bind(final GroupAdapter groupAdapter, final ExtendedFloatingActionButton addChargeFAB);
}
