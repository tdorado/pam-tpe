package com.td.wallendar.addmembers.ui

import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.repositories.interfaces.EventsRepository
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

class AddMembersPresenter(
        addMembersView: AddMembersView,
        private val groupsRepository: GroupsRepository,
        private val eventsRepository: EventsRepository,
        private val schedulerProvider: SchedulerProvider,
        private val isEvent: Boolean,
) {
    private val view: WeakReference<AddMembersView> = WeakReference(addMembersView)
    private val disposable: CompositeDisposable = CompositeDisposable()
    fun submitMembers(groupId: Long, emailMembers: MutableList<String>) {
        val repositoryCall = {
            if (isEvent) {
                eventsRepository.addMembers(groupId, AddMembersRequest(HashSet(emailMembers)))
            } else {
                groupsRepository.addMembers(groupId, AddMembersRequest(HashSet(emailMembers)))
            }
        }
        disposable.add(repositoryCall()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ onMembersAddedSuccessfully() }) { throwable: Throwable -> onMembersAddedWithError(throwable) })
    }

    fun onViewDetached() {
        disposable.dispose()
    }

    private fun onMembersAddedWithError(throwable: Throwable) {
        view.get()?.onMembersAddedWithError()
    }

    private fun onMembersAddedSuccessfully() {
        view.get()?.onMembersAddedSuccessfully()
    }

}