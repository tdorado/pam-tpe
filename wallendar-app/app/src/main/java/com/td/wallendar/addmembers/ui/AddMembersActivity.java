package com.td.wallendar.addmembers.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
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

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AddMembersActivity extends AbstractActivity implements AddMembersView {
    private AddMembersPresenter addMembersPresenter;

    private TextInputLayout memberInputLayout;
    private Button tickToAddMember;
    private RecyclerView recyclerView;

    private MembersAdapter membersAdapter;
    private List<String> members = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_members);

        setupAdapter();
        setupActionBar();

        setupInput();
        setupTick();
        setupRecyclerView();

        createPresenter();
    }

    private void setupAdapter() {
        membersAdapter = new MembersAdapter(members);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.current_members_added);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(membersAdapter);
    }

    private void setupTick() {
        tickToAddMember = findViewById(R.id.add_member_button);
        tickToAddMember.setOnClickListener(view -> {
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
            addMembersPresenter = new AddMembersPresenter(this, groupsRepository);
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
    public void onMembersAdded(Set<Member> members) {
        final Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
