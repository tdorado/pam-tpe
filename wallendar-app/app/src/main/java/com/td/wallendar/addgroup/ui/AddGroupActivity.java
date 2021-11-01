package com.td.wallendar.addgroup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.home.ui.HomeActivity;
import com.td.wallendar.home.ui.HomePresenter;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.GroupsRepositoryImpl;

public class AddGroupActivity extends AbstractActivity implements AddGroupView {

    private AddGroupPresenter addGroupPresenter;

    private TextInputLayout groupTitleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_group);

        setupActionBar();
        setupGroupTitleInput();

        createPresenter();
    }

    private void setupGroupTitleInput() {
        groupTitleInput = findViewById(R.id.group_title);
    }

    private void createPresenter() {
        addGroupPresenter = (AddGroupPresenter) getLastNonConfigurationInstance();

        if (addGroupPresenter == null) {
            addGroupPresenter = new AddGroupPresenter(this, new GroupsRepositoryImpl());
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white);
        actionBar.setTitle(R.string.add_group);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_group_done) {
            final Editable editableGroupTitle = groupTitleInput.getEditText().getText();
            if (editableGroupTitle != null) {
                final String groupTitle = editableGroupTitle.toString();
                addGroupPresenter.createGroup(groupTitle, getUserId());
            } else {
                // TODO
                Toast.makeText(getApplicationContext(), "PONELE UN TITULO", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    // TODO
    @Override
    public void onGroupCreated(final Group group) {
        final Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_GROUP", group);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    // TODO
    @Override
    protected Long getUserId() {
        return 1L;
    }
}
