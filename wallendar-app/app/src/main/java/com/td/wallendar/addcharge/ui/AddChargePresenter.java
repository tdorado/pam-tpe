package com.td.wallendar.addcharge.ui;

import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class AddChargePresenter {

    private final WeakReference<AddChargeView> addChargeView;
    private final ChargesRepository chargesRepository;
    private final GroupsRepository groupsRepository;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable disposable;

    private Long groupId;

    public AddChargePresenter(final AddChargeView addChargeView,
                              final ChargesRepository chargesRepository,
                              final GroupsRepository groupsRepository,
                              final SchedulerProvider schedulerProvider) {
        this.addChargeView = new WeakReference<>(addChargeView);
        this.chargesRepository = chargesRepository;
        this.groupsRepository = groupsRepository;
        this.schedulerProvider = schedulerProvider;
        this.disposable = new CompositeDisposable();
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    public void onViewAttached(final long userId) {
        disposable.add(groupsRepository.getGroupsByUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onGroupsReceived, this::onGroupsError));
    }

    public void onViewDetached() {
        disposable.dispose();
    }

    private void onGroupsError(Throwable throwable) {
        addChargeView.get().onGroupsLoadError();
    }

    private void onGroupsReceived(List<Group> groups) {
        addChargeView.get().onGroupsLoadOk(groups);
        if (groupId != null) {
            addChargeView.get().setSelectedGroup(groupId);
        }
    }

    public void addCharge(final long groupId, final AddChargeRequest addChargeRequest) {
        disposable.add(chargesRepository.addCharge(groupId, addChargeRequest)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onChargeAdded, this::onChargeAddedError));
    }

    private void onChargeAdded(Charge charge) {
        addChargeView.get().chargeAddedOk(charge);
    }

    private void onChargeAddedError(Throwable throwable) {
        addChargeView.get().chargeError();
    }
}
