package com.td.wallendar.addcharge.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.td.wallendar.R;

public class AddChargeActivity extends AppCompatActivity implements AddChargeView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_addcharge);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
