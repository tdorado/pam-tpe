package com.td.wallendar.groupmembers.ui

import com.td.wallendar.models.Group
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import java.util.*

class GroupMembersPresenter(groupMembersView: GroupMembersView, private val groupsRepository: GroupsRepository, private val schedulerProvider: SchedulerProvider) {
    private val groupMembersView: WeakReference<GroupMembersView> = WeakReference(groupMembersView)
    private val disposable: CompositeDisposable = CompositeDisposable()
    fun onViewAttached(groupId: Long) {
        disposable.add(groupsRepository.getGroup(groupId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ group: Group -> onGroupReceived(group) }) { throwable: Throwable -> onGroupFailed(throwable) })
    }

    fun onViewDetached() {
        disposable.dispose()
    }

    private fun onGroupReceived(group: Group) {
        groupMembersView.get()?.bind(ArrayList(group.members))
    }

    private fun onGroupFailed(throwable: Throwable) {
        groupMembersView.get()?.failedToLoadMembers()
    }

}