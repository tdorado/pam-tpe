package com.td.wallendar.addgroup.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import com.google.android.material.textfield.TextInputLayout
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.addgroup.ui.pickers.DatePickerFragment
import com.td.wallendar.addgroup.ui.pickers.OnDatePickerSelected
import com.td.wallendar.addgroup.ui.pickers.OnTimePickerSelected
import com.td.wallendar.addgroup.ui.pickers.TimePickerFragment
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.models.Group
import java.util.*

class AddGroupActivity : AbstractActivity(), AddGroupView, OnTimePickerSelected, OnDatePickerSelected {
    private val IS_EVENT: String = "IS_EVENT"
    private var addGroupPresenter: AddGroupPresenter? = null
    private var groupTitleInput: TextInputLayout? = null
    private var isEvent: Boolean = false

    private var dateForEvent: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)
        isEvent = intent.extras?.getBoolean(IS_EVENT)!!
        if (isEvent) {
            findViewById<LinearLayout>(R.id.event_inputs).visibility = VISIBLE
            findViewById<Button?>(R.id.pick_date_button)
                    .setOnClickListener { DatePickerFragment(this).show(supportFragmentManager, "datePicker") }
            findViewById<Button?>(R.id.pick_time_button)
                    .setOnClickListener { TimePickerFragment(this).show(supportFragmentManager, "timePicker") }
        }
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
            val eventsRepository = dependenciesContainer.getEventsRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            addGroupPresenter = AddGroupPresenter(this, groupsRepository, eventsRepository, schedulerProvider, isEvent)
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

                if (isEvent) {
                    addGroupPresenter?.createEvent(groupTitle, getLoggedUserId(), dateForEvent)
                } else {
                    addGroupPresenter?.createGroup(groupTitle, getLoggedUserId())
                }
            } else {
                // TODO
                Toast.makeText(applicationContext, "PONELE UN TITULO", Toast.LENGTH_LONG).show()
            }
        }
        return false
    }

    override fun onGroupCreated(group: Group) {
        val resultIntent = Intent()
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

    override fun onTimeSelected(hourOfDay: Int, minute: Int) {
        dateForEvent.set(Calendar.HOUR_OF_DAY, hourOfDay)
        dateForEvent.set(Calendar.MINUTE, minute)
    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {
        dateForEvent.set(Calendar.DAY_OF_MONTH, day)
        dateForEvent.set(Calendar.YEAR, year)
        dateForEvent.set(Calendar.MONTH, month)
    }

}