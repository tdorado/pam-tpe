package com.td.wallendar.group.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static io.reactivex.Single.error;
import static io.reactivex.Single.just;

import com.td.wallendar.TestSchedulerProvider;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

public class GroupPresenterTest {

    private GroupView groupView;
    private GroupsRepository groupsRepository;
    private SchedulerProvider schedulerProvider = new TestSchedulerProvider();

    private GroupPresenter presenter;

    private final Group fakeGroup = new Group(1L, "group title", new ApplicationUser(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

    @Before
    public void setup() {
        groupView = mock(GroupView.class);
        groupsRepository = mock(GroupsRepository.class);

        presenter = new GroupPresenter(groupView, groupsRepository, schedulerProvider);
    }

    @Test
    public void givenAnUserIdAndTheViewAttached_ThenTheViewShouldShowTheGroupInformation_Ok() {
        when(groupsRepository.getGroup(1L)).thenReturn(just(fakeGroup));

        presenter.onViewAttached(1L);

        verify(groupView).bindGroup(fakeGroup);
        verify(groupView).bindGroupHistory(Collections.emptyList());
    }

    @Test
    public void givenAnUserIdAndTheViewAttached_ThenTheViewShouldShowTheGroupInformation_WithError() {
        when(groupsRepository.getGroup(1L)).thenReturn(error(new Exception("Error while fetching group")));

        presenter.onViewAttached(1L);

        verify(groupView).onGroupLoadError();
    }

    @Test
    public void whenViewIsDetached_ThenDisposableShouldBeCleared() {
        presenter.onViewDetached();

        assertEquals(0, presenter.getDisposable().size());
    }

    @Test
    public void whenViewIsDestroyed_ThenDisposableShouldBeDisposed() {
        presenter.onViewDetached();

        assertFalse(presenter.getDisposable().isDisposed());
    }
}