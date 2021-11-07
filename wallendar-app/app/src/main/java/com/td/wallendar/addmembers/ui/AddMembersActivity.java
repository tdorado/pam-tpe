package com.td.wallendar.addmembers.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.addmembers.MembersAdapter;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddMembersActivity extends AbstractActivity implements AddMembersView {
    private AddMembersPresenter addMembersPresenter;

    private TextInputLayout memberInputLayout;
    private Button checkToAddMember;
    private RecyclerView recyclerView;

    private MembersAdapter membersAdapter;
    private final List<String> members = new ArrayList<>();

    private final long NO_GROUP_ID = -1;
    private final String GROUP_ID = "GROUP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_members);

        setupAdapter();
        setupActionBar();

        setupInput();
        setupAddMember();
        setupSubmitMembers();
        setupRecyclerView();

        createPresenter();
    }

    private void setupSubmitMembers() {
        final long groupId = getIntent().getLongExtra("GROUP_ID", -1);
        if (groupId == NO_GROUP_ID) {
            // TODO error
        } else {
            findViewById(R.id.submit_members).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addMembersPresenter.submitMembers(groupId, members);
                }
            });
        }
    }

    private void setupAdapter() {
        membersAdapter = new MembersAdapter(members);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.current_members_added);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(membersAdapter);
    }

    private void setupAddMember() {
        checkToAddMember = findViewById(R.id.add_member_button);
        checkToAddMember.setOnClickListener(view -> {
            final String currentMember = Objects.requireNonNull(memberInputLayout.getEditText()).getText().toString();
            memberInputLayout.getEditText().getText().clear();
            membersAdapter.addToDataset(currentMember);
        });
    }

    private void setupInput() {
        memberInputLayout = findViewById(R.id.member_layout);
    }

    private void createPresenter() {
        addMembersPresenter = (AddMembersPresenter) getLastNonConfigurationInstance();

        if (addMembersPresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            addMembersPresenter = new AddMembersPresenter(this, groupsRepository, schedulerProvider);
        }
    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        actionBar.setTitle(R.string.add_members);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // TODO we are not using the groupId param right now, may be useful in some borders cases
    @Override
    public void onMembersAddedSuccessfully(long groupId) {
        final Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onMembersAddedWithError() {
        Toast.makeText(getApplicationContext(), "There was an error adding members", Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void onStop() {
        super.onStop();
        addMembersPresenter.onViewDetached();
    }
}
