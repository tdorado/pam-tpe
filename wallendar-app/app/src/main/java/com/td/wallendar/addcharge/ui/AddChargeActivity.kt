package com.td.wallendar.addcharge.ui

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
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

class AddChargeActivity : AbstractActivity(), AddChargeView {
    private var adapter: ArrayAdapter<String?>? = null
    private val stringGroupMap: MutableMap<String?, Group?>? = HashMap()
    private var chargeTitleInput: TextInputLayout? = null
    private var chargeAmountInput: TextInputLayout? = null
    private var groupSelected: String? = null
    private var addChargePresenter: AddChargePresenter? = null
    private var editTextFilledExposedDropdown: AutoCompleteTextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_charge)
        setupActionBar()
        setupInputs()
        createPresenter()
        setupGroupId()
    }

    private fun setupGroupId() {
        val extras = intent.extras
        if (extras != null) {
            val groupId = extras.getLong("GROUP_ID")
            addChargePresenter.setGroupId(groupId)
        }
    }

    private fun setupInputs() {
        chargeTitleInput = findViewById(R.id.charge_title)
        chargeAmountInput = findViewById(R.id.charge_amount)
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white)
        actionBar.setTitle(R.string.add_charge)
    }

    private fun createPresenter() {
        addChargePresenter = lastNonConfigurationInstance as AddChargePresenter?
        if (addChargePresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val chargesRepository = dependenciesContainer.chargesRepository
            val groupsRepository = dependenciesContainer.groupsRepository
            val schedulerProvider = dependenciesContainer.schedulerProvider
            addChargePresenter = AddChargePresenter(this, chargesRepository,
                    groupsRepository, schedulerProvider)
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return addChargePresenter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_charge_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item.getItemId() == R.id.add_charge_done) {
            val editableChargeTitle = chargeTitleInput.getEditText().getText()
            val editableChargeAmount = chargeAmountInput.getEditText().getText()
            if (editableChargeTitle != null && editableChargeAmount != null && groupSelected != null) {
                val chargeTitle = editableChargeTitle.toString()
                val chargeAmount = editableChargeAmount.toString()
                val chargeAmountValue: Double
                chargeAmountValue = try {
                    chargeAmount.toDouble()
                } catch (e: Exception) {
                    //TODO
                    Toast.makeText(applicationContext, "TIENE QUE SER DOUBLE", Toast.LENGTH_LONG).show()
                    return false
                }
                val groupId = stringGroupMap.get(groupSelected).getId()
                addChargePresenter.addCharge(groupId, AddChargeRequest(chargeTitle,
                        loggedUserId, chargeAmountValue))
                return true
            } else {
                //TODO
                Toast.makeText(applicationContext, "PONELE TITULO", Toast.LENGTH_LONG).show()
            }
        }
        return false
    }

    override fun chargeError() {
        Toast.makeText(applicationContext, "Error adding charge", Toast.LENGTH_LONG).show()
    }

    override fun chargeAddedOk(charge: Charge?) {
        Toast.makeText(applicationContext, "Charge added ok", Toast.LENGTH_LONG).show()
        val resultIntent = Intent()
        resultIntent.putExtra("NEW_CHARGE", charge)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onGroupsLoadOk(groups: MutableList<Group?>?) {
        val stringGroups: MutableList<String?> = ArrayList()
        for (group in groups) {
            stringGroups.add(group.getTitle())
            stringGroupMap[group.getTitle()] = group
        }
        adapter = ArrayAdapter(this, R.layout.dropdown_menu_popup_item, stringGroups)
        editTextFilledExposedDropdown = findViewById(R.id.group_charge_dropdown)
        editTextFilledExposedDropdown.setAdapter(adapter)
        editTextFilledExposedDropdown.setOnItemClickListener(OnItemClickListener { adapterView: AdapterView<*>?, view: View?, i: Int, l: Long -> groupSelected = adapter.getItem(i) })
    }

    override fun onGroupsLoadError() {
        Toast.makeText(applicationContext, "Groups load error", Toast.LENGTH_LONG).show()
    }

    override fun setSelectedGroup(groupId: Long?) {
        for (group in stringGroupMap.values) {
            if (group.getId() == groupId) {
                groupSelected = group.getTitle()
            }
        }
        editTextFilledExposedDropdown.setText(groupSelected, false)
    }

    public override fun onStart() {
        super.onStart()
        addChargePresenter.onViewAttached(loggedUserId)
    }

    public override fun onStop() {
        super.onStop()
        addChargePresenter.onViewDetached()
    }
}