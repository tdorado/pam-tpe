package com.td.wallendar.group.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.addcharge.ui.AddChargeActivity;
import com.td.wallendar.addmembers.ui.AddMembersActivity;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.group.GroupHistoryAdapter;
import com.td.wallendar.groupbalance.ui.GroupBalanceActivity;
import com.td.wallendar.groupmembers.ui.GroupMembersActivity;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Group;
import com.td.wallendar.models.GroupHistory;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.util.List;

public class GroupActivity extends AbstractActivity implements GroupView {

    private static final int REQUEST_ADD_CHARGE = 1;
    private static final int REQUEST_ADD_MEMBERS = 2;
    private static final int REFRESH = 3;

    private GroupPresenter groupPresenter;
    private GroupHistoryAdapter groupHistoryAdapter;
    private RecyclerView recycler;

    private ExtendedFloatingActionButton addChargeFAB;
    private TextView groupTitle;
    // Intended to be nullable
    private Long groupId;

    private boolean needsToRefresh = false;

    private final String GROUP_ID = "GROUP_ID";
    private final String NEW_CHARGE = "NEW_CHARGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        groupId = getIntent().getExtras().getLong(GROUP_ID);
        setUpView();
        createPresenter();
    }

    private void setUpView() {
        groupHistoryAdapter = new GroupHistoryAdapter();

        recycler = findViewById(R.id.group_activity_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(groupHistoryAdapter);

        setUpAddChargeButton(groupId);

        groupTitle = findViewById(R.id.group_title);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // TODO
        findViewById(R.id.group_events).setOnClickListener(view -> Toast.makeText(getApplicationContext(),
                getString(R.string.feature_not_ready), Toast.LENGTH_SHORT).show());
        findViewById(R.id.group_balances).setOnClickListener(view -> {
            final Intent intent = new Intent(this, GroupBalanceActivity.class);
            intent.putExtra(GROUP_ID, groupId);
            startActivityForResult(intent, REFRESH);
        });
        findViewById(R.id.group_members).setOnClickListener(view -> {
            final Intent intent = new Intent(this, GroupMembersActivity.class);
            intent.putExtra(GROUP_ID, groupId);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_CHARGE && resultCode == RESULT_OK) {
            Charge charge = (Charge) data.getExtras().getSerializable(NEW_CHARGE);
            groupHistoryAdapter.addToDataset(charge);
            needsToRefresh = true;
        } else if (requestCode == REQUEST_ADD_MEMBERS && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), getString(R.string.members_added_successful), Toast.LENGTH_SHORT).show();
        } else if (requestCode == REFRESH && resultCode == RESULT_OK) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_members_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.add_members).setVisible(true);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_members) {
            final Intent addMembersIntent = new Intent(this, AddMembersActivity.class);
            addMembersIntent.putExtra(GROUP_ID, groupId);
            startActivityForResult(addMembersIntent, REQUEST_ADD_MEMBERS);
            return true;
        }
        return false;
    }

    private void createPresenter() {
        groupPresenter = (GroupPresenter) getLastNonConfigurationInstance();

        if (groupPresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            groupPresenter = new GroupPresenter(this, groupsRepository, schedulerProvider);
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return groupPresenter;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void bindGroup(Group group) {
        groupTitle.setText(group.getTitle());
    }

    @Override
    public void bindGroupHistory(List<GroupHistory> historic) {
        groupHistoryAdapter.setDataset(historic);
    }

    @Override
    public void onGroupLoadError() {
        Toast.makeText(getApplicationContext(), "There was an error loading the group, try again later", Toast.LENGTH_LONG).show();
    }

    private void setUpAddChargeButton(Long groupId) {
        addChargeFAB = findViewById(R.id.add_charge_fab);
        addChargeFAB.setOnClickListener(view -> {
            final Intent intent = new Intent(this, AddChargeActivity.class);
            intent.putExtra(GROUP_ID, groupId);
            startActivityForResult(intent, REQUEST_ADD_CHARGE);
        });
        // Shrink floating button when scrolling, extend at the top. Just fancy fab
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addChargeFAB.extend();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && addChargeFAB.isExtended()) {
                    addChargeFAB.shrink();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        groupPresenter.onViewAttached(groupId);
    }

    @Override
    public void onStop() {
        super.onStop();
        groupPresenter.onViewDetached();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        groupPresenter.onViewDestroyed();
    }

    @Override
    public void onBackPressed() {
        if (needsToRefresh) {
            final Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }
}
