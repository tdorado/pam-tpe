package com.td.wallendar.addcharge.ui;

import com.td.wallendar.dtos.response.AddChargeResponse;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.AndroidSchedulerProvider;
import com.td.wallendar.utils.SchedulerProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class AddChargePresenter {

    private final AddChargeView view;
    private ChargesRepository chargesRepository;
    private GroupsRepository groupsRepository;

    private final CompositeDisposable groupsDisposable = new CompositeDisposable();
    private final CompositeDisposable chargeDisposable = new CompositeDisposable();

    private final SchedulerProvider schedulerProvider = new AndroidSchedulerProvider();

    public AddChargePresenter(final AddChargeView view) {
        this.view = view;
    }

    public void onViewAttached(final Long userId) {
        if (groupsRepository != null) {
            groupsDisposable.add(
                    groupsRepository.getGroupsByUser(userId)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::onGroupsReceived, this::onGroupsError)
            );
        }
    }

    private void onGroupsError(Throwable throwable) {
        view.onGroupsLoadError();
    }

    private void onGroupsReceived(List<Group> groups) {
        view.addGroups(groups);
    }

    public void addCharge(final Charge charge) {
        if (chargesRepository != null) {
            chargeDisposable.add(
                    chargesRepository.addCharge(charge)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(AndroidSchedulers.mainThread())
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
