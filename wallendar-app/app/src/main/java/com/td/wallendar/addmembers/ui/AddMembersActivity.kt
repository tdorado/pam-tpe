package com.td.wallendar.addmembers.ui

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
import com.td.wallendar.models.UserAlias
import com.td.wallendar.models.Debt
import com.td.wallendar.models.ApplicationUser
import com.td.wallendar.home.ui.HomeView
import com.td.wallendar.home.groups.OnGroupClickedListener
import com.td.wallendar.home.balances.OnBalanceSettleUpClickedListener
import com.td.wallendar.home.profile.OnShowAliasesClickedListener
import com.td.wallendar.home.profile.OnLogoutClickedListener
import com.td.wallendar.home.balances.OnRemindButtonClickedListener
import com.td.wallendar.home.ui.HomeActivity
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
import com.td.wallendar.group.ui.GroupActivity
import android.content.DialogInterface
import android.content.ActivityNotFoundException
import io.reactivex.disposables.CompositeDisposable
import com.td.wallendar.dtos.request.AddPaymentRequest
import kotlin.jvm.JvmOverloads
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.td.wallendar.home.groups.GroupViewHolder
import com.td.wallendar.home.balances.BalanceViewHolder
import com.td.wallendar.models.GroupHistory
import com.td.wallendar.group.ui.GroupView
import com.td.wallendar.group.ui.GroupPresenter
import com.td.wallendar.group.GroupHistoryAdapter
import com.td.wallendar.groupbalance.ui.GroupBalanceActivity
import com.td.wallendar.groupmembers.ui.GroupMembersActivity
import com.td.wallendar.models.Charge
import com.td.wallendar.addmembers.ui.AddMembersActivity
import com.td.wallendar.group.GroupHistoryViewHolder
import com.td.wallendar.models.GroupHistoryType
import com.td.wallendar.login.ui.LoginView
import com.td.wallendar.login.ui.LoginPresenter
import com.google.android.material.textfield.TextInputEditText
import com.td.wallendar.register.ui.RegisterActivity
import com.td.wallendar.dtos.request.LoginRequest
import com.td.wallendar.dtos.response.LoginResponse
import com.td.wallendar.utils.mappers.ApplicationUserMapper
import com.td.wallendar.utils.mappers.DebtMapper
import com.td.wallendar.dtos.response.GroupResponse
import com.td.wallendar.utils.mappers.GroupMapper
import com.td.wallendar.utils.mappers.ChargeMapper
import com.td.wallendar.utils.mappers.PaymentMapper
import com.td.wallendar.models.Payment
import com.td.wallendar.dtos.response.UserAliasResponse
import com.td.wallendar.utils.mappers.UserAliasMapper
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.SingleSource
import com.td.wallendar.utils.networking.RetrofitUtils
import kotlin.Throws
import com.td.wallendar.utils.networking.RequestException
import com.td.wallendar.utils.networking.NoConnectivityException
import com.td.wallendar.models.ChargeType
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
import android.widget.*
import com.td.wallendar.register.ui.RegisterView
import com.td.wallendar.register.ui.RegisterPresenter
import com.td.wallendar.addcharge.ui.AddChargeView
import com.td.wallendar.addcharge.ui.AddChargePresenter
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.VisibleForTesting
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
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.Shadows
import java.util.*

class AddMembersActivity : AbstractActivity(), AddMembersView {
    private var addMembersPresenter: AddMembersPresenter? = null
    private var memberInputLayout: TextInputLayout? = null
    private var checkToAddMember: Button? = null
    private var recyclerView: RecyclerView? = null
    private var membersAdapter: MembersAdapter? = null
    private val members: MutableList<String?>? = ArrayList()
    private val NO_GROUP_ID: Long = -1
    private val GROUP_ID: String? = "GROUP_ID"
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
            findViewById<View?>(R.id.submit_members).setOnClickListener { view: View? -> addMembersPresenter.submitMembers(groupId, members) }
        }
    }

    private fun setupAdapter() {
        membersAdapter = MembersAdapter(members)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.current_members_added)
        recyclerView.setLayoutManager(LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false))
        recyclerView.setAdapter(membersAdapter)
    }

    private fun setupAddMember() {
        checkToAddMember = findViewById(R.id.add_member_button)
        checkToAddMember.setOnClickListener(View.OnClickListener { view: View? ->
            val currentMember = Objects.requireNonNull(memberInputLayout.getEditText()).text.toString()
            if (!currentMember.isEmpty()) {
                memberInputLayout.getEditText().getText().clear()
                membersAdapter.addToDataset(currentMember)
            } else {
                Toast.makeText(applicationContext, "You must write an email.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupInput() {
        memberInputLayout = findViewById(R.id.member_layout)
    }

    private fun createPresenter() {
        addMembersPresenter = lastNonConfigurationInstance as AddMembersPresenter?
        if (addMembersPresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val groupsRepository = dependenciesContainer.groupsRepository
            val schedulerProvider = dependenciesContainer.schedulerProvider
            addMembersPresenter = AddMembersPresenter(this, groupsRepository, schedulerProvider)
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
    override fun onMembersAddedSuccessfully(groupId: Long) {
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
        addMembersPresenter.onViewDetached()
    }
}