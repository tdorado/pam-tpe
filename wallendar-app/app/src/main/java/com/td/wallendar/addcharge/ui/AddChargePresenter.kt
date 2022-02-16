package com.td.wallendar.addcharge.ui

import com.td.wallendar.dtos.request.AddChargeRequest
import com.td.wallendar.models.Charge
import com.td.wallendar.models.Event
import com.td.wallendar.models.Group
import com.td.wallendar.repositories.interfaces.ChargesRepository
import com.td.wallendar.repositories.interfaces.EventsRepository
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

class AddChargePresenter(
        addChargeView: AddChargeView,
        chargesRepository: ChargesRepository,
        groupsRepository: GroupsRepository,
        private val eventsRepository: EventsRepository,
        schedulerProvider: SchedulerProvider,
        private val isEvent: Boolean,
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
        val repository = { if (isEvent) eventsRepository.getEventsByUser(userId) else groupsRepository.getGroupsByUser(userId) }

        if (isEvent) {
            disposable.add(eventsRepository.getEventsByUser(userId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ events: MutableList<Event> -> onEventsReceived(events) }) { throwable: Throwable -> onGroupsError(throwable) })
        } else {
            disposable.add(groupsRepository.getGroupsByUser(userId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ group: MutableList<Group> -> onGroupsReceived(group) }) { throwable: Throwable -> onGroupsError(throwable) })
        }

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

    private fun onEventsReceived(events: MutableList<Event>) {
        addChargeView?.get()?.onGroupsLoadOk(events.toMutableList())
        if (groupId != null) {
            addChargeView?.get()?.setSelectedGroup(groupId!!)
        }
    }

    fun addCharge(groupId: Long, addChargeRequest: AddChargeRequest) {
        chargesRepository.addCharge(groupId, addChargeRequest, isEvent)
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