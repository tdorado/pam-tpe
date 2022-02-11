package com.td.wallendar.addmembers.ui

import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.models.Group
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import java.util.*

class AddMembersPresenter(addMembersView: AddMembersView, private val groupsRepository: GroupsRepository, private val schedulerProvider: SchedulerProvider) {
    private val view: WeakReference<AddMembersView> = WeakReference(addMembersView)
    private val disposable: CompositeDisposable = CompositeDisposable()
    fun submitMembers(groupId: Long, emailMembers: MutableList<String>) {
        disposable.add(groupsRepository.addMembers(groupId, AddMembersRequest(HashSet(emailMembers)))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ group: Group -> onMembersAddedSuccessfully(group) }) { throwable: Throwable -> onMembersAddedWithError(throwable) })
    }

    fun onViewDetached() {
        disposable.dispose()
    }

    private fun onMembersAddedWithError(throwable: Throwable) {
        view.get()?.onMembersAddedWithError()
    }

    private fun onMembersAddedSuccessfully(group: Group) {
        view.get()?.onMembersAddedSuccessfully(group.id)
    }

}