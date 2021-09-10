package com.td.wallendar.addcharge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.td.wallendar.R;
import com.td.wallendar.home.ui.HomeActivity;

import java.util.Arrays;
import java.util.List;

public class AddChargeActivity extends AppCompatActivity implements AddChargeView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.add_charge);

        setContentView(R.layout.activity_addcharge);

        List<String> groups = Arrays.asList("GROUP 1", "GROUP 2", "GROUP 3", "GROUP 4");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, groups);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.group_charge_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
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
            finish();
            return true;
        }
        return false;
    }
}
