package com.td.wallendar.group.ui;

import com.td.wallendar.models.Group;
import com.td.wallendar.models.GroupHistory;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import io.reactivex.disposables.CompositeDisposable;

public class GroupPresenter {

    private static final Comparator<GroupHistory> groupHistoryComparator = (gh1, gh2) -> gh2.getDate().compareTo(gh1.getDate());

    private final WeakReference<GroupView> groupView;
    private final GroupsRepository groupsRepository;
    private final CompositeDisposable disposable;
    private final SchedulerProvider schedulerProvider;

    public GroupPresenter(final GroupView groupView,
                          final GroupsRepository groupsRepository,
                          final SchedulerProvider schedulerProvider) {
        this.groupView = new WeakReference<>(groupView);
        this.groupsRepository = groupsRepository;
        this.schedulerProvider = schedulerProvider;
        this.disposable = new CompositeDisposable();
    }

    public void onViewAttached(final Long groupId) {
        if (groupsRepository != null) {
            disposable.add(groupsRepository.getGroup(groupId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(this::onGroupReceived, this::onGroupError));
        }
    }

    private void onGroupReceived(Group group) {
        groupView.get().bindGroup(group);

        Set<GroupHistory> historic = new TreeSet<>(groupHistoryComparator);
        historic.addAll(group.getCharges());
        historic.addAll(group.getPayments());

        groupView.get().bindGroupHistory(new ArrayList<>(historic));
    }

    private void onGroupError(Throwable throwable) {
        System.out.println(throwable);
    }

    public void onViewDetached() {
        disposable.dispose();
    }
}
