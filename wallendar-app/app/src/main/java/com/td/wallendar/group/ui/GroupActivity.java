package com.td.wallendar.group.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.addcharge.ui.AddChargeActivity;
import com.td.wallendar.addgroup.ui.AddGroupActivity;
import com.td.wallendar.addmembers.ui.AddMembersActivity;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.group.GroupHistoryAdapter;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Group;
import com.td.wallendar.models.GroupHistory;
import com.td.wallendar.repositories.interfaces.GroupsRepository;

import java.util.List;

public class GroupActivity extends AbstractActivity implements GroupView {

    private static final int REQUEST_ADD_CHARGE = 1;
    private static final int REQUEST_ADD_MEMBERS = 2;

    private GroupPresenter groupPresenter;
    private GroupHistoryAdapter groupHistoryAdapter;
    private RecyclerView recycler;

    private ExtendedFloatingActionButton addChargeFAB;
    private TextView groupTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        groupHistoryAdapter = new GroupHistoryAdapter();

        recycler = findViewById(R.id.group_activity_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(groupHistoryAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        groupTitle = findViewById(R.id.group_title);

        Long groupId = getIntent().getExtras().getLong("GROUP_ID");

        createPresenter();
        groupPresenter.getGroup(groupId);

        setUpAddChargeButton(groupId);

        // TODO
        findViewById(R.id.group_events).setOnClickListener(view -> Toast.makeText(getApplicationContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT)
                .show());
        findViewById(R.id.group_balances).setOnClickListener(view -> Toast.makeText(getApplicationContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT)
                .show());
        findViewById(R.id.group_activity).setOnClickListener(view -> Toast.makeText(getApplicationContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT)
                .show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_CHARGE && resultCode == RESULT_OK) {
            Charge charge = (Charge) data.getExtras().getSerializable("NEW_CHARGE");
            groupHistoryAdapter.addToDataset(charge);
        } else if (requestCode == REQUEST_ADD_MEMBERS && resultCode == RESULT_OK) {

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
            startActivityForResult(new Intent(this, AddMembersActivity.class), REQUEST_ADD_MEMBERS);
            return true;
        }
        return false;
    }

    private void createPresenter() {
        groupPresenter = (GroupPresenter) getLastNonConfigurationInstance();

        if (groupPresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            groupPresenter = new GroupPresenter(this, groupsRepository);
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return groupPresenter;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
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

    private void setUpAddChargeButton(Long groupId) {
        addChargeFAB = findViewById(R.id.add_charge_fab);
        addChargeFAB.setOnClickListener(view -> {
            final Intent intent = new Intent(this, AddChargeActivity.class);
            intent.putExtra("GROUP_ID", groupId);
            startActivityForResult(intent, REQUEST_ADD_CHARGE);
        });
    }
}
