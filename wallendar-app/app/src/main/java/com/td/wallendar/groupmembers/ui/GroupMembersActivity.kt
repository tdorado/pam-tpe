package com.td.wallendar.groupmembers.ui

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.groupmembers.GroupMembersAdapter
import com.td.wallendar.models.ApplicationUser

class GroupMembersActivity : AbstractActivity(), GroupMembersView {
    private val GROUP_ID: String = "GROUP_ID"
    private var groupMembersPresenter: GroupMembersPresenter? = null
    private var groupMembersAdapter: GroupMembersAdapter? = null
    private var groupId: Long? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_members)
        groupId = intent.extras?.getLong(GROUP_ID)
        setUpView()
        createPresenter()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    public override fun onStart() {
        super.onStart()
        groupMembersPresenter?.onViewAttached(groupId!!)
    }

    public override fun onStop() {
        super.onStop()
        groupMembersPresenter?.onViewDetached()
    }

    override fun bind(members: MutableList<ApplicationUser>) {
        groupMembersAdapter?.setDataset(members)
    }

    override fun failedToLoadMembers() {
        Toast.makeText(applicationContext,
                getString(R.string.failed_to_load_members), Toast.LENGTH_SHORT).show()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return groupMembersPresenter
    }

    private fun setUpView() {
        groupMembersAdapter = GroupMembersAdapter()
        val recycler = findViewById<RecyclerView?>(R.id.group_members_recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = groupMembersAdapter
        setupActionBar()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setTitle(R.string.members)
    }

    private fun createPresenter() {
        groupMembersPresenter = lastNonConfigurationInstance as GroupMembersPresenter?
        if (groupMembersPresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val groupsRepository = dependenciesContainer.getGroupsRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            groupMembersPresenter = GroupMembersPresenter(this, groupsRepository, schedulerProvider)
        }
    }
}