package com.td.wallendar.debtdetail.ui

import com.td.wallendar.models.DebtDetail
import com.td.wallendar.repositories.interfaces.DebtsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

class DebtDetailPresenter(debtDetailView: DebtDetailView,
                          private val debtsRepository: DebtsRepository,
                          private val schedulerProvider: SchedulerProvider) {
    private val debtDetailsView: WeakReference<DebtDetailView?> = WeakReference(debtDetailView)
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun onViewAttached(debtId: Long) {
        disposable.add(debtsRepository.getDebtDetail(debtId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ details: MutableList<DebtDetail> -> onDetailsReceived(details) }) { throwable: Throwable -> onDetailsFailed(throwable) })
    }

    private fun onDetailsFailed(throwable: Throwable) {
        debtDetailsView.get()?.failedToLoadDetails()
    }

    private fun onDetailsReceived(details: List<DebtDetail>) {
        debtDetailsView.get()?.bind(details)
    }

    fun onViewDetached() {
        disposable.dispose()
    }

    fun onViewDestroyed() {
        disposable.dispose()
    }
}