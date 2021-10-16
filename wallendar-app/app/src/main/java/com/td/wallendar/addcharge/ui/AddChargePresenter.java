package com.td.wallendar.addcharge.ui;

import com.td.wallendar.dtos.response.AddChargeResponse;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.ChargesRepositoryImpl;
import com.td.wallendar.repositories.GroupsRepositoryImpl;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class AddChargePresenter {

    private final AddChargeView view;
    private ChargesRepository chargesRepository = new ChargesRepositoryImpl();
    private GroupsRepository groupsRepository = new GroupsRepositoryImpl();

    private final CompositeDisposable groupsDisposable = new CompositeDisposable();
    private final CompositeDisposable chargeDisposable = new CompositeDisposable();

    private final SchedulerProvider schedulerProvider = new AndroidSchedulerProvider();

    private Long groupId;

    public AddChargePresenter(final AddChargeView view) {
        this.view = view;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void onViewAttached(final Long userId) {
        if (groupsRepository != null) {
            groupsDisposable.add(
                    groupsRepository.getGroupsByUser(userId)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe(this::onGroupsReceived, this::onGroupsError)
            );
        }
    }

    private void onGroupsError(Throwable throwable) {
        view.onGroupsLoadError();
    }

    private void onGroupsReceived(List<Group> groups) {
        view.addGroups(groups);
        if(groupId != null){
            view.setSelectedGroup(groupId);
        }
    }

    public void addCharge(final Charge charge) {
        if (chargesRepository != null) {
            chargeDisposable.add(
                    chargesRepository.addCharge(charge)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe(this::onChargeAdded, this::onChargeAddedError)
            );
        }
    }

    private void onChargeAdded(AddChargeResponse addChargeResponse) {
        view.chargeAddedOk();
    }

    private void onChargeAddedError(Throwable throwable) {
        // TODO
        System.out.println(throwable);
        view.chargeError();
    }

}
