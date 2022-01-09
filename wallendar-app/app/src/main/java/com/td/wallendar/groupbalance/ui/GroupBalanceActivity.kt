package com.td.wallendar.groupbalance.ui

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
import android.view.MenuInflater
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
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.LinearLayout
import android.view.Gravity
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
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.Shadows
import java.text.DecimalFormat

class GroupBalanceActivity : AbstractActivity(), GroupBalanceView, OnGroupSettleUpClickedListener, OnGroupRemindClickedListener {
    private val GROUP_ID: String? = "GROUP_ID"
    private var groupBalancePresenter: GroupBalancePresenter? = null
    private var groupBalanceAdapter: GroupBalanceAdapter? = null
    private var groupId: Long = 0
    private var needsToRefresh = false
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_balance)
        groupId = intent.extras.getLong(GROUP_ID)
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
        groupBalancePresenter.onViewAttached(groupId)
    }

    public override fun onStop() {
        super.onStop()
        groupBalancePresenter.onViewDetached()
    }

    override fun onGroupSettleUpClick(debt: Debt?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.settle_up_debt))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.setPositiveButton(getString(R.string.yes)) { dialog: DialogInterface?, which: Int ->
            groupBalancePresenter.onSettleUpDebtClicked(debt)
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog: DialogInterface?, which: Int -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    override fun onGroupRemindClick(debt: Debt?) {
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

    override fun bind(debts: MutableList<Debt?>?) {
        groupBalanceAdapter.setDataset(debts)
    }

    override fun failedToLoadDebts() {
        Toast.makeText(applicationContext,
                getString(R.string.failed_to_load_debts), Toast.LENGTH_SHORT).show()
    }

    override fun failedToSettleUpDebt() {
        Toast.makeText(applicationContext,
                getString(R.string.failed_to_settle_up), Toast.LENGTH_SHORT).show()
    }

    override fun onSettleUpPaymentDone(debt: Debt?) {
        if (groupBalanceAdapter != null) {
            needsToRefresh = true
            groupBalanceAdapter.removeFromDataset(debt)
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return groupBalancePresenter
    }

    private fun setUpView() {
        groupBalanceAdapter = GroupBalanceAdapter(loggedUserId)
        groupBalanceAdapter.setOnGroupSettleUpClickedListener(this)
        groupBalanceAdapter.setOnGroupRemindClickedListener(this)
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
            val groupsRepository = dependenciesContainer.groupsRepository
            val schedulerProvider = dependenciesContainer.schedulerProvider
            groupBalancePresenter = GroupBalancePresenter(this, groupsRepository, schedulerProvider)
        }
    }

    companion object {
        private val df: DecimalFormat? = DecimalFormat("0.00")
    }
}