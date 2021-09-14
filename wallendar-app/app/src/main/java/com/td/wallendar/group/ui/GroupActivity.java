package com.td.wallendar.group.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.group.GroupActivitiesAdapter;

public class GroupActivity extends AppCompatActivity implements GroupView {

    private GroupActivitiesAdapter groupActivitiesAdapter;
    private RecyclerView recycler;

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
