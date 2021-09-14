package com.td.wallendar.group.ui;

import com.td.wallendar.models.Group;
import com.td.wallendar.models.GroupHistory;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class GroupPresenter {

    private final WeakReference<GroupView> groupView;
    private final GroupsRepository groupsRepository;

    private CompositeDisposable disposable;
    private SchedulerProvider schedulerProvider;

    public GroupPresenter(final GroupView groupView,
                          final GroupsRepository groupsRepository) {
        this.groupView = new WeakReference<>(groupView);
        this.groupsRepository = groupsRepository;
        this.schedulerProvider = new AndroidSchedulerProvider();
        this.disposable = new CompositeDisposable();
    }

    void getGroup(final Long groupId) {
        if (groupsRepository != null) {
            disposable.add(groupsRepository.getGroup(groupId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(this::onGroupReceived, this::onGroupError));
        }
    }

    private void onGroupReceived(Group group) {
        if (groupView != null) {
            groupView.get().bindGroup(group);
            List<GroupHistory> historic = new ArrayList<>(group.getCharges());
            groupView.get().listGroupHistory(historic);
        }
    }

    private void onGroupError(Throwable throwable) {
        System.out.println(throwable);
    }
}
