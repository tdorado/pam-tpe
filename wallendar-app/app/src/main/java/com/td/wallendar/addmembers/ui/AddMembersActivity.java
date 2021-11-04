package com.td.wallendar.addmembers.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.addgroup.ui.AddGroupPresenter;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.repositories.interfaces.GroupsRepository;

public class AddMembersActivity extends AbstractActivity implements AddMembersView {
    private AddMembersPresenter addMembersPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_group);

        setupActionBar();

        createPresenter();
    }

    private void createPresenter() {
        addMembersPresenter = (AddMembersPresenter) getLastNonConfigurationInstance();

        if (addMembersPresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            addMembersPresenter = new AddMembersPresenter(this, groupsRepository);
        }
    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        actionBar.setTitle(R.string.add_group);
    }

}
