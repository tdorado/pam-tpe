package com.td.wallendar.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.ViewSwitcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.td.wallendar.R;
import com.td.wallendar.home.views.groups.GroupsAdapter;
import com.td.wallendar.home.views.groups.GroupsView;
import com.td.wallendar.home.views.profile.ProfileViewImpl;

public class MainActivity extends AppCompatActivity {

    private final int GROUPS = 0;
    private final int EVENTS = 1;
    private final int BALANCES = 2;
    private final int PROFILE = 3;

    private ViewSwitcher viewSwitcher;
    private BottomNavigationView bottomNavigationView;
    // CUSTOM VIEWS
    private GroupsView groupsView;
    private ProfileViewImpl profileView;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewSwitcher = findViewById(R.id.view_switcher);
        // SETUP VIEWS
        groupsView = findViewById(R.id.view_groups);
        groupsView.bind(new GroupsAdapter());

        profileView = findViewById(R.id.view_profile);
        profileView.bind();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.groups:
                    viewSwitcher.setDisplayedChild(GROUPS);
                    break;
                case R.id.events:
                    break;
                case R.id.balances:
                    break;
                case R.id.profile:
                    viewSwitcher.setDisplayedChild(2);
                    break;
                default:
                    return false;
            }
            return true;
        });
    }
}