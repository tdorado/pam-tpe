package com.td.wallendar.addgroup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.google.android.material.textfield.TextInputLayout;
import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

public class AddGroupActivity extends AbstractActivity implements AddGroupView {

    private AddGroupPresenter addGroupPresenter;

    private TextInputLayout groupTitleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_group);

        setupActionBar();
        setupGroupTitleInput();

        createPresenter();
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return addGroupPresenter;
    }

    private void setupGroupTitleInput() {
        groupTitleInput = findViewById(R.id.group_title);
    }

    private void createPresenter() {
        addGroupPresenter = (AddGroupPresenter) getLastNonConfigurationInstance();

        if (addGroupPresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            addGroupPresenter = new AddGroupPresenter(this, groupsRepository, schedulerProvider);
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        actionBar.setTitle(R.string.add_group);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_group_done) {
            final Editable editableGroupTitle = groupTitleInput.getEditText().getText();
            if (editableGroupTitle != null) {
                final String groupTitle = editableGroupTitle.toString();
                addGroupPresenter.createGroup(groupTitle, getLoggedUserId());
            } else {
                // TODO
                Toast.makeText(getApplicationContext(), "PONELE UN TITULO", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    // TODO
    @Override
    public void onGroupCreated(final Group group) {
        final Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_GROUP", group);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        addGroupPresenter.onViewDetached();
    }
}
