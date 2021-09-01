package com.td.wallendar.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.ViewFlipper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.td.wallendar.R;
import com.td.wallendar.home.views.balances.BalancesView;
import com.td.wallendar.home.views.events.EventsView;
import com.td.wallendar.home.views.groups.GroupsAdapter;
import com.td.wallendar.home.views.groups.GroupsView;
import com.td.wallendar.home.views.profile.ProfileView;

public class MainActivity extends AppCompatActivity {

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

    @RequiresApi(api = Build.VERSION_CODES.R)
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

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void setUpViews() {
        viewFlipper = findViewById(R.id.view_flipper);

        setUpGroupsView();
        setUpEventsView();
        setUpBalancesView();
        setUpProfileView();
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    private void setUpGroupsView() {
        groupsView = findViewById(R.id.view_groups);
        groupsView.bind(new GroupsAdapter());
    }

    private void setUpEventsView() {
        eventsView = findViewById(R.id.view_events);
        eventsView.bind();
    }

    private void setUpBalancesView() {
        balancesView = findViewById(R.id.view_balances);
        balancesView.bind();
    }

    private void setUpProfileView() {
        profileView = findViewById(R.id.view_profile);
        profileView.bind();
    }
}