package com.td.wallendar.home.ui

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.addcharge.ui.AddChargeActivity
import com.td.wallendar.addgroup.ui.AddGroupActivity
import com.td.wallendar.debtdetail.ui.DebtDetailActivity
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.group.ui.GroupActivity
import com.td.wallendar.home.balances.BalanceAdapter
import com.td.wallendar.home.balances.OnBalanceSettleUpClickedListener
import com.td.wallendar.home.balances.OnDetailDebtClickedListener
import com.td.wallendar.home.balances.OnRemindButtonClickedListener
import com.td.wallendar.home.balances.ui.BalancesView
import com.td.wallendar.home.groups.GroupAdapter
import com.td.wallendar.home.groups.OnGroupClickedListener
import com.td.wallendar.home.groups.ui.GroupsView
import com.td.wallendar.home.profile.OnLogoutClickedListener
import com.td.wallendar.home.profile.OnShowAliasesClickedListener
import com.td.wallendar.home.profile.ui.ProfileView
import com.td.wallendar.models.*
import java.text.DecimalFormat

class HomeActivity : AbstractActivity(), HomeView, OnGroupClickedListener, OnBalanceSettleUpClickedListener, OnShowAliasesClickedListener, OnLogoutClickedListener, OnRemindButtonClickedListener, OnDetailDebtClickedListener {
    private var currentView = GROUPS
    private var viewFlipper: ViewFlipper? = null
    private var addChargeFAB: ExtendedFloatingActionButton? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private var groupsView: GroupsView? = null
    private var balancesView: BalancesView? = null
    private var profileView: ProfileView? = null
    private var homePresenter: HomePresenter? = null
    private var groupAdapter: GroupAdapter? = null
    private var balanceAdapter: BalanceAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (!checkIfUserLogged()) {
            return
        }
        createPresenter()
        setUpViews()
        setUpBottomNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_GROUP && resultCode == RESULT_OK) {
            val group = data?.extras?.getSerializable("NEW_GROUP") as Group?
            groupAdapter?.addToDataset(group!!) // FIXME
        }
        if (requestCode == REFRESH && resultCode == RESULT_OK) {
            homePresenter?.onViewAttached(getLoggedUserId())
        }
    }

    private fun createPresenter() {
        homePresenter = lastNonConfigurationInstance as HomePresenter?
        if (homePresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val groupsRepository = dependenciesContainer.getGroupsRepository()
            val debtsRepository = dependenciesContainer.getDebtsRepository()
            val applicationUsersRepository = dependenciesContainer.getApplicationUsersRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            homePresenter = HomePresenter(this, groupsRepository, debtsRepository,
                    applicationUsersRepository, schedulerProvider)
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return homePresenter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (currentView == GROUPS) {
            menu?.findItem(R.id.add_group)?.isVisible = true
            return true
        }
        menu?.findItem(R.id.add_group)?.isVisible = false
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_group) {
            startActivityForResult(Intent(this, AddGroupActivity::class.java), REQUEST_ADD_GROUP)
            return true
        }
        return false
    }

    @SuppressLint("NonConstantResourceId")
    private fun setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView?.selectedItemId = R.id.groups
        bottomNavigationView?.setOnItemSelectedListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.groups -> homePresenter?.onGroupsClicked()
                R.id.balances -> homePresenter?.onBalancesClicked()
                R.id.profile -> homePresenter?.onProfileClicked()
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    private fun setUpViews() {
        viewFlipper = findViewById(R.id.view_flipper)
        setUpAddChargeButton()
        setUpGroupsView()
        setUpBalancesView()
        setUpProfileView()
    }

    private fun setUpAddChargeButton() {
        addChargeFAB = findViewById(R.id.add_charge_fab)
        addChargeFAB?.setOnClickListener({ view: View? -> startActivityForResult(Intent(this, AddChargeActivity::class.java), REFRESH) })
    }

    private fun setUpGroupsView() {
        groupsView = findViewById(R.id.view_groups)
        groupAdapter = GroupAdapter(getLoggedUserId())
        groupAdapter?.setOnGroupClickedListener(this)
        groupsView?.bind(groupAdapter!!, addChargeFAB!!)
    }

    private fun setUpBalancesView() {
        balancesView = findViewById(R.id.view_balances)
        balanceAdapter = BalanceAdapter(getLoggedUserId())
        balanceAdapter?.setOnBalanceSettleUpClickedListener(this, this, this)
        balancesView?.bind(balanceAdapter!!)
    }

    private fun setUpProfileView() {
        profileView = findViewById(R.id.view_profile)
    }

    override fun showGroups() {
        currentView = GROUPS
        invalidateOptionsMenu()
        addChargeFAB?.show()
        viewFlipper?.displayedChild = GROUPS
    }

    override fun showBalances() {
        currentView = BALANCES
        invalidateOptionsMenu()
        addChargeFAB?.hide()
        viewFlipper?.displayedChild = BALANCES
    }

    override fun showProfile() {
        currentView = PROFILE
        invalidateOptionsMenu()
        addChargeFAB?.hide()
        viewFlipper?.displayedChild = PROFILE
    }

    override fun onBackPressed() {
        if (currentView == GROUPS) {
            finishAndRemoveTask()
        } else {
            currentView = GROUPS
            bottomNavigationView?.selectedItemId = R.id.groups
        }
    }

    override fun bindGroups(groups: MutableList<Group>) {
        groupAdapter?.setDataset(groups)
    }

    override fun bindDebts(debts: MutableList<Debt>) {
        balanceAdapter?.setDataset(debts)
    }

    override fun bindProfile(applicationUser: ApplicationUser) {
        profileView?.bind(applicationUser, this, this)
    }

    override fun updateGroup(group: Group) {
        groupAdapter?.updateInDataset(group)
    }

    override fun removeDebt(debt: Debt) {
        balanceAdapter?.removeFromDataset(debt)
    }

    override fun errorGettingUser() {
        Toast.makeText(applicationContext, getString(R.string.login_error), Toast.LENGTH_LONG).show()
        logout()
    }

    override fun errorGettingGroups() {
        Toast.makeText(applicationContext, getString(R.string.error_getting_groups), Toast.LENGTH_LONG).show()
    }

    override fun errorGettingBalances() {
        Toast.makeText(applicationContext, getString(R.string.error_getting_balances), Toast.LENGTH_LONG).show()
    }

    override fun errorPayingDebt() {
        Toast.makeText(applicationContext, getString(R.string.error_paying_debt), Toast.LENGTH_LONG).show()
    }

    public override fun onStart() {
        super.onStart()
        if (homePresenter != null) {
            homePresenter?.onViewAttached(getLoggedUserId())
        }
    }

    public override fun onStop() {
        super.onStop()
        if (homePresenter != null) {
            homePresenter?.onViewDetached()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (homePresenter != null) {
            homePresenter?.onViewDestroyed()
        }
    }

    override fun onGroupClicked(groupId: Long) {
        val intent = Intent(applicationContext, GroupActivity::class.java)
        intent.putExtra("GROUP_ID", groupId)
        startActivityForResult(intent, REFRESH)
    }

    override fun onBalanceSettleUpClick(debt: Debt) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.settle_up_debt))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.setPositiveButton(getString(R.string.yes)) { dialog: DialogInterface?, _: Int ->
            homePresenter?.onSettleUpDebtClicked(debt)
            dialog?.dismiss()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    override fun onLogoutClicked() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.are_you_sure))
        builder.setPositiveButton(getString(R.string.logout)) { dialog: DialogInterface?, _: Int ->
            logout()
            dialog?.dismiss()
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    override fun onShowAliasesClicked() {
        //TODO make show aliases activity
    }

    override fun onRemindButtonClick(debt: Debt) {
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

    companion object {
        private val df: DecimalFormat = DecimalFormat("0.00")
        private const val GROUPS = 0
        private const val BALANCES = 1
        private const val PROFILE = 2
        private const val REQUEST_ADD_GROUP = 1
        private const val REFRESH = 2
    }

    override fun onDetailDebtClickedListener(debtId: Long) {
        val intent = Intent(applicationContext, DebtDetailActivity::class.java)
        intent.putExtra("DEBT_ID", debtId)
        startActivityForResult(intent, REFRESH)
    }
}