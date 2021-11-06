package com.td.wallendar.addmembers.ui;

import com.td.wallendar.dtos.request.AddMembersRequest;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;

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

    public void submitMembers(final long groupId, final List<String> emailMembers) {
        disposable.add(groupsRepository.addMembers(groupId, new AddMembersRequest(new HashSet<>(emailMembers)))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onMembersAddedSuccessfully, this::onMembersAddedWithError));
    }

    private void onMembersAddedWithError(Throwable throwable) {
        view.get().onMembersAddedWithError();
    }

    private void onMembersAddedSuccessfully(Group group) {
        view.get().onMembersAddedSuccessfully(group.getId());
    }
}
