package com.td.wallendar.group.ui

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

class GroupActivity : AbstractActivity(), GroupView {
    private var groupPresenter: GroupPresenter? = null
    private var groupHistoryAdapter: GroupHistoryAdapter? = null
    private var recycler: RecyclerView? = null
    private var addChargeFAB: ExtendedFloatingActionButton? = null
    private var groupTitle: TextView? = null

    // Intended to be nullable
    private var groupId: Long? = null
    private var needsToRefresh = false
    private val GROUP_ID: String? = "GROUP_ID"
    private val NEW_CHARGE: String? = "NEW_CHARGE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        groupId = intent.extras.getLong(GROUP_ID)
        setUpView()
        createPresenter()
    }

    private fun setUpView() {
        groupHistoryAdapter = GroupHistoryAdapter()
        recycler = findViewById(R.id.group_activity_recycler)
        recycler.setHasFixedSize(true)
        recycler.setLayoutManager(LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false))
        recycler.setAdapter(groupHistoryAdapter)
        setUpAddChargeButton(groupId)
        groupTitle = findViewById(R.id.group_title)
        val actionBar = supportActionBar
        actionBar.setDisplayHomeAsUpEnabled(true)

        // TODO
        findViewById<View?>(R.id.group_events).setOnClickListener { view: View? ->
            Toast.makeText(applicationContext,
                    getString(R.string.feature_not_ready), Toast.LENGTH_SHORT).show()
        }
        findViewById<View?>(R.id.group_balances).setOnClickListener { view: View? ->
            val intent = Intent(this, GroupBalanceActivity::class.java)
            intent.putExtra(GROUP_ID, groupId)
            startActivityForResult(intent, REFRESH)
        }
        findViewById<View?>(R.id.group_members).setOnClickListener { view: View? ->
            val intent = Intent(this, GroupMembersActivity::class.java)
            intent.putExtra(GROUP_ID, groupId)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_CHARGE && resultCode == RESULT_OK) {
            val charge = data.getExtras().getSerializable(NEW_CHARGE) as Charge?
            groupHistoryAdapter.addToDataset(charge)
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
        menu.findItem(R.id.add_members).isVisible = true
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item.getItemId() == R.id.add_members) {
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
            val groupsRepository = dependenciesContainer.groupsRepository
            val schedulerProvider = dependenciesContainer.schedulerProvider
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

    override fun bindGroup(group: Group?) {
        groupTitle.setText(group.getTitle())
    }

    override fun bindGroupHistory(historic: MutableList<GroupHistory?>?) {
        groupHistoryAdapter.setDataset(historic)
    }

    override fun onGroupLoadError() {
        Toast.makeText(applicationContext, "There was an error loading the group, try again later", Toast.LENGTH_LONG).show()
    }

    private fun setUpAddChargeButton(groupId: Long?) {
        addChargeFAB = findViewById(R.id.add_charge_fab)
        addChargeFAB.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(this, AddChargeActivity::class.java)
            intent.putExtra(GROUP_ID, groupId)
            startActivityForResult(intent, REQUEST_ADD_CHARGE)
        })
        // Shrink floating button when scrolling, extend at the top. Just fancy fab
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addChargeFAB.extend()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && addChargeFAB.isExtended()) {
                    addChargeFAB.shrink()
                }
            }
        })
    }

    public override fun onStart() {
        super.onStart()
        groupPresenter.onViewAttached(groupId)
    }

    public override fun onStop() {
        super.onStop()
        groupPresenter.onViewDetached()
    }

    public override fun onDestroy() {
        super.onDestroy()
        groupPresenter.onViewDestroyed()
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