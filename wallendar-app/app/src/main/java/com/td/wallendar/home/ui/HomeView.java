package com.td.wallendar.home.ui;

import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Group;

import java.util.List;

public interface HomeView {
    void showGroups();

    void showBalances();

    void showProfile();

    void bindGroups(List<Group> groups);

    void bindDebts(List<Debt> debts);

    void bindProfile(ApplicationUser applicationUser);

    void updateGroup(Group group);

    void removeDebt(Debt debt);

    void errorGettingUser();

    void errorGettingGroups();

    void errorGettingBalances();
}
