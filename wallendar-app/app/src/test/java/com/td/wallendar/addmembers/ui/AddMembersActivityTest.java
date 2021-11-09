package com.td.wallendar.addmembers.ui;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.td.wallendar.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class AddMembersActivityTest {

    private ActivityController<AddMembersActivity> activity;

    private AddMembersPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.buildActivity(AddMembersActivity.class);
        presenter = mock(AddMembersPresenter.class);

        activity.get().getIntent().putExtra("GROUP_ID", 1L);

        activity.create();

        activity.get().setPresenter(presenter);
    }

    @Test
    public void whenSubmitMembersIsClicked_thenPresenterIsCalledAndActivityIsFinishedOnCallback() {

        doNothing().when(presenter).submitMembers(1L, new ArrayList<>());

        activity.get().findViewById(R.id.submit_members).performClick();

        verify(presenter).submitMembers(1L, new ArrayList<>());

        activity.get().onMembersAddedSuccessfully(1L);

        assertTrue(activity.get().isFinishing());
    }
}