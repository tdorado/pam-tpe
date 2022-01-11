package com.td.wallendar.addcharge.ui

import com.td.wallendar.dtos.request.AddChargeRequest
import com.td.wallendar.models.Charge
import com.td.wallendar.models.Group
import com.td.wallendar.repositories.interfaces.ChargesRepository
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

class AddChargePresenter(
        addChargeView: AddChargeView,
        chargesRepository: ChargesRepository,
        groupsRepository: GroupsRepository,
        schedulerProvider: SchedulerProvider,
) {
    private val addChargeView: WeakReference<AddChargeView>?
    private val chargesRepository: ChargesRepository
    private val groupsRepository: GroupsRepository
    private val schedulerProvider: SchedulerProvider
    private val disposable: CompositeDisposable

    init {
        this.addChargeView = WeakReference(addChargeView)
        this.chargesRepository = chargesRepository
        this.groupsRepository = groupsRepository
        this.schedulerProvider = schedulerProvider
        disposable = CompositeDisposable()
    }

    private var groupId: Long? = null

    fun setGroupId(groupId: Long) {
        this.groupId = groupId
    }

    fun onViewAttached(userId: Long) {
        groupsRepository.getGroupsByUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ groups: MutableList<Group> -> onGroupsReceived(groups) }) { throwable: Throwable -> onGroupsError(throwable) }.let { disposable?.add(it) }
    }

    fun onViewDetached() {
        disposable.dispose()
    }

    private fun onGroupsError(throwable: Throwable) {
        addChargeView?.get()?.onGroupsLoadError()
    }

    private fun onGroupsReceived(groups: MutableList<Group>) {
        addChargeView?.get()?.onGroupsLoadOk(groups)
        if (groupId != null) {
            addChargeView?.get()?.setSelectedGroup(groupId!!)
        }
    }

    fun addCharge(groupId: Long, addChargeRequest: AddChargeRequest) {
        chargesRepository.addCharge(groupId, addChargeRequest)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ charge: Charge -> onChargeAdded(charge) }) { throwable: Throwable -> onChargeAddedError(throwable) }.let { disposable.add(it) }
    }

    private fun onChargeAdded(charge: Charge) {
        addChargeView?.get()?.chargeAddedOk(charge)
    }

    private fun onChargeAddedError(throwable: Throwable) {
        addChargeView?.get()?.chargeError()
    }

}