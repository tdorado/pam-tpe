package com.td.wallendar.groupbalance.ui

import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.models.Debt
import com.td.wallendar.models.Group
import com.td.wallendar.repositories.interfaces.EventsRepository
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import java.util.*

class GroupBalancePresenter(
        groupBalanceView: GroupBalanceView,
        private val groupsRepository: GroupsRepository,
        private val eventsRepository: EventsRepository,
        private val schedulerProvider: SchedulerProvider,
        private val isEvent: Boolean,
) {
    private val groupBalanceView: WeakReference<GroupBalanceView?> = WeakReference(groupBalanceView)
    private val disposable: CompositeDisposable = CompositeDisposable()
    private var debtToSettleUp: Debt? = null
    fun onViewAttached(groupId: Long) {
        val repository = { if (isEvent) eventsRepository.getEvent(groupId) else groupsRepository.getGroup(groupId) }

        disposable.add(repository()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ group: Group -> onGroupReceived(group) }) { throwable: Throwable -> onGroupFailed(throwable) })
    }

    fun onViewDetached() {
        disposable.dispose()
    }

    fun onSettleUpDebtClicked(debt: Debt) {
        val addPaymentRequest = AddPaymentRequest(debt.from.id,
                debt.to.id,
                debt.amount)
        debtToSettleUp = debt
        val repository = {
            if (isEvent) eventsRepository.addPayment(debt.groupId, addPaymentRequest)
            else groupsRepository.addPayment(debt.groupId, addPaymentRequest)
        }

        disposable.add(repository()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ onSettleUpDebtSuccessful() }) { throwable: Throwable -> onSettleUpDebtFailed(throwable) })
    }

    private fun onGroupReceived(group: Group) {
        val debts = group.debts
        val debtsFiltered: MutableList<Debt> = ArrayList()
        for (debt in debts) {
            if (debt.amount > 0) {
                debtsFiltered.add(debt)
            }
        }
        groupBalanceView.get()?.bind(debtsFiltered)
    }

    private fun onGroupFailed(throwable: Throwable) {
        groupBalanceView.get()?.failedToLoadDebts()
    }

    private fun onSettleUpDebtSuccessful() {
        if (debtToSettleUp != null) {
            groupBalanceView.get()?.onSettleUpPaymentDone(debtToSettleUp!!)
            debtToSettleUp = null
        }
    }

    private fun onSettleUpDebtFailed(throwable: Throwable?) {
        groupBalanceView.get()?.failedToSettleUpDebt()
    }

}