package com.td.wallendar.addcharge.ui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static io.reactivex.Single.just;

import com.td.wallendar.TestSchedulerProvider;
import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;

public class AddChargePresenterTest {

    private AddChargeView addChargeView;
    private ChargesRepository chargesRepository;
    private GroupsRepository groupsRepository;

    private AddChargePresenter addChargePresenter;

    private final Group fakeGroup = new Group(1L, "group title", new ApplicationUser(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

    @Before
    public void setup() {
        addChargeView = mock(AddChargeView.class);
        chargesRepository = mock(ChargesRepository.class);
        groupsRepository = mock(GroupsRepository.class);
        SchedulerProvider schedulerProvider = new TestSchedulerProvider();


        addChargePresenter = new AddChargePresenter(addChargeView, chargesRepository, groupsRepository, schedulerProvider);
    }

    @Test
    public void givenTheViewAttachedAndAUserIdThenTheViewShouldListTheGroups_Ok() {
        List<Group> fakeGroups = new ArrayList<>();
        fakeGroups.add(fakeGroup);

        when(groupsRepository.getGroupsByUser(1L)).thenReturn(just(fakeGroups));

        addChargePresenter.onViewAttached(1L);

        verify(addChargeView).onGroupsLoadOk(fakeGroups);
        verify(addChargeView).setSelectedGroup(1L);
    }

    @Test
    public void givenTheViewAttachedAndAUserIdThenTheViewShouldListTheGroups_WithErrors() {
        when(groupsRepository.getGroupsByUser(1L)).thenReturn(Single.error(new Exception("No connection")));

        addChargePresenter.onViewAttached(1L);

        verify(addChargeView).onGroupsLoadError();
    }

    @Test
    public void givenAGroupIdAndAChargeWhenAddChargeIsCalledThenOnChargeAddedShouldBeCalled_Ok() {
        final AddChargeRequest addChargeRequest = new AddChargeRequest("Carne", 1L, 23d);
        final Charge charge = new Charge(1L, "Carne", new ApplicationUser(), new HashSet<>(), 23d, new Date());

        when(chargesRepository.addCharge(1L, addChargeRequest)).thenReturn(just(charge));

        addChargePresenter.addCharge(1L, addChargeRequest);

        verify(addChargeView).chargeAddedOk(charge);
    }
    @Test
    public void givenAGroupIdAndAChargeWhenAddChargeIsCalledThenOnChargeAddedShouldBeCalled_WithErrors() {
        final AddChargeRequest addChargeRequest = new AddChargeRequest("Carne", 1L, 23d);

        when(chargesRepository.addCharge(1L, addChargeRequest)).thenReturn(Single.error(new Exception("No connection")));

        addChargePresenter.addCharge(1L, addChargeRequest);

        verify(addChargeView).chargeError();
    }
}