package com.td.wallendar.home.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.td.wallendar.R;
import com.td.wallendar.home.balances.BalancesAdapter;
import com.td.wallendar.home.balances.ui.BalancesView;
import com.td.wallendar.home.events.EventsAdapter;
import com.td.wallendar.home.events.ui.EventsView;
import com.td.wallendar.home.groups.GroupsAdapter;
import com.td.wallendar.home.groups.ui.GroupsView;
import com.td.wallendar.home.profile.ui.ProfileView;

public class HomeActivity extends AppCompatActivity {

    private static final int GROUPS = 0;
    private static final int EVENTS = 1;
    private static final int BALANCES = 2;
    private static final int PROFILE = 3;

    private ViewFlipper viewFlipper;
    private BottomNavigationView bottomNavigationView;
    private GroupsView groupsView;
    private EventsView eventsView;
    private BalancesView balancesView;
    private ProfileView profileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        setUpViews();
        setUpBottomNavigation();
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.groups);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.groups:
                    viewFlipper.setDisplayedChild(GROUPS);
                    break;
                case R.id.events:
                    viewFlipper.setDisplayedChild(EVENTS);
                    break;
                case R.id.balances:
                    viewFlipper.setDisplayedChild(BALANCES);
                    break;
                case R.id.profile:
                    viewFlipper.setDisplayedChild(PROFILE);
                    break;
                default:
                    return false;
            }
            return true;
        });
    }

    private void setUpViews() {
        viewFlipper = findViewById(R.id.view_flipper);

        setUpGroupsView();
        setUpEventsView();
        setUpBalancesView();
        setUpProfileView();
    }


    private void setUpGroupsView() {
        groupsView = findViewById(R.id.view_groups);
        groupsView.bind(new GroupsAdapter());
    }

    private void setUpEventsView() {
        eventsView = findViewById(R.id.view_events);
        eventsView.bind(new EventsAdapter());
    }

    private void setUpBalancesView() {
        balancesView = findViewById(R.id.view_balances);
        balancesView.bind(new BalancesAdapter());
    }

    private void setUpProfileView() {
        profileView = findViewById(R.id.view_profile);
        profileView.bind();
    }
}