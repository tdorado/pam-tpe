package com.td.wallendar.home.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.R;
import com.td.wallendar.addcharge.ui.AddChargeActivity;
import com.td.wallendar.addevent.ui.AddEventActivity;
import com.td.wallendar.addgroup.ui.AddGroupActivity;
import com.td.wallendar.home.balances.BalancesAdapter;
import com.td.wallendar.home.balances.ui.BalancesView;
import com.td.wallendar.home.events.EventsAdapter;
import com.td.wallendar.home.events.ui.EventsView;
import com.td.wallendar.home.groups.ui.GroupsView;
import com.td.wallendar.home.profile.ui.ProfileView;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private static final int GROUPS = 0;
    private static final int EVENTS = 1;
    private static final int BALANCES = 2;
    private static final int PROFILE = 3;
    private int currentView = GROUPS;

    private ViewFlipper viewFlipper;
    private ExtendedFloatingActionButton addChargeFAB;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        switch (currentView) {
            case GROUPS:
                menu.findItem(R.id.add_group).setVisible(true);
                menu.findItem(R.id.add_event).setVisible(false);
                return true;
            case EVENTS:
                menu.findItem(R.id.add_event).setVisible(true);
                menu.findItem(R.id.add_group).setVisible(false);
                return true;
            default:
                menu.findItem(R.id.add_group).setVisible(false);
                menu.findItem(R.id.add_event).setVisible(false);
                return true;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_group:
                startActivity(new Intent(HomeActivity.this, AddGroupActivity.class));
                return true;
            case R.id.add_event:
                startActivity(new Intent(HomeActivity.this, AddEventActivity.class));
                return true;
            default:
                return false;
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.groups);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.groups:
                    currentView = GROUPS;
                    invalidateOptionsMenu();
                    addChargeFAB.show();
                    viewFlipper.setDisplayedChild(GROUPS);
                    break;
                case R.id.events:
                    currentView = EVENTS;
                    invalidateOptionsMenu();
                    addChargeFAB.show();
                    viewFlipper.setDisplayedChild(EVENTS);
                    break;
                case R.id.balances:
                    currentView = BALANCES;
                    invalidateOptionsMenu();
                    addChargeFAB.hide();
                    viewFlipper.setDisplayedChild(BALANCES);
                    break;
                case R.id.profile:
                    currentView = PROFILE;
                    invalidateOptionsMenu();
                    addChargeFAB.hide();
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

        setUpAddChargeButton();
        setUpGroupsView();
        setUpEventsView();
        setUpBalancesView();
        setUpProfileView();
    }

    private void setUpAddChargeButton() {
        addChargeFAB = findViewById(R.id.add_charge_fab);
        addChargeFAB.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, AddChargeActivity.class)));
    }

    private void setUpGroupsView() {
        groupsView = findViewById(R.id.view_groups);
        groupsView.bind(addChargeFAB, this);
    }

    private void setUpEventsView() {
        eventsView = findViewById(R.id.view_events);
        eventsView.bind(addChargeFAB, new EventsAdapter());
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