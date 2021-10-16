package com.td.wallendar.home.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
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
    private static final int BALANCES = 2;
    private static final int PROFILE = 3;
    private int currentView = GROUPS;

    private ViewFlipper viewFlipper;
    private ExtendedFloatingActionButton addChargeFAB;
    private BottomNavigationView bottomNavigationView;
    private GroupsView groupsView;
    private BalancesView balancesView;
    private ProfileView profileView;

    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createPresenter();

        setUpViews();
        setUpBottomNavigation();
    }

    private void createPresenter() {
        homePresenter = (HomePresenter) getLastNonConfigurationInstance();

        if (homePresenter == null) {
            homePresenter = new HomePresenter(this);
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return homePresenter;
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
        if (currentView == GROUPS) {
            menu.findItem(R.id.add_group).setVisible(true);
            menu.findItem(R.id.add_event).setVisible(false);
            return true;
        }
        menu.findItem(R.id.add_group).setVisible(false);
        menu.findItem(R.id.add_event).setVisible(false);
        return true;
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

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.groups:
                    homePresenter.onGroupsClicked();
                    break;
                case R.id.balances:
                    homePresenter.onBalancesClicked();
                    break;
                case R.id.profile:
                    Toast.makeText(getApplicationContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT)
                            .show();
                    homePresenter.onProfileClicked();
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

    private void setUpBalancesView() {
        balancesView = findViewById(R.id.view_balances);
        balancesView.bind();
    }

    private void setUpProfileView() {
        profileView = findViewById(R.id.view_profile);
        profileView.bind();
    }

    @Override
    public void showGroups() {
        currentView = GROUPS;
        invalidateOptionsMenu();
        addChargeFAB.show();
        viewFlipper.setDisplayedChild(GROUPS);
    }

    @Override
    public void showBalances() {
        currentView = BALANCES;
        invalidateOptionsMenu();
        addChargeFAB.hide();
        viewFlipper.setDisplayedChild(BALANCES);
    }

    @Override
    public void showProfile() {
        currentView = PROFILE;
        invalidateOptionsMenu();
        addChargeFAB.hide();
        viewFlipper.setDisplayedChild(PROFILE);
    }
}