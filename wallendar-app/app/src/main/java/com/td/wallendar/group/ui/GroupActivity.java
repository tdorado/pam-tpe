package com.td.wallendar.group.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.group.GroupActivitiesAdapter;

public class GroupActivity extends AppCompatActivity implements GroupView {

    private GroupActivitiesAdapter groupActivitiesAdapter;
    private RecyclerView recycler;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        groupActivitiesAdapter = new GroupActivitiesAdapter();

        recycler = findViewById(R.id.group_activity_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(groupActivitiesAdapter);
    }
}
