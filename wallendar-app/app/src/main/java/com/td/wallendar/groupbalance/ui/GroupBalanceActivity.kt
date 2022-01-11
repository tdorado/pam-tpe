package com.td.wallendar.groupbalance.ui

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.groupbalance.GroupBalanceAdapter
import com.td.wallendar.groupbalance.OnGroupRemindClickedListener
import com.td.wallendar.groupbalance.OnGroupSettleUpClickedListener
import com.td.wallendar.models.Debt
import java.text.DecimalFormat

class GroupBalanceActivity : AbstractActivity(), GroupBalanceView, OnGroupSettleUpClickedListener, OnGroupRemindClickedListener {
    private val GROUP_ID: String = "GROUP_ID"
    private var groupBalancePresenter: GroupBalancePresenter? = null
    private var groupBalanceAdapter: GroupBalanceAdapter? = null
    private var groupId: Long = 0
    private var needsToRefresh = false
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_balance)
        groupId = intent.extras?.getLong(GROUP_ID)!!
        setUpView()
        createPresenter()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (needsToRefresh) {
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
        }
        finish()
    }

    public override fun onStart() {
        super.onStart()
        groupBalancePresenter?.onViewAttached(groupId)
    }

    public override fun onStop() {
        super.onStop()
        groupBalancePresenter?.onViewDetached()
    }

    override fun onGroupSettleUpClick(debt: Debt) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.settle_up_debt))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.setPositiveButton(getString(R.string.yes)) { dialog: DialogInterface?, _: Int ->
            groupBalancePresenter?.onSettleUpDebtClicked(debt)
            dialog?.dismiss()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    override fun onGroupRemindClick(debt: Debt) {
        val whatsAppIntent = Intent(Intent.ACTION_VIEW)
        whatsAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val phoneNumber = debt.from.phoneNumber
        val messageText = getString(R.string.pay_me_what_you_owe_me, df.format(debt.amount))
        val deeplink = "http://api.whatsapp.com/send?phone=$phoneNumber&text=$messageText"
        whatsAppIntent.setPackage("com.whatsapp")
        whatsAppIntent.data = Uri.parse(deeplink)
        try {
            startActivity(whatsAppIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(applicationContext, getString(R.string.whatsapp_not_installed), Toast.LENGTH_SHORT).show()
        }
    }

    override fun bind(debts: MutableList<Debt>) {
        groupBalanceAdapter?.setDataset(debts)
    }

    override fun failedToLoadDebts() {
        Toast.makeText(applicationContext,
                getString(R.string.failed_to_load_debts), Toast.LENGTH_SHORT).show()
    }

    override fun failedToSettleUpDebt() {
        Toast.makeText(applicationContext,
                getString(R.string.failed_to_settle_up), Toast.LENGTH_SHORT).show()
    }

    override fun onSettleUpPaymentDone(debt: Debt) {
        if (groupBalanceAdapter != null) {
            needsToRefresh = true
            groupBalanceAdapter?.removeFromDataset(debt)
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return groupBalancePresenter
    }

    private fun setUpView() {
        groupBalanceAdapter = GroupBalanceAdapter(getLoggedUserId())
        groupBalanceAdapter?.setOnGroupSettleUpClickedListener(this)
        groupBalanceAdapter?.setOnGroupRemindClickedListener(this)
        val recycler = findViewById<RecyclerView?>(R.id.group_balance_recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = groupBalanceAdapter
        setupActionBar()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setTitle(R.string.balances)
    }

    private fun createPresenter() {
        groupBalancePresenter = lastNonConfigurationInstance as GroupBalancePresenter?
        if (groupBalancePresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val groupsRepository = dependenciesContainer.getGroupsRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            groupBalancePresenter = GroupBalancePresenter(this, groupsRepository, schedulerProvider)
        }
    }

    companion object {
        private val df: DecimalFormat = DecimalFormat("0.00")
    }
}