package com.td.wallendar.addcharge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.td.wallendar.R;
import com.td.wallendar.di.ApplicationUserModule;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddChargeActivity extends AppCompatActivity implements AddChargeView {

    private ArrayAdapter<String> adapter;
    private final Map<String, Group> stringGroupMap = new HashMap<>();

    private TextInputLayout chargeTitleInput;
    private TextInputLayout chargeAmountInput;

    private String groupSelected = null;

    private AddChargePresenter addChargePresenter;
    private AutoCompleteTextView editTextFilledExposedDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_charge);

        setupActionBar();
        setupInputs();

        createPresenter();

        setupGroupId();
    }

    private void setupGroupId() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long groupId = extras.getLong("GROUP_ID");
            addChargePresenter.setGroupId(groupId);
        }
    }

    private void setupInputs() {
        chargeTitleInput = findViewById(R.id.charge_title);
        chargeAmountInput = findViewById(R.id.charge_amount);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        actionBar.setTitle(R.string.add_charge);
    }

    private void createPresenter() {
        addChargePresenter = (AddChargePresenter) getLastNonConfigurationInstance();

        if (addChargePresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final ChargesRepository chargesRepository = dependenciesContainer.getChargesRepository();
            final GroupsRepository groupsRepository = dependenciesContainer.getGroupsRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            addChargePresenter = new AddChargePresenter(this, chargesRepository,
                    groupsRepository, schedulerProvider);
        }
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return addChargePresenter;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_charge_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_charge_done) {
            final Editable editableChargeTitle = chargeTitleInput.getEditText().getText();
            final Editable editableChargeAmount = chargeAmountInput.getEditText().getText();
            if (editableChargeTitle != null && editableChargeAmount != null && groupSelected != null) {
                final String chargeTitle = editableChargeTitle.toString();
                final String chargeAmount = editableChargeAmount.toString();
                double chargeAmountValue;
                try {
                    chargeAmountValue = Double.parseDouble(chargeAmount);
                } catch (Exception e) {
                    //TODO
                    Toast.makeText(getApplicationContext(), "TIENE QUE SER DOUBLE", Toast.LENGTH_LONG).show();
                    return false;
                }
                final long groupId = stringGroupMap.get(groupSelected).getId();
                addChargePresenter.addCharge(groupId, new AddChargeRequest(chargeTitle,
                        ApplicationUserModule.getLoggedUserId(getApplicationContext()),
                        chargeAmountValue));
                return true;
            } else {
                //TODO
                Toast.makeText(getApplicationContext(), "PONELE TITULO", Toast.LENGTH_LONG).show();
            }

        }
        return false;
    }

    @Override
    public void chargeError() {
        Toast.makeText(getApplicationContext(), "Error adding charge", Toast.LENGTH_LONG).show();
    }

    @Override
    public void chargeAddedOk(Charge charge) {
        Toast.makeText(getApplicationContext(), "Charge added ok", Toast.LENGTH_LONG).show();
        final Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_CHARGE", charge);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onGroupsLoadOk(List<Group> groups) {
        final List<String> stringGroups = new ArrayList<>();
        for (Group group : groups) {
            stringGroups.add(group.getTitle());
            stringGroupMap.put(group.getTitle(), group);
        }
        adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, stringGroups);
        editTextFilledExposedDropdown = findViewById(R.id.group_charge_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener((adapterView, view, i, l) -> groupSelected = adapter.getItem(i));
    }

    @Override
    public void onGroupsLoadError() {
        Toast.makeText(getApplicationContext(), "Groups load error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSelectedGroup(Long groupId) {
        for (Group group : stringGroupMap.values()) {
            if (group.getId() == groupId) {
                groupSelected = group.getTitle();
            }
        }
        editTextFilledExposedDropdown.setText(groupSelected, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        addChargePresenter.onViewAttached(ApplicationUserModule.getLoggedUserId(getApplicationContext()));
    }

    @Override
    public void onStop() {
        super.onStop();
        addChargePresenter.onViewDetached();
    }
}
