package com.td.wallendar.addgroup.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

import com.google.android.material.textfield.TextInputLayout;
import com.td.wallendar.R;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Group;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class AddGroupActivityTest {
    private ActivityController<AddGroupActivity> activity;

    private AddGroupPresenter presenter;

    private Group fakeGroup = new Group(1L, "Group title", new ApplicationUser(),
            Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet());

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.buildActivity(AddGroupActivity.class);
        presenter = mock(AddGroupPresenter.class);

        activity.create();
        activity.get().setAddGroupPresenter(presenter);
    }

    @Test
    public void whenOptionsItemSelectedIsClicked_ThenANewGroupIsCreated_Ok() {
        doNothing().when(presenter).createGroup("Group title", 1L);

        activity.get().setLoggedUserId(1L);
        Objects.requireNonNull(((TextInputLayout) activity.get().findViewById(R.id.group_title)).getEditText()).setText("Group title");

        shadowOf(activity.get()).clickMenuItem(R.id.add_group_done);

        verify(presenter).createGroup("Group title", 1L);

        activity.get().onGroupCreated(fakeGroup);

        assertTrue(activity.get().isFinishing());
    }

    @Test
    public void whenOptionsItemSelectedIsClicked_ThenANewGroupIsCreated_WithErrors() {
        doNothing().when(presenter).createGroup("Group title", 1L);

        activity.get().setLoggedUserId(1L);
        Objects.requireNonNull(((TextInputLayout) activity.get().findViewById(R.id.group_title)).getEditText()).setText("Group title");
        
        shadowOf(activity.get()).clickMenuItem(R.id.add_group_done);

        verify(presenter).createGroup("Group title", 1L);

        activity.get().onGroupCreatedWithErrors();

        assertFalse(activity.get().isFinishing());
    }
}