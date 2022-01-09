package com.td.wallendar.group.ui

import com.td.wallendar.TestSchedulerProvider
import com.td.wallendar.models.Group
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.util.HashSet

class GroupPresenterTest {
    private var groupView: GroupView? = null
    private var groupsRepository: GroupsRepository? = null
    private val schedulerProvider: SchedulerProvider? = TestSchedulerProvider()
    private var presenter: GroupPresenter? = null
    private val fakeGroup: Group? = Group(1L, "group title", ApplicationUser(), HashSet(), HashSet(), HashSet(), HashSet())
    @Before
    fun setup() {
        groupView = Mockito.mock<GroupView?>(GroupView::class.java)
        groupsRepository = Mockito.mock<GroupsRepository?>(GroupsRepository::class.java)
        presenter = GroupPresenter(groupView, groupsRepository, schedulerProvider)
    }

    @Test
    fun givenAnUserIdAndTheViewAttached_ThenTheViewShouldShowTheGroupInformation_Ok() {
        Mockito.`when`<Single<Group?>?>(groupsRepository.getGroup(1L)).thenReturn(Single.just(fakeGroup))
        presenter.onViewAttached(1L)
        Mockito.verify<GroupView?>(groupView).bindGroup(fakeGroup)
        Mockito.verify<GroupView?>(groupView).bindGroupHistory(emptyList())
    }

    @Test
    fun givenAnUserIdAndTheViewAttached_ThenTheViewShouldShowTheGroupInformation_WithError() {
        Mockito.`when`<Single<Group?>?>(groupsRepository.getGroup(1L)).thenReturn(Single.error<Group?>(Exception("Error while fetching group")))
        presenter.onViewAttached(1L)
        Mockito.verify<GroupView?>(groupView).onGroupLoadError()
    }

    @Test
    fun whenViewIsDetached_ThenDisposableShouldBeCleared() {
        presenter.onViewDetached()
        Assert.assertEquals(0, presenter.getDisposable().size().toLong())
    }

    @Test
    fun whenViewIsDestroyed_ThenDisposableShouldBeDisposed() {
        presenter.onViewDetached()
        Assert.assertFalse(presenter.getDisposable().isDisposed)
    }
}