package com.td.wallendar.home.ui;

import java.lang.ref.WeakReference;

public class HomePresenter {
    private final WeakReference<HomeView> homeView;

    public HomePresenter(HomeView homeView) {
        this.homeView = new WeakReference<>(homeView);
    }

    public void onGroupsClicked() {
        if (homeView.get() != null) {
            homeView.get().showGroups();
        }
    }

    public void onEventsClicked() {
        if (homeView.get() != null) {
            homeView.get().showEvents();
        }
    }

    public void onBalancesClicked() {
        if (homeView.get() != null) {
            homeView.get().showBalances();
        }
    }

    public void onProfileClicked() {
        if (homeView.get() != null) {
            homeView.get().showProfile();
        }
    }
}
