package com.td.wallendar.addmembers.ui;

import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class AddMembersPresenter {

    private final WeakReference<AddMembersView> view;
    private final GroupsRepository groupsRepository;
    private final CompositeDisposable disposable;
    private final SchedulerProvider schedulerProvider;

    public AddMembersPresenter(final AddMembersView addMembersView, final GroupsRepository groupsRepository) {
        view = new WeakReference<>(addMembersView);
        this.groupsRepository = groupsRepository;
        this.disposable = new CompositeDisposable();
        this.schedulerProvider = new AndroidSchedulerProvider();
    }
}
