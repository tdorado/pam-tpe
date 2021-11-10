package com.td.wallendar.home.ui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static io.reactivex.Single.error;
import static io.reactivex.Single.just;

import com.td.wallendar.TestSchedulerProvider;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class HomePresenterTest {

    private HomeView homeView;
    private GroupsRepository groupsRepository;
    private DebtsRepository debtsRepository;
    private ApplicationUsersRepository applicationUsersRepository;
    private SchedulerProvider schedulerProvider = new TestSchedulerProvider();

    private HomePresenter homePresenter;

    private final Group fakeGroup = new Group(1L, "group title", new ApplicationUser(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    private List<Group> groups = new ArrayList<>();

    private ApplicationUser tdallas = new ApplicationUser(1L, "tdallas@itba.edu.ar", "Tomas", "Dallas", "");
    private ApplicationUser tdorado = new ApplicationUser(1L, "tdorado@itba.edu.ar", "Tomas", "Dorado", "");

    private final Debt fakeDebt = new Debt(
            1L,
            tdallas,
            tdorado,
            25d,
            1L
    );
    private List<Debt> debts = new ArrayList<>();


    @Before
    public void setup() {
        groups.add(fakeGroup);
        debts.add(fakeDebt);

        homeView = mock(HomeView.class);
        groupsRepository = mock(GroupsRepository.class);
        debtsRepository = mock(DebtsRepository.class);
        applicationUsersRepository = mock(ApplicationUsersRepository.class);

        homePresenter = new HomePresenter(homeView, groupsRepository, debtsRepository, applicationUsersRepository, schedulerProvider);
    }

    @Test
    public void whenViewIsAttachedThenGroupsShouldBeFetchedAndLoad_Ok() {
        when(groupsRepository.getGroupsByUser(1L)).thenReturn(just(groups));
        when(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(just(Collections.emptyList()));
        when(applicationUsersRepository.getUser(1L)).thenReturn(just(tdallas));

        homePresenter.onViewAttached(1L);

        verify(homeView).bindGroups(groups);
    }

    @Test
    public void whenViewIsAttachedThenDebtsForUserShouldBeFetchedAndLoad_Ok() {
        when(groupsRepository.getGroupsByUser(1L)).thenReturn(just(Collections.emptyList()));
        when(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(just(debts));
        when(applicationUsersRepository.getUser(1L)).thenReturn(just(tdallas));

        homePresenter.onViewAttached(1L);

        verify(homeView).bindDebts(debts);
    }

    @Test
    public void whenViewIsAttachedThenUserShouldBeFetchedAndLoad_Ok() {
        when(groupsRepository.getGroupsByUser(1L)).thenReturn(just(Collections.emptyList()));
        when(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(just(Collections.emptyList()));
        when(applicationUsersRepository.getUser(1L)).thenReturn(just(tdallas));

        homePresenter.onViewAttached(1L);

        verify(homeView).bindProfile(tdallas);
    }

    @Test
    public void whenViewIsAttachedThenGroupsAndUserAndDebtsShouldBeFetchedAndLoaded_Ok() {
        when(groupsRepository.getGroupsByUser(1L)).thenReturn(just(groups));
        when(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(just(debts));
        when(applicationUsersRepository.getUser(1L)).thenReturn(just(tdallas));

        homePresenter.onViewAttached(1L);

        verify(homeView).bindProfile(tdallas);
        verify(homeView).bindGroups(groups);
        verify(homeView).bindDebts(debts);
    }

    // Errors
    @Test
    public void whenViewIsAttachedThenGroupsShouldBeFetchedAndLoad_WithErrors() {
        when(groupsRepository.getGroupsByUser(1L)).thenReturn(error(new Exception("Fake Exception")));
        when(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(just(debts));
        when(applicationUsersRepository.getUser(1L)).thenReturn(just(tdallas));

        homePresenter.onViewAttached(1L);

        verify(homeView, times(1)).errorGettingGroups();

        verify(homeView, times(1)).bindDebts(debts);
        verify(homeView, times(1)).bindProfile(tdallas);
    }

    @Test
    public void whenViewIsAttachedThenDebtsForUserShouldBeFetchedAndLoad_WithErrors() {
        when(groupsRepository.getGroupsByUser(1L)).thenReturn(just(groups));
        when(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(error(new Exception("Fake Exception")));
        when(applicationUsersRepository.getUser(1L)).thenReturn(just(tdallas));

        homePresenter.onViewAttached(1L);

        verify(homeView).errorGettingBalances();
        verify(homeView, times(1)).bindProfile(tdallas);
        verify(homeView, times(1)).bindGroups(groups);
    }

    @Test
    public void whenViewIsAttachedThenUserShouldBeFetchedAndLoad_WithErrors() {
        when(groupsRepository.getGroupsByUser(1L)).thenReturn(just(Collections.emptyList()));
        when(debtsRepository.getTotalDebtsByUserId(1L)).thenReturn(just(Collections.emptyList()));
        when(applicationUsersRepository.getUser(1L)).thenReturn(error(new Exception("Fake exception")));

        homePresenter.onViewAttached(1L);

        verify(homeView).errorGettingUser();
        verify(homeView, times(1)).bindGroups(Collections.emptyList());
        verify(homeView, times(1)).bindDebts(Collections.emptyList());
    }


}