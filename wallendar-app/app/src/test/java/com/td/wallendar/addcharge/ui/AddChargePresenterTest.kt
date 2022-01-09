package com.td.wallendar.addcharge.ui

import com.td.wallendar.TestSchedulerProvider
import com.td.wallendar.models.Group
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.util.*

class AddChargePresenterTest {
    private var addChargeView: AddChargeView? = null
    private var chargesRepository: ChargesRepository? = null
    private var groupsRepository: GroupsRepository? = null
    private var addChargePresenter: AddChargePresenter? = null
    private val fakeGroup: Group? = Group(1L, "group title", ApplicationUser(), HashSet(), HashSet(), HashSet(), HashSet())
    @Before
    fun setup() {
        addChargeView = Mockito.mock<AddChargeView?>(AddChargeView::class.java)
        chargesRepository = Mockito.mock<ChargesRepository?>(ChargesRepository::class.java)
        groupsRepository = Mockito.mock<GroupsRepository?>(GroupsRepository::class.java)
        val schedulerProvider: SchedulerProvider = TestSchedulerProvider()
        addChargePresenter = AddChargePresenter(addChargeView, chargesRepository, groupsRepository, schedulerProvider)
    }

    @Test
    fun givenTheViewAttachedAndAUserIdThenTheViewShouldListTheGroups_Ok() {
        val fakeGroups: MutableList<Group?> = ArrayList()
        fakeGroups.add(fakeGroup)
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.just(fakeGroups))
        addChargePresenter.onViewAttached(1L)
        Mockito.verify<AddChargeView?>(addChargeView).onGroupsLoadOk(fakeGroups)
        Mockito.verify<AddChargeView?>(addChargeView).setSelectedGroup(1L)
    }

    @Test
    fun givenTheViewAttachedAndAUserIdThenTheViewShouldListTheGroups_WithErrors() {
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.error<MutableList<Group?>?>(Exception("No connection")))
        addChargePresenter.onViewAttached(1L)
        Mockito.verify<AddChargeView?>(addChargeView).onGroupsLoadError()
    }

    @Test
    fun givenAGroupIdAndAChargeWhenAddChargeIsCalledThenOnChargeAddedShouldBeCalled_Ok() {
        val addChargeRequest = AddChargeRequest("Carne", 1L, 23.0)
        val charge = Charge(1L, "Carne", ApplicationUser(), HashSet<ApplicationUser?>(), 23.0, Date())
        Mockito.`when`<Single<Charge?>?>(chargesRepository.addCharge(1L, addChargeRequest)).thenReturn(Single.just<Charge?>(charge))
        addChargePresenter.addCharge(1L, addChargeRequest)
        Mockito.verify<AddChargeView?>(addChargeView).chargeAddedOk(charge)
    }

    @Test
    fun givenAGroupIdAndAChargeWhenAddChargeIsCalledThenOnChargeAddedShouldBeCalled_WithErrors() {
        val addChargeRequest = AddChargeRequest("Carne", 1L, 23.0)
        Mockito.`when`<Single<Charge?>?>(chargesRepository.addCharge(1L, addChargeRequest)).thenReturn(Single.error<Charge?>(Exception("No connection")))
        addChargePresenter.addCharge(1L, addChargeRequest)
        Mockito.verify<AddChargeView?>(addChargeView).chargeError()
    }
}