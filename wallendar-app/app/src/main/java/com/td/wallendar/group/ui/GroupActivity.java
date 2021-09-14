package com.td.wallendar.group.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.group.GroupActivitiesAdapter;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.GroupsRepositoryImpl;

public class GroupActivity extends AppCompatActivity implements GroupView {

    private GroupPresenter groupPresenter;
    private GroupActivitiesAdapter groupActivitiesAdapter;
    private RecyclerView recycler;

    private TextView groupTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        groupActivitiesAdapter = new GroupActivitiesAdapter();

        recycler = findViewById(R.id.group_activity_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(groupActivitiesAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        groupTitle = findViewById(R.id.group_title);

        Long groupId = getIntent().getExtras().getLong("GROUP_ID");

        createPresenter();
        groupPresenter.getGroup(groupId);
    }

    private void createPresenter() {
        groupPresenter = (GroupPresenter) getLastNonConfigurationInstance();

        if (groupPresenter == null) {
            groupPresenter = new GroupPresenter(this, new GroupsRepositoryImpl());
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
}
