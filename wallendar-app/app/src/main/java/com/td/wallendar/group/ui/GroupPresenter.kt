package com.td.wallendar.group.ui

import androidx.annotation.VisibleForTesting
import com.td.wallendar.models.Group
import com.td.wallendar.models.GroupHistory
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import java.util.*

class GroupPresenter(
        groupView: GroupView,
        private val groupsRepository: GroupsRepository,
        private val schedulerProvider: SchedulerProvider
) {
    private val groupView: WeakReference<GroupView> = WeakReference(groupView)
    private val disposable: CompositeDisposable = CompositeDisposable()
    fun onViewAttached(groupId: Long) {
        disposable.add(groupsRepository.getGroup(groupId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ group: Group -> onGroupReceived(group) }) { throwable: Throwable -> onGroupError(throwable) })
    }

    private fun onGroupReceived(group: Group) {
        groupView.get()?.bindGroup(group)
        val historic: MutableSet<GroupHistory> = TreeSet(groupHistoryComparator)
        historic.addAll(group.charges)
        historic.addAll(group.payments)
        groupView.get()?.bindGroupHistory(ArrayList(historic))
    }

    private fun onGroupError(throwable: Throwable) {
        groupView.get()?.onGroupLoadError()
    }

    fun onViewDetached() {
        disposable.clear()
    }

    fun onViewDestroyed() {
        disposable.dispose()
    }

    @VisibleForTesting
    fun getDisposable(): CompositeDisposable {
        return disposable
    }

    companion object {
        private val groupHistoryComparator: Comparator<GroupHistory> = Comparator { gh1: GroupHistory, gh2: GroupHistory -> gh2.getDate().compareTo(gh1.getDate()) }
    }

}