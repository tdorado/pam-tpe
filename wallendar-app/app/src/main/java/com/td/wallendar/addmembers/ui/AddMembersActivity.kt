package com.td.wallendar.addmembers.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.addmembers.MembersAdapter
import com.td.wallendar.di.DependenciesContainerLocator
import java.util.*

class AddMembersActivity : AbstractActivity(), AddMembersView {
    private var addMembersPresenter: AddMembersPresenter? = null
    private var memberInputLayout: TextInputLayout? = null
    private var checkToAddMember: Button? = null
    private var recyclerView: RecyclerView? = null
    private var membersAdapter: MembersAdapter? = null
    private val members: MutableList<String> = ArrayList()
    private val NO_GROUP_ID: Long = -1
    private val GROUP_ID: String = "GROUP_ID"
    private val IS_EVENT: String = "IS_EVENT"
    private var isEvent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_members)
        setupAdapter()
        setupActionBar()
        setupInput()
        setupAddMember()
        setupSubmitMembers()
        setupRecyclerView()
        createPresenter()
    }

    private fun setupSubmitMembers() {
        val groupId = intent.getLongExtra(GROUP_ID, -1)
        if (groupId == NO_GROUP_ID) {
            // TODO error
        } else {
            findViewById<View?>(R.id.submit_members).setOnClickListener { addMembersPresenter?.submitMembers(groupId, members) }
        }
    }

    private fun setupAdapter() {
        membersAdapter = MembersAdapter(members)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.current_members_added)
        recyclerView?.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = membersAdapter
    }

    private fun setupAddMember() {
        checkToAddMember = findViewById(R.id.add_member_button)
        checkToAddMember?.setOnClickListener {
            val currentMember = Objects.requireNonNull(memberInputLayout?.editText)?.text.toString()
            if (currentMember.isNotEmpty()) {
                memberInputLayout?.editText?.text?.clear()
                membersAdapter?.addToDataset(currentMember)
            } else {
                Toast.makeText(applicationContext, "You must write an email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupInput() {
        memberInputLayout = findViewById(R.id.member_layout)
    }

    private fun createPresenter() {
        addMembersPresenter = lastNonConfigurationInstance as AddMembersPresenter?
        if (addMembersPresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val groupsRepository = dependenciesContainer.getGroupsRepository()
            val eventsRepository = dependenciesContainer.getEventsRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            isEvent = intent.extras?.getBoolean(IS_EVENT)!!
            addMembersPresenter = AddMembersPresenter(this, groupsRepository, eventsRepository, schedulerProvider, isEvent)
        }
    }

    @VisibleForTesting
    fun setPresenter(addMembersPresenter: AddMembersPresenter?) {
        this.addMembersPresenter = addMembersPresenter
    }

    @VisibleForTesting
    fun getPresenter(): AddMembersPresenter? {
        return addMembersPresenter
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return addMembersPresenter
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white)
        actionBar.setTitle(R.string.add_members)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // TODO we are not using the groupId param right now, may be useful in some borders cases
    override fun onMembersAddedSuccessfully() {
        val resultIntent = Intent()
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onMembersAddedWithError() {
        Toast.makeText(applicationContext, "There was an error adding members", Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    public override fun onStop() {
        super.onStop()
        addMembersPresenter?.onViewDetached()
    }
}