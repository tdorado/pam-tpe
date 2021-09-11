package com.td.wallendar.addevent.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.td.wallendar.R;

public class AddEventActivity extends AppCompatActivity implements AddEventView {

    private Button mPickDateButton;
    private TextView mShowSelectedDateText;

    private Button mPickTimeButton;
    private TextView mShowSelectedTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        actionBar.setTitle(R.string.add_event);

        setContentView(R.layout.activity_add_event);
        setUpDatePicker();
        setUpTimePicker();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_event_done) {
            finish();
            return true;
        }
        return false;
    }

    private void setUpDatePicker() {

        mPickDateButton = findViewById(R.id.pick_date_button);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);

        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText(R.string.select_a_date);

        final MaterialDatePicker<Long> materialDatePicker = materialDateBuilder.build();

        mPickDateButton.setOnClickListener(
                v -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    String selectedDate = getResources().getString(R.string.selected_date) + " " + materialDatePicker.getHeaderText();
                    mShowSelectedDateText.setText(selectedDate);
                });
    }

    private void setUpTimePicker() {
        mPickTimeButton = findViewById(R.id.pick_time_button);
        mShowSelectedTimeText = findViewById(R.id.show_selected_time);

        final MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(R.string.select_a_time)
                .build();

        mPickTimeButton.setOnClickListener(
                v -> materialTimePicker.show(getSupportFragmentManager(), "MATERIAL_TIME_PICKER"));

        materialTimePicker.addOnPositiveButtonClickListener(
                selection -> {
                    Integer min = materialTimePicker.getMinute();
                    String minute = (min == 0 ? "00" : min.toString());
                    String selectedTime = getResources().getString(R.string.selected_time) + " " +
                            materialTimePicker.getHour() + ":" + minute;
                    mShowSelectedTimeText.setText(selectedTime);
                });

    }
}