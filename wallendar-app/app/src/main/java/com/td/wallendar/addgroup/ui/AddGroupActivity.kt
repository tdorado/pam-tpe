package com.td.wallendar.addgroup.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import com.google.android.material.textfield.TextInputLayout
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.models.Group

class AddGroupActivity : AbstractActivity(), AddGroupView {
    private var addGroupPresenter: AddGroupPresenter? = null
    private var groupTitleInput: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)
        setupActionBar()
        setupGroupTitleInput()
        createPresenter()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return addGroupPresenter
    }

    private fun setupGroupTitleInput() {
        groupTitleInput = findViewById(R.id.group_title)
    }

    private fun createPresenter() {
        addGroupPresenter = lastNonConfigurationInstance as AddGroupPresenter?
        if (addGroupPresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val groupsRepository = dependenciesContainer.getGroupsRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            addGroupPresenter = AddGroupPresenter(this, groupsRepository, schedulerProvider)
        }
    }

    @VisibleForTesting
    fun setAddGroupPresenter(addGroupPresenter: AddGroupPresenter?) {
        this.addGroupPresenter = addGroupPresenter
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white)
        actionBar.setTitle(R.string.add_group)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_group_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_group_done) {
            val editableGroupTitle = groupTitleInput?.editText?.text
            if (editableGroupTitle != null) {
                val groupTitle = editableGroupTitle.toString()
                addGroupPresenter?.createGroup(groupTitle, getLoggedUserId())
            } else {
                // TODO
                Toast.makeText(applicationContext, "PONELE UN TITULO", Toast.LENGTH_LONG).show()
            }
        }
        return false
    }

    override fun onGroupCreated(group: Group) {
        val resultIntent = Intent()
        resultIntent.putExtra("NEW_GROUP", group)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onGroupCreatedWithErrors() {
        Toast.makeText(applicationContext, "Error creating group", Toast.LENGTH_LONG).show()
    }

    public override fun onStop() {
        super.onStop()
        addGroupPresenter?.onViewDetached()
    }
}