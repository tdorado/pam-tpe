package com.td.wallendar.addgroup.ui;

import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class AddGroupPresenter {

    private final WeakReference<AddGroupView> view;
    private final GroupsRepository groupsRepository;
    private final CompositeDisposable disposable;
    private final SchedulerProvider schedulerProvider;


    public AddGroupPresenter(final AddGroupView addGroupView, final GroupsRepository groupsRepository) {
        view = new WeakReference<>(addGroupView);
        this.groupsRepository = groupsRepository;
        this.disposable = new CompositeDisposable();
        this.schedulerProvider = new AndroidSchedulerProvider();
    }

    public void createGroup(final String groupTitle, final Long ownerId) {
        disposable.add(groupsRepository.createGroup(groupTitle, ownerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onGroupCreatedSuccessfully, this::OnGroupCreatedWithError));
    }

    private void onGroupCreatedSuccessfully(Group group) {
        view.get().onGroupCreated(true);
    }

    private void OnGroupCreatedWithError(Throwable throwable) {
        view.get().onGroupCreated(false);
    }
}
