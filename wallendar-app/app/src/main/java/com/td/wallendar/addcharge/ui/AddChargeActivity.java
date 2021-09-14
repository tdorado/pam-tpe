package com.td.wallendar.addcharge.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.td.wallendar.R;
import com.td.wallendar.models.EqualCharge;
import com.td.wallendar.models.Group;
import com.td.wallendar.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AddChargeActivity extends AppCompatActivity implements AddChargeView {

    private ArrayAdapter<String> adapter;
    private Map<String, Group> stringGroupMap = new HashMap<>();

    private String groupSelected;
    private String chargeTitleSelected;
    private double chargeAmountSelected;

    private AddChargePresenter addChargePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.add_charge);

        setContentView(R.layout.activity_add_charge);

        TextInputLayout chargeTitle = findViewById(R.id.charge_title);
        chargeTitle.addOnEditTextAttachedListener(textInputLayout ->
                chargeTitleSelected = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString()
        );
        TextInputLayout chargeAmount = findViewById(R.id.charge_amount);
        chargeAmount.addOnEditTextAttachedListener(textInputLayout ->
                {
                    if (!Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().isEmpty()) {
                        chargeAmountSelected = Double.parseDouble(textInputLayout.getEditText().getText().toString());
                    }
                }
        );

        buildPresenter();

    }

    public void buildPresenter() {
        addChargePresenter = new AddChargePresenter(this);
    }

    @Override
    public Long getUserId() {
        return 1L;
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
            addChargePresenter.addCharge(new EqualCharge(new User(),
                    chargeTitleSelected, new ArrayList<>(), chargeAmountSelected, new Date(), new Group(groupSelected))
            );
            return true;
        }
        return false;
    }

    @Override
    public void onGroupsLoadError() {
        Toast.makeText(getApplicationContext(), "Groups load error", Toast.LENGTH_LONG);
    }

    @Override
    public void chargeError() {
        Toast.makeText(getApplicationContext(), "Add charge ends with error", Toast.LENGTH_LONG);

    }

    @Override
    public void chargeAddedOk() {
        Toast.makeText(getApplicationContext(), "Charge added ok", Toast.LENGTH_LONG);

    }

    @Override
    public void addGroups(List<Group> groups) {
        final List<String> stringGroups = new ArrayList<>();
        for (Group group : groups) {
            stringGroups.add(group.getTitle());
            stringGroupMap.put(group.getTitle(), group);
        }
        adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, stringGroups);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.group_charge_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener((adapterView, view, i, l) -> groupSelected = adapter.getItem(i));
    }
}
