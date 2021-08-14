package com.td.wallendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

public class WithFooterActivity extends AppCompatActivity {

    private final int layoutId;

    public WithFooterActivity(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        //FIXME avoid repetition
        final LinearLayout groupsTabView = findViewById(R.id.linearLayoutGroupsTab);
        groupsTabView.setOnClickListener(view -> {
            if (layoutId != R.layout.activity_groups) {
                final Intent newIntent = new Intent(WithFooterActivity.this, GroupsActivity.class);
                startActivity(newIntent);
            }
        });

        LinearLayout eventsTabView = findViewById(R.id.linearLayoutEventsTab);
        eventsTabView.setOnClickListener(view -> {
            if (layoutId != R.layout.activity_events) {
                final Intent newIntent = new Intent(WithFooterActivity.this, EventsActivity.class);
                startActivity(newIntent);
            }
        });

        LinearLayout balancesTabView = findViewById(R.id.linearLayoutBalancesTab);
        balancesTabView.setOnClickListener(view -> {
            if (layoutId != R.layout.activity_balances) {
                final Intent newIntent = new Intent(WithFooterActivity.this, BalancesActivity.class);
                startActivity(newIntent);
            }
        });

        LinearLayout profileTabView = findViewById(R.id.linearLayoutProfileTab);
        profileTabView.setOnClickListener(view -> {
            if (layoutId != R.layout.activity_profile) {
                final Intent newIntent = new Intent(WithFooterActivity.this, ProfileActivity.class);
                startActivity(newIntent);
            }
        });
    }
}
