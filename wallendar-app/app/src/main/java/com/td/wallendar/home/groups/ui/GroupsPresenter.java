package com.td.wallendar.home.groups.ui;

import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.AndroidSchedulerProvider;
import com.td.wallendar.utils.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class GroupsPresenter {

    private final WeakReference<GroupsView> groupsView;
    private final GroupsRepository groupsRepository;

    private CompositeDisposable disposable;
    private SchedulerProvider schedulerProvider;

    public GroupsPresenter(final WeakReference<GroupsView> groupsView,
                           final GroupsRepository groupsRepository) {
        this.groupsView = groupsView;
        this.groupsRepository = groupsRepository;
        this.schedulerProvider = new AndroidSchedulerProvider();
        this.disposable = new CompositeDisposable();
    }

    void listGroups(final Long userId) {
        if (groupsRepository != null) {
            disposable.add(groupsRepository.getGroupsByUser(userId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGroupsReceived, this::onGroupsError));
        }
    }

    private void onGroupsReceived(List<Group> groups) {
        if (groupsView != null) {
            groupsView.get().listGroups(groups);
        }
    }

    private void onGroupsError(Throwable throwable) {
    }

    void getGroup(final Long groupId) {
        if (groupsRepository != null) {
            final Group group = groupsRepository.getGroup(groupId);
            groupsView.get().enterGroup(group);
        }
    }
}
