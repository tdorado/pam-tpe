package com.td.wallendar.home.ui

import com.td.wallendar.TestSchedulerProvider
import com.td.wallendar.models.Group
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.util.ArrayList
import java.util.HashSet

class HomePresenterTest {
    private var homeView: HomeView? = null
    private var groupsRepository: GroupsRepository? = null
    private var debtsRepository: DebtsRepository? = null
    private var applicationUsersRepository: ApplicationUsersRepository? = null
    private val schedulerProvider: SchedulerProvider? = TestSchedulerProvider()
    private var homePresenter: HomePresenter? = null
    private val fakeGroup: Group? = Group(1L, "group title", ApplicationUser(), HashSet(), HashSet(), HashSet(), HashSet())
    private val groups: MutableList<Group?>? = ArrayList()
    private val tdallas: ApplicationUser? = ApplicationUser(1L, "tdallas@itba.edu.ar", "Tomas", "Dallas", "")
    private val tdorado: ApplicationUser? = ApplicationUser(1L, "tdorado@itba.edu.ar", "Tomas", "Dorado", "")
    private val fakeDebt: Debt? = Debt(
            1L,
            tdallas,
            tdorado,
            25.0,
            1L
    )
    private val debts: MutableList<Debt?>? = ArrayList<Debt?>()
    @Before
    fun setup() {
        groups.add(fakeGroup)
        debts.add(fakeDebt)
        homeView = Mockito.mock<HomeView?>(HomeView::class.java)
        groupsRepository = Mockito.mock<GroupsRepository?>(GroupsRepository::class.java)
        debtsRepository = Mockito.mock<DebtsRepository?>(DebtsRepository::class.java)
        applicationUsersRepository = Mockito.mock<ApplicationUsersRepository?>(ApplicationUsersRepository::class.java)
        homePresenter = HomePresenter(homeView, groupsRepository, debtsRepository, applicationUsersRepository, schedulerProvider)
    }

    @Test
    fun whenViewIsAttachedThenGroupsShouldBeFetchedAndLoad_Ok() {
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.just(groups))
        Mockito.`when`<Single<MutableList<Debt?>?>?>(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(Single.just<MutableList<Debt?>?>(emptyList()))
        Mockito.`when`<Single<ApplicationUser?>?>(applicationUsersRepository.getUser(1L)).thenReturn(Single.just<ApplicationUser?>(tdallas))
        homePresenter.onViewAttached(1L)
        Mockito.verify<HomeView?>(homeView).bindGroups(groups)
    }

    @Test
    fun whenViewIsAttachedThenDebtsForUserShouldBeFetchedAndLoad_Ok() {
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.just<MutableList<Group?>?>(emptyList()))
        Mockito.`when`<Single<MutableList<Debt?>?>?>(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(Single.just<MutableList<Debt?>?>(debts))
        Mockito.`when`<Single<ApplicationUser?>?>(applicationUsersRepository.getUser(1L)).thenReturn(Single.just<ApplicationUser?>(tdallas))
        homePresenter.onViewAttached(1L)
        Mockito.verify<HomeView?>(homeView).bindDebts(debts)
    }

    @Test
    fun whenViewIsAttachedThenUserShouldBeFetchedAndLoad_Ok() {
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.just<MutableList<Group?>?>(emptyList()))
        Mockito.`when`<Single<MutableList<Debt?>?>?>(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(Single.just<MutableList<Debt?>?>(emptyList()))
        Mockito.`when`<Single<ApplicationUser?>?>(applicationUsersRepository.getUser(1L)).thenReturn(Single.just<ApplicationUser?>(tdallas))
        homePresenter.onViewAttached(1L)
        Mockito.verify<HomeView?>(homeView).bindProfile(tdallas)
    }

    @Test
    fun whenViewIsAttachedThenGroupsAndUserAndDebtsShouldBeFetchedAndLoaded_Ok() {
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.just(groups))
        Mockito.`when`<Single<MutableList<Debt?>?>?>(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(Single.just<MutableList<Debt?>?>(debts))
        Mockito.`when`<Single<ApplicationUser?>?>(applicationUsersRepository.getUser(1L)).thenReturn(Single.just<ApplicationUser?>(tdallas))
        homePresenter.onViewAttached(1L)
        Mockito.verify<HomeView?>(homeView).bindProfile(tdallas)
        Mockito.verify<HomeView?>(homeView).bindGroups(groups)
        Mockito.verify<HomeView?>(homeView).bindDebts(debts)
    }

    // Errors
    @Test
    fun whenViewIsAttachedThenGroupsShouldBeFetchedAndLoad_WithErrors() {
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.error<MutableList<Group?>?>(Exception("Fake Exception")))
        Mockito.`when`<Single<MutableList<Debt?>?>?>(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(Single.just<MutableList<Debt?>?>(debts))
        Mockito.`when`<Single<ApplicationUser?>?>(applicationUsersRepository.getUser(1L)).thenReturn(Single.just<ApplicationUser?>(tdallas))
        homePresenter.onViewAttached(1L)
        Mockito.verify<HomeView?>(homeView, Mockito.times(1)).errorGettingGroups()
        Mockito.verify<HomeView?>(homeView, Mockito.times(1)).bindDebts(debts)
        Mockito.verify<HomeView?>(homeView, Mockito.times(1)).bindProfile(tdallas)
    }

    @Test
    fun whenViewIsAttachedThenDebtsForUserShouldBeFetchedAndLoad_WithErrors() {
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.just(groups))
        Mockito.`when`<Single<MutableList<Debt?>?>?>(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(Single.error<MutableList<Debt?>?>(Exception("Fake Exception")))
        Mockito.`when`<Single<ApplicationUser?>?>(applicationUsersRepository.getUser(1L)).thenReturn(Single.just<ApplicationUser?>(tdallas))
        homePresenter.onViewAttached(1L)
        Mockito.verify<HomeView?>(homeView).errorGettingBalances()
        Mockito.verify<HomeView?>(homeView, Mockito.times(1)).bindProfile(tdallas)
        Mockito.verify<HomeView?>(homeView, Mockito.times(1)).bindGroups(groups)
    }

    @Test
    fun whenViewIsAttachedThenUserShouldBeFetchedAndLoad_WithErrors() {
        Mockito.`when`<Single<MutableList<Group?>?>?>(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.just<MutableList<Group?>?>(emptyList()))
        Mockito.`when`<Single<MutableList<Debt?>?>?>(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(Single.just<MutableList<Debt?>?>(emptyList()))
        Mockito.`when`<Single<ApplicationUser?>?>(applicationUsersRepository.getUser(1L)).thenReturn(Single.error<ApplicationUser?>(Exception("Fake exception")))
        homePresenter.onViewAttached(1L)
        Mockito.verify<HomeView?>(homeView).errorGettingUser()
        Mockito.verify<HomeView?>(homeView, Mockito.times(1)).bindGroups(emptyList())
        Mockito.verify<HomeView?>(homeView, Mockito.times(1)).bindDebts(emptyList())
    }
}