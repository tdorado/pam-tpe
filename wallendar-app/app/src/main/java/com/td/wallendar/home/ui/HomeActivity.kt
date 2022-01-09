package com.td.wallendar.home.ui

import com.td.wallendar.utils.scheduler.SchedulerProvider
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider
import android.content.SharedPreferences
import com.td.wallendar.AbstractActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.td.wallendar.service.GroupsService
import com.td.wallendar.service.DebtsService
import com.td.wallendar.service.ChargesService
import com.td.wallendar.service.ApplicationUsersService
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.repositories.GroupsRepositoryImpl
import com.td.wallendar.repositories.interfaces.ChargesRepository
import com.td.wallendar.repositories.ChargesRepositoryImpl
import com.td.wallendar.repositories.interfaces.DebtsRepository
import com.td.wallendar.repositories.DebtsRepositoryImpl
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository
import com.td.wallendar.repositories.ApplicationUsersRepositoryImpl
import com.td.wallendar.di.DependenciesContainer
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.di.ProductionDependenciesContainer
import com.td.wallendar.di.DependenciesModule
import com.td.wallendar.dtos.response.ApplicationUserResponse
import com.td.wallendar.dtos.response.ChargeResponse
import com.td.wallendar.dtos.response.DebtResponse
import com.td.wallendar.dtos.response.PaymentResponse
import com.td.wallendar.home.ui.HomeView
import com.td.wallendar.home.groups.OnGroupClickedListener
import com.td.wallendar.home.balances.OnBalanceSettleUpClickedListener
import com.td.wallendar.home.profile.OnShowAliasesClickedListener
import com.td.wallendar.home.profile.OnLogoutClickedListener
import com.td.wallendar.home.balances.OnRemindButtonClickedListener
import com.td.wallendar.home.ui.HomeActivity
import android.widget.ViewFlipper
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.td.wallendar.home.groups.ui.GroupsView
import com.td.wallendar.home.balances.ui.BalancesView
import com.td.wallendar.home.profile.ui.ProfileView
import com.td.wallendar.home.ui.HomePresenter
import com.td.wallendar.home.groups.GroupAdapter
import com.td.wallendar.home.balances.BalanceAdapter
import android.os.Bundle
import com.td.wallendar.R
import android.content.Intent
import android.app.Activity
import android.annotation.SuppressLint
import com.td.wallendar.addgroup.ui.AddGroupActivity
import com.google.android.material.navigation.NavigationBarView
import com.td.wallendar.addcharge.ui.AddChargeActivity
import android.widget.Toast
import com.td.wallendar.group.ui.GroupActivity
import android.content.DialogInterface
import android.content.ActivityNotFoundException
import android.net.Uri
import io.reactivex.disposables.CompositeDisposable
import com.td.wallendar.dtos.request.AddPaymentRequest
import kotlin.jvm.JvmOverloads
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.td.wallendar.home.groups.GroupViewHolder
import android.widget.TextView
import android.widget.LinearLayout
import com.td.wallendar.home.balances.BalanceViewHolder
import com.td.wallendar.group.ui.GroupView
import com.td.wallendar.group.ui.GroupPresenter
import com.td.wallendar.group.GroupHistoryAdapter
import com.td.wallendar.groupbalance.ui.GroupBalanceActivity
import com.td.wallendar.groupmembers.ui.GroupMembersActivity
import com.td.wallendar.addmembers.ui.AddMembersActivity
import com.td.wallendar.group.GroupHistoryViewHolder
import com.td.wallendar.login.ui.LoginView
import com.td.wallendar.login.ui.LoginPresenter
import com.google.android.material.textfield.TextInputEditText
import android.widget.EditText
import com.td.wallendar.register.ui.RegisterActivity
import com.td.wallendar.dtos.request.LoginRequest
import com.td.wallendar.dtos.response.LoginResponse
import com.td.wallendar.utils.mappers.ApplicationUserMapper
import com.td.wallendar.utils.mappers.DebtMapper
import com.td.wallendar.dtos.response.GroupResponse
import com.td.wallendar.utils.mappers.GroupMapper
import com.td.wallendar.utils.mappers.ChargeMapper
import com.td.wallendar.utils.mappers.PaymentMapper
import com.td.wallendar.dtos.response.UserAliasResponse
import com.td.wallendar.utils.mappers.UserAliasMapper
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.SingleSource
import com.td.wallendar.utils.networking.RetrofitUtils
import kotlin.Throws
import com.td.wallendar.utils.networking.RequestException
import com.td.wallendar.utils.networking.NoConnectivityException
import retrofit2.http.GET
import retrofit2.http.POST
import com.td.wallendar.dtos.request.AddGroupRequest
import com.td.wallendar.dtos.request.AddMemberByEmailRequest
import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.dtos.request.AddChargeRequest
import com.td.wallendar.dtos.request.AddApplicationUserRequest
import com.td.wallendar.dtos.request.AddUserAliasRequest
import com.td.wallendar.addgroup.ui.AddGroupView
import com.td.wallendar.addgroup.ui.AddGroupPresenter
import com.google.android.material.textfield.TextInputLayout
import android.text.Editable
import android.view.*
import com.td.wallendar.register.ui.RegisterView
import com.td.wallendar.register.ui.RegisterPresenter
import com.td.wallendar.addcharge.ui.AddChargeView
import android.widget.ArrayAdapter
import com.td.wallendar.addcharge.ui.AddChargePresenter
import android.widget.AutoCompleteTextView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import com.td.wallendar.addmembers.ui.AddMembersView
import com.td.wallendar.addmembers.ui.AddMembersPresenter
import com.td.wallendar.addmembers.MembersAdapter
import com.td.wallendar.addmembers.MembersViewHolder
import com.td.wallendar.groupbalance.ui.GroupBalanceView
import com.td.wallendar.groupbalance.OnGroupSettleUpClickedListener
import com.td.wallendar.groupbalance.OnGroupRemindClickedListener
import com.td.wallendar.groupbalance.ui.GroupBalancePresenter
import com.td.wallendar.groupbalance.GroupBalanceAdapter
import com.td.wallendar.groupbalance.GroupBalanceViewHolder
import com.td.wallendar.groupmembers.ui.GroupMembersView
import com.td.wallendar.groupmembers.ui.GroupMembersPresenter
import com.td.wallendar.groupmembers.GroupMembersAdapter
import com.td.wallendar.groupmembers.GroupMembersViewHolder
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatActivity
import com.td.wallendar.login.ui.LoginActivity
import com.td.wallendar.models.*
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.Shadows
import java.text.DecimalFormat

class HomeActivity : AbstractActivity(), HomeView, OnGroupClickedListener, OnBalanceSettleUpClickedListener, OnShowAliasesClickedListener, OnLogoutClickedListener, OnRemindButtonClickedListener {
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
            val group = data.getExtras().getSerializable("NEW_GROUP") as Group?
            groupAdapter.addToDataset(group)
        }
        if (requestCode == REFRESH && resultCode == RESULT_OK) {
            homePresenter.onViewAttached(loggedUserId)
        }
    }

    private fun createPresenter() {
        homePresenter = lastNonConfigurationInstance as HomePresenter?
        if (homePresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val groupsRepository = dependenciesContainer.groupsRepository
            val debtsRepository = dependenciesContainer.debtsRepository
            val applicationUsersRepository = dependenciesContainer.applicationUsersRepository
            val schedulerProvider = dependenciesContainer.schedulerProvider
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
            menu.findItem(R.id.add_group).isVisible = true
            return true
        }
        menu.findItem(R.id.add_group).isVisible = false
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item.getItemId() == R.id.add_group) {
            startActivityForResult(Intent(this, AddGroupActivity::class.java), REQUEST_ADD_GROUP)
            return true
        }
        return false
    }

    @SuppressLint("NonConstantResourceId")
    private fun setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setSelectedItemId(R.id.groups)
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item: MenuItem? ->
            when (item.getItemId()) {
                R.id.groups -> homePresenter.onGroupsClicked()
                R.id.balances -> homePresenter.onBalancesClicked()
                R.id.profile -> homePresenter.onProfileClicked()
                else -> return@setOnItemSelectedListener false
            }
            true
        })
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
        addChargeFAB.setOnClickListener(View.OnClickListener { view: View? -> startActivityForResult(Intent(this, AddChargeActivity::class.java), REFRESH) })
    }

    private fun setUpGroupsView() {
        groupsView = findViewById(R.id.view_groups)
        groupAdapter = GroupAdapter(loggedUserId)
        groupAdapter.setOnGroupClickedListener(this)
        groupsView.bind(groupAdapter, addChargeFAB)
    }

    private fun setUpBalancesView() {
        balancesView = findViewById(R.id.view_balances)
        balanceAdapter = BalanceAdapter(loggedUserId)
        balanceAdapter.setOnBalanceSettleUpClickedListener(this, this)
        balancesView.bind(balanceAdapter)
    }

    private fun setUpProfileView() {
        profileView = findViewById(R.id.view_profile)
    }

    override fun showGroups() {
        currentView = GROUPS
        invalidateOptionsMenu()
        addChargeFAB.show()
        viewFlipper.setDisplayedChild(GROUPS)
    }

    override fun showBalances() {
        currentView = BALANCES
        invalidateOptionsMenu()
        addChargeFAB.hide()
        viewFlipper.setDisplayedChild(BALANCES)
    }

    override fun showProfile() {
        currentView = PROFILE
        invalidateOptionsMenu()
        addChargeFAB.hide()
        viewFlipper.setDisplayedChild(PROFILE)
    }

    override fun onBackPressed() {
        if (currentView == GROUPS) {
            finishAndRemoveTask()
        } else {
            currentView = GROUPS
            bottomNavigationView.setSelectedItemId(R.id.groups)
        }
    }

    override fun bindGroups(groups: MutableList<Group?>?) {
        groupAdapter.setDataset(groups)
    }

    override fun bindDebts(debts: MutableList<Debt?>?) {
        balanceAdapter.setDataset(debts)
    }

    override fun bindProfile(applicationUser: ApplicationUser?) {
        profileView.bind(applicationUser, this, this)
    }

    override fun updateGroup(group: Group?) {
        groupAdapter.updateInDataset(group)
    }

    override fun removeDebt(debt: Debt?) {
        balanceAdapter.removeFromDataset(debt)
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
            homePresenter.onViewAttached(loggedUserId)
        }
    }

    public override fun onStop() {
        super.onStop()
        if (homePresenter != null) {
            homePresenter.onViewDetached()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (homePresenter != null) {
            homePresenter.onViewDestroyed()
        }
    }

    override fun onGroupClicked(groupId: Long) {
        val intent = Intent(applicationContext, GroupActivity::class.java)
        intent.putExtra("GROUP_ID", groupId)
        startActivityForResult(intent, REFRESH)
    }

    override fun onBalanceSettleUpClick(debt: Debt?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.settle_up_debt))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.setPositiveButton(getString(R.string.yes)) { dialog: DialogInterface?, which: Int ->
            homePresenter.onSettleUpDebtClicked(debt)
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog: DialogInterface?, which: Int -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    override fun onLogoutClicked() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.are_you_sure))
        builder.setPositiveButton(getString(R.string.logout)) { dialog: DialogInterface?, which: Int ->
            logout()
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface?, which: Int -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    override fun onShowAliasesClicked() {
        //TODO make show aliases activity
    }

    override fun onRemindButtonClick(debt: Debt?) {
        val whatsAppIntent = Intent(Intent.ACTION_VIEW)
        whatsAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val phoneNumber = debt.getFrom().phoneNumber
        val messageText = getString(R.string.pay_me_what_you_owe_me, df.format(debt.getAmount()))
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
        private val df: DecimalFormat? = DecimalFormat("0.00")
        private const val GROUPS = 0
        private const val BALANCES = 1
        private const val PROFILE = 2
        private const val REQUEST_ADD_GROUP = 1
        private const val REFRESH = 2
    }
}