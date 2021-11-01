package com.td.wallendar.home.ui;

import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class HomePresenter {
    private final WeakReference<HomeView> homeView;
    private final GroupsRepository groupsRepository;
    private final SchedulerProvider schedulerProvider;
    private CompositeDisposable disposable;


    public HomePresenter(final HomeView homeView, final GroupsRepository groupsRepository,
                         final SchedulerProvider schedulerProvider) {
        this.homeView = new WeakReference<>(homeView);
        this.groupsRepository = groupsRepository;
        this.schedulerProvider = schedulerProvider;
        this.disposable = new CompositeDisposable();
    }

    public void onGroupsClicked() {
        if (homeView.get() != null) {
            homeView.get().showGroups();
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

    public void onViewAttached(){
        //TODO hacer esto en storage?
        long userId = 1;
        disposable.add(groupsRepository.getGroupsByUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onGroupsReceived, this::onGroupsError));
    }

    private void onGroupsReceived(List<Group> groups) {
        if (homeView.get() != null) {
            homeView.get().bindGroups(groups);
        }
    }

    private void onGroupsError(Throwable throwable) {
        System.out.println(throwable);
    }

    public void onViewDetached(){
        disposable.dispose();
    }
}
