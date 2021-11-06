package com.td.wallendar.home.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.addcharge.ui.AddChargeActivity;
import com.td.wallendar.addgroup.ui.AddGroupActivity;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.group.ui.GroupActivity;
import com.td.wallendar.home.balances.BalanceAdapter;
import com.td.wallendar.home.balances.OnBalanceSettleUpClickedListener;
import com.td.wallendar.home.balances.ui.BalancesView;
import com.td.wallendar.home.groups.GroupAdapter;
import com.td.wallendar.home.groups.OnGroupClickedListener;
import com.td.wallendar.home.groups.ui.GroupsView;
import com.td.wallendar.home.profile.OnLogoutClickedListener;
import com.td.wallendar.home.profile.OnShowAliasesClickedListener;
import com.td.wallendar.home.profile.ui.ProfileView;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.util.List;

public class HomeActivity extends AbstractActivity implements HomeView, OnGroupClickedListener,
        OnBalanceSettleUpClickedListener, OnShowAliasesClickedListener, OnLogoutClickedListener {

    private static final int GROUPS = 0;
    private static final int BALANCES = 1;
    private static final int PROFILE = 2;
    private int currentView = GROUPS;

    private static final int REQUEST_ADD_GROUP = 1;
    private static final int REFRESH = 2;

    private ViewFlipper viewFlipper;
    private ExtendedFloatingActionButton addChargeFAB;
    private BottomNavigationView bottomNavigationView;
    private GroupsView groupsView;
    private BalancesView balancesView;
    private ProfileView profileView;

    private HomePresenter homePresenter;

    private GroupAdapter groupAdapter;
    private BalanceAdapter balanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (!checkIfUserLogged()) {
            return;
        }
        createPresenter();

        setUpViews();
        setUpBottomNavigation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_GROUP && resultCode == RESULT_OK) {
            Group group = (Group) data.getExtras().getSerializable("NEW_GROUP");
            groupAdapter.addToDataset(group);
        }
        if (requestCode == REFRESH && resultCode == RESULT_OK) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    private void createPresenter() {
        homePresenter = (HomePresenter) getLastNonConfigurationInstance();

        if (homePresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            final DebtsRepository debtsRepository = dependenciesContainer.getDebtsRepository();
            final ApplicationUsersRepository applicationUsersRepository = dependenciesContainer.getApplicationUsersRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            homePresenter = new HomePresenter(this, groupsRepository, debtsRepository,
                    applicationUsersRepository, schedulerProvider);
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
            return true;
        }
        menu.findItem(R.id.add_group).setVisible(false);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_group) {
            startActivityForResult(new Intent(this, AddGroupActivity.class), REQUEST_ADD_GROUP);
            return true;
        }
        return false;
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
        addChargeFAB.setOnClickListener(view -> startActivity(new Intent(this, AddChargeActivity.class)));
    }

    private void setUpGroupsView() {
        groupsView = findViewById(R.id.view_groups);
        groupAdapter = new GroupAdapter();
        groupAdapter.setOnGroupClickedListener(this);
        groupsView.bind(groupAdapter, addChargeFAB);
    }

    private void setUpBalancesView() {
        balancesView = findViewById(R.id.view_balances);
        balanceAdapter = new BalanceAdapter(getLoggedUserId());
        balanceAdapter.setOnBalanceSettleUpClickedListener(this);
        balancesView.bind(balanceAdapter);
    }

    private void setUpProfileView() {
        profileView = findViewById(R.id.view_profile);
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

    @Override
    public void onBackPressed() {
        if (currentView == GROUPS) {
            finishAndRemoveTask();
        } else {
            currentView = GROUPS;
            bottomNavigationView.setSelectedItemId(R.id.groups);
        }
    }

    @Override
    public void bindGroups(List<Group> groups) {
        groupAdapter.setDataset(groups);
    }

    @Override
    public void bindDebts(List<Debt> debts) {
        balanceAdapter.setDataset(debts);
    }

    @Override
    public void bindProfile(ApplicationUser applicationUser) {
        profileView.bind(applicationUser, this, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        homePresenter.onViewAttached(getLoggedUserId());
    }

    @Override
    public void onStop() {
        super.onStop();
        homePresenter.onViewDetached();
    }

    @Override
    public void onGroupClicked(long groupId) {
        final Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
        intent.putExtra("GROUP_ID", groupId);
        startActivity(intent);
    }

    @Override
    public void onBalanceSettleUpClick(long groupId, Debt debt) {
        //TODO settle up call to backend
    }

    @Override
    public void onLogoutClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.are_you_sure));
        builder.setPositiveButton(getString(R.string.logout), (dialog, which) -> {
            logout();
            dialog.dismiss();
        });
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onShowAliasesClicked() {
        //TODO make show aliases activity
    }
}