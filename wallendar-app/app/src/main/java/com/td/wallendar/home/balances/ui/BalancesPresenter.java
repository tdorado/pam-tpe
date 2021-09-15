package com.td.wallendar.home.balances.ui;

import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BalancesPresenter {
    private final WeakReference<BalancesView> balancesView;
    private final DebtsRepository debtsRepository;

    private CompositeDisposable disposable;
    private SchedulerProvider schedulerProvider;

    public BalancesPresenter(final WeakReference<BalancesView> balancesView,
                           final DebtsRepository debtsRepository) {
        this.balancesView = balancesView;
        this.debtsRepository = debtsRepository;
        this.schedulerProvider = new AndroidSchedulerProvider();
        this.disposable = new CompositeDisposable();
    }

    void getBalances(final Long userId) {
        if (debtsRepository != null) {
            disposable.add(debtsRepository.getTotalDebtsByUserId(userId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(this::onDebtsReceived, this::onDebtsError));
        }
    }

    private void onDebtsReceived(List<Debt> debts) {
        if (balancesView != null) {
            balancesView.get().listBalances(debts);
        }
    }

    private void onDebtsError(Throwable throwable) {
        System.out.println(throwable);
    }
}
