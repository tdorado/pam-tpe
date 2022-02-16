package com.td.wallendar.addgroup.ui

import com.td.wallendar.dtos.request.AddEventRequest
import com.td.wallendar.dtos.request.AddGroupRequest
import com.td.wallendar.models.Event
import com.td.wallendar.models.Group
import com.td.wallendar.repositories.interfaces.EventsRepository
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class AddGroupPresenter(
        addGroupView: AddGroupView,
        private val groupsRepository: GroupsRepository,
        private val eventsRepository: EventsRepository,
        private val schedulerProvider: SchedulerProvider,
        private val isEvent: Boolean,
) {
    private val view: WeakReference<AddGroupView> = WeakReference(addGroupView)
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun createGroup(groupTitle: String, ownerId: Long) {
        disposable.add(groupsRepository.createGroup(AddGroupRequest(groupTitle, ownerId))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ group: Group -> onGroupCreatedSuccessfully(group) }) { throwable: Throwable -> onGroupCreatedWithError(throwable) })
    }

    fun createEvent(eventTitle: String, ownerId: Long, date: Calendar) {
        val dateFormatted = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date.time)
        disposable.add(eventsRepository.createEvent(AddEventRequest(eventTitle, ownerId, dateFormatted))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ event: Event -> onGroupCreatedSuccessfully(event) }) { throwable: Throwable -> onGroupCreatedWithError(throwable) })
    }

    private fun onGroupCreatedSuccessfully(group: Group) {
        view.get()?.onGroupCreated(group)
    }

    private fun onGroupCreatedWithError(throwable: Throwable) {
        view.get()?.onGroupCreatedWithErrors()
    }

    fun onViewDetached() {
        disposable.dispose()
    }

}