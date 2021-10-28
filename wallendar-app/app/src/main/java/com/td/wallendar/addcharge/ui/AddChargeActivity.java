package com.td.wallendar.addcharge.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.td.wallendar.R;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AddChargeActivity extends AppCompatActivity implements AddChargeView {

    private ArrayAdapter<String> adapter;
    private Map<String, Group> stringGroupMap = new HashMap<>();

    private String groupSelected;
    private String chargeTitleSelected;
    private double chargeAmountSelected;

    private AddChargePresenter addChargePresenter;
    private AutoCompleteTextView editTextFilledExposedDropdown;

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

        Bundle extras = getIntent().getExtras();
        Long groupId;
        if (extras != null) {
            groupId = extras.getLong("GROUP_ID");
            addChargePresenter.setGroupId(groupId);
        }

    }

    public void buildPresenter() {
        addChargePresenter = new AddChargePresenter(this);
        addChargePresenter.onViewAttached(1L);
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
            addChargePresenter.addCharge(new Charge(
                            chargeTitleSelected,
                            new ApplicationUser(),
                            new HashSet<>(),
                            chargeAmountSelected,
                            new Date(),
                            new Group()
                    )
            );
            return true;
        }
        return false;
    }

    @Override
    public void onGroupsLoadError() {
        Toast.makeText(getApplicationContext(), "Groups load error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void chargeError() {
        Toast.makeText(getApplicationContext(), "Add charge ends with error", Toast.LENGTH_LONG).show();

    }

    @Override
    public void chargeAddedOk() {
        Toast.makeText(getApplicationContext(), "Charge added ok", Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void addGroups(List<Group> groups) {
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
    public void setSelectedGroup(Long groupId) {
        editTextFilledExposedDropdown.setText(adapter.getItem(groupId.intValue()), false);
    }
}
