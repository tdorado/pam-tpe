package com.td.wallendar.home.ui;

import com.td.wallendar.models.Group;

import java.util.List;

public interface HomeView {
    void showGroups();

    void showBalances();

    void showProfile();

    void bindGroups(List<Group> groups);
}
