package com.td.wallendar.groupmembers.ui;

import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class GroupMembersPresenter {
    private final WeakReference<GroupMembersView> groupMembersView;
    private final GroupsRepository groupsRepository;
    private final CompositeDisposable disposable;
    private final SchedulerProvider schedulerProvider;

    public GroupMembersPresenter(GroupMembersView groupMembersView, GroupsRepository groupsRepository, SchedulerProvider schedulerProvider) {
        this.groupMembersView = new WeakReference<>(groupMembersView);
        this.groupsRepository = groupsRepository;
        this.disposable = new CompositeDisposable();
        this.schedulerProvider = schedulerProvider;
    }

    public void onViewAttached(long groupId) {
        disposable.add(groupsRepository.getGroup(groupId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onGroupReceived, this::onGroupFailed));
    }

    public void onViewDetached() {
        disposable.dispose();
    }

    private void onGroupReceived(Group group) {
        groupMembersView.get().bind(new ArrayList<>(group.getMembers()));
    }

    private void onGroupFailed(Throwable throwable) {
        groupMembersView.get().failedToLoadMembers();
    }
}
