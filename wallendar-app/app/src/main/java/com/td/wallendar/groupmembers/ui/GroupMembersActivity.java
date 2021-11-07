package com.td.wallendar.groupmembers.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.groupmembers.GroupMembersAdapter;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.util.List;

public class GroupMembersActivity extends AbstractActivity implements GroupMembersView {

    private final String GROUP_ID = "GROUP_ID";

    private GroupMembersPresenter groupMembersPresenter;
    private GroupMembersAdapter groupMembersAdapter;

    private long groupId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);
        groupId = getIntent().getExtras().getLong(GROUP_ID);

        setUpView();
        createPresenter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        groupMembersPresenter.onViewAttached(groupId);
    }

    @Override
    public void onStop() {
        super.onStop();
        groupMembersPresenter.onViewDetached();
    }

    @Override
    public void bind(List<ApplicationUser> members) {
        groupMembersAdapter.setDataset(members);
    }

    @Override
    public void failedToLoadMembers() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.failed_to_load_members), Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return groupMembersPresenter;
    }

    private void setUpView() {
        groupMembersAdapter = new GroupMembersAdapter();
        RecyclerView recycler = findViewById(R.id.group_members_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(groupMembersAdapter);

        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.members);
    }

    private void createPresenter() {
        groupMembersPresenter = (GroupMembersPresenter) getLastNonConfigurationInstance();

        if (groupMembersPresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            groupMembersPresenter = new GroupMembersPresenter(this, groupsRepository, schedulerProvider);
        }
    }
}
