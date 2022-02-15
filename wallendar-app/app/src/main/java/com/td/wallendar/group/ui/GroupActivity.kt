package com.td.wallendar.group.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.addcharge.ui.AddChargeActivity
import com.td.wallendar.addmembers.ui.AddMembersActivity
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.group.GroupHistoryAdapter
import com.td.wallendar.groupbalance.ui.GroupBalanceActivity
import com.td.wallendar.groupmembers.ui.GroupMembersActivity
import com.td.wallendar.models.Charge
import com.td.wallendar.models.Group
import com.td.wallendar.models.GroupHistory

class GroupActivity : AbstractActivity(), GroupView {
    private var groupPresenter: GroupPresenter? = null
    private var groupHistoryAdapter: GroupHistoryAdapter? = null
    private var recycler: RecyclerView? = null
    private var addChargeFAB: ExtendedFloatingActionButton? = null
    private var groupTitle: TextView? = null

    // Intended to be nullable
    private var groupId: Long? = null
    private var isEvent: Boolean = false
    private var needsToRefresh = false
    private val GROUP_ID: String = "GROUP_ID"
    private val IS_EVENT: String = "IS_EVENT"

    private val NEW_CHARGE: String = "NEW_CHARGE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        groupId = intent.extras?.getLong(GROUP_ID)
        isEvent = intent.extras?.getBoolean(IS_EVENT)!!
        setUpView()
        createPresenter()
    }

    private fun setUpView() {
        groupHistoryAdapter = GroupHistoryAdapter()
        recycler = findViewById(R.id.group_activity_recycler)
        recycler?.setHasFixedSize(true)
        recycler?.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler?.adapter = groupHistoryAdapter
        setUpAddChargeButton(groupId)
        groupTitle = findViewById(R.id.group_title)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // TODO
        findViewById<View?>(R.id.group_events).setOnClickListener {
            Toast.makeText(applicationContext,
                    getString(R.string.feature_not_ready), Toast.LENGTH_SHORT).show()
        }
        findViewById<View?>(R.id.group_events).visibility = if (isEvent) GONE else VISIBLE
        findViewById<LinearLayout?>(R.id.group_linear_buttons).weightSum = if (isEvent) 2F else 3F


        findViewById<View?>(R.id.group_balances).setOnClickListener {
            val intent = Intent(this, GroupBalanceActivity::class.java)
            intent.putExtra(GROUP_ID, groupId)
            intent.putExtra(IS_EVENT, isEvent)
            startActivityForResult(intent, REFRESH)
        }
        findViewById<View?>(R.id.group_members).setOnClickListener {
            val intent = Intent(this, GroupMembersActivity::class.java)
            intent.putExtra(GROUP_ID, groupId)
            intent.putExtra(IS_EVENT, isEvent)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_CHARGE && resultCode == RESULT_OK) {
            val charge = data?.extras?.getSerializable(NEW_CHARGE) as Charge?
            groupHistoryAdapter?.addToDataset(charge!!)
            needsToRefresh = true
        } else if (requestCode == REQUEST_ADD_MEMBERS && resultCode == RESULT_OK) {
            Toast.makeText(applicationContext, getString(R.string.members_added_successful), Toast.LENGTH_SHORT).show()
        } else if (requestCode == REFRESH && resultCode == RESULT_OK) {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_members_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.add_members)?.isVisible = true
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_members) {
            val addMembersIntent = Intent(this, AddMembersActivity::class.java)
            addMembersIntent.putExtra(GROUP_ID, groupId)
            startActivityForResult(addMembersIntent, REQUEST_ADD_MEMBERS)
            return true
        }
        return false
    }

    private fun createPresenter() {
        groupPresenter = lastNonConfigurationInstance as GroupPresenter?
        if (groupPresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val groupsRepository = dependenciesContainer.getGroupsRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            groupPresenter = GroupPresenter(this, groupsRepository, schedulerProvider)
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return groupPresenter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun bindGroup(group: Group) {
        groupTitle?.setText(group.title)
    }

    override fun bindGroupHistory(historic: MutableList<GroupHistory>) {
        groupHistoryAdapter?.setDataset(historic)
    }

    override fun onGroupLoadError() {
        Toast.makeText(applicationContext, "There was an error loading the group, try again later", Toast.LENGTH_LONG).show()
    }

    private fun setUpAddChargeButton(groupId: Long?) {
        addChargeFAB = findViewById(R.id.add_charge_fab)
        addChargeFAB?.setOnClickListener {
            val intent = Intent(this, AddChargeActivity::class.java)
            intent.putExtra(GROUP_ID, groupId)
            intent.putExtra(IS_EVENT, isEvent)
            startActivityForResult(intent, REQUEST_ADD_CHARGE)
        }
        // Shrink floating button when scrolling, extend at the top. Just fancy fab
        recycler?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addChargeFAB?.extend()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && addChargeFAB?.isExtended == true) {
                    addChargeFAB?.shrink()
                }
            }
        })
    }

    public override fun onStart() {
        super.onStart()
        groupPresenter?.onViewAttached(groupId!!)
    }

    public override fun onStop() {
        super.onStop()
        groupPresenter?.onViewDetached()
    }

    public override fun onDestroy() {
        super.onDestroy()
        groupPresenter?.onViewDestroyed()
    }

    override fun onBackPressed() {
        if (needsToRefresh) {
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
        }
        finish()
    }

    companion object {
        private const val REQUEST_ADD_CHARGE = 1
        private const val REQUEST_ADD_MEMBERS = 2
        private const val REFRESH = 3
    }
}