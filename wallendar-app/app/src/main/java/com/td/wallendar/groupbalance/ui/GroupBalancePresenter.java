package com.td.wallendar.groupbalance.ui;

import com.td.wallendar.dtos.request.AddPaymentRequest;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;

public class GroupBalancePresenter {
    private final WeakReference<GroupBalanceView> groupBalanceView;
    private final GroupsRepository groupsRepository;
    private final CompositeDisposable disposable;
    private final SchedulerProvider schedulerProvider;

    private Debt debtToSettleUp = null;

    public GroupBalancePresenter(GroupBalanceView groupBalanceView, GroupsRepository groupsRepository, SchedulerProvider schedulerProvider) {
        this.groupBalanceView = new WeakReference<>(groupBalanceView);
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

    public void onSettleUpDebtClicked(Debt debt) {
        AddPaymentRequest addPaymentRequest = new AddPaymentRequest(debt.getFrom().getId(),
                debt.getTo().getId(),
                debt.getAmount());
        debtToSettleUp = debt;
        disposable.add(groupsRepository.addPayment(debt.getGroupId(), addPaymentRequest)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onSettleUpDebtSuccessful, this::onSettleUpDebtFailed));
    }

    private void onGroupReceived(Group group) {
        Set<Debt> debts = group.getDebts();
        List<Debt> debtsFiltered = new ArrayList<>();
        for (Debt debt : debts) {
            if (debt.getAmount() > 0) {
                debtsFiltered.add(debt);
            }
        }
        groupBalanceView.get().bind(debtsFiltered);
    }

    private void onGroupFailed(Throwable throwable) {
        groupBalanceView.get().failedToLoadDebts();
    }

    private void onSettleUpDebtSuccessful(Group group) {
        if (debtToSettleUp != null) {
            groupBalanceView.get().onSettleUpPaymentDone(debtToSettleUp);
            debtToSettleUp = null;
        }
    }

    private void onSettleUpDebtFailed(Throwable throwable) {
        groupBalanceView.get().failedToSettleUpDebt();
    }

}
