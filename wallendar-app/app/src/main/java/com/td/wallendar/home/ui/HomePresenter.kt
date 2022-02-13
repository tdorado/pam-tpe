package com.td.wallendar.home.ui

import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.models.ApplicationUser
import com.td.wallendar.models.Debt
import com.td.wallendar.models.Group
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository
import com.td.wallendar.repositories.interfaces.DebtsRepository
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

class HomePresenter(
        homeView: HomeView, private val groupsRepository: GroupsRepository,
        private val debtsRepository: DebtsRepository,
        private val applicationUsersRepository: ApplicationUsersRepository,
        private val schedulerProvider: SchedulerProvider
) {
    private val homeView: WeakReference<HomeView> = WeakReference(homeView)
    private val disposable: CompositeDisposable = CompositeDisposable()
    private var debtToSettleUp: Debt? = null
    fun onGroupsClicked() {
        if (homeView.get() != null) {
            homeView.get()?.showGroups()
        }
    }

    fun onBalancesClicked() {
        if (homeView.get() != null) {
            homeView.get()?.showBalances()
        }
    }

    fun onProfileClicked() {
        if (homeView.get() != null) {
            homeView.get()?.showProfile()
        }
    }

    fun onViewAttached(userId: Long) {
        disposable.add(groupsRepository.getGroupsByUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ groups: MutableList<Group> -> onGroupsReceived(groups) }) { throwable: Throwable -> onGroupsError(throwable) })
        disposable.add(debtsRepository.getTotalDebtsByUserId(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ debts: MutableList<Debt> -> onDebtsReceived(debts) }) { throwable: Throwable -> onDebtsError(throwable) })
        disposable.add(applicationUsersRepository.getUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ applicationUser: ApplicationUser -> onLoggedUserReceived(applicationUser) }) { throwable: Throwable -> onLoggedUserError(throwable) })
    }

    fun onSettleUpDebtClicked(debt: Debt) {
        val addPaymentRequest = AddPaymentRequest(debt.from.id,
                debt.to.id,
                debt.amount)
        debtToSettleUp = debt
        disposable.add(groupsRepository.addPayment(debt.groupId, addPaymentRequest)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ onPaymentReceived() }) { throwable: Throwable -> onPaymentError(throwable) })
    }

    private fun onGroupsReceived(groups: MutableList<Group>) {
        if (homeView.get() != null) {
            homeView.get()?.bindGroups(groups)
        }
    }

    private fun onGroupsError(throwable: Throwable) {
        homeView.get()?.errorGettingGroups()
    }

    private fun onDebtsReceived(debts: MutableList<Debt>) {
        if (homeView.get() != null) {
            homeView.get()?.bindDebts(debts)
        }
    }

    private fun onDebtsError(throwable: Throwable) {
        homeView.get()?.errorGettingBalances()
    }

    private fun onLoggedUserReceived(applicationUser: ApplicationUser) {
        if (homeView.get() != null) {
            homeView.get()?.bindProfile(applicationUser)
        }
    }

    private fun onLoggedUserError(throwable: Throwable) {
        homeView.get()?.errorGettingUser()
    }

    private fun onPaymentReceived() {
        if (homeView.get() != null) {
            if (debtToSettleUp != null) {
                homeView.get()?.removeDebt(debtToSettleUp!!)
                debtToSettleUp = null
            }
        }
    }

    private fun onPaymentError(throwable: Throwable) {
        homeView.get()?.errorPayingDebt()
    }

    fun onViewDetached() {
        // solo en esta activity hacemos el clear, porque como el home es singleton a veces se va a necesitar volver a agregar cosas al disposable
        disposable.clear()
    }

    fun onViewDestroyed() {
        // cuando se destruye ahi si se hace el dispose
        disposable.dispose()
    }

}