package com.td.wallendar.addgroup.ui;

import com.td.wallendar.dtos.request.AddGroupRequest;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class AddGroupPresenter {

    private final WeakReference<AddGroupView> view;
    private final GroupsRepository groupsRepository;
    private final CompositeDisposable disposable;
    private final SchedulerProvider schedulerProvider;


    public AddGroupPresenter(final AddGroupView addGroupView, final GroupsRepository groupsRepository, final SchedulerProvider schedulerProvider) {
        view = new WeakReference<>(addGroupView);
        this.groupsRepository = groupsRepository;
        this.disposable = new CompositeDisposable();
        this.schedulerProvider = schedulerProvider;
    }

    public void createGroup(final String groupTitle, final Long ownerId) {
        disposable.add(groupsRepository.createGroup(new AddGroupRequest(groupTitle, ownerId))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onGroupCreatedSuccessfully, this::OnGroupCreatedWithError));
    }

    private void onGroupCreatedSuccessfully(final Group group) {
        view.get().onGroupCreated(group);
    }

    private void OnGroupCreatedWithError(Throwable throwable) {
        //TODO error
    }

    public void onViewDetached() {
        disposable.dispose();
    }
}
