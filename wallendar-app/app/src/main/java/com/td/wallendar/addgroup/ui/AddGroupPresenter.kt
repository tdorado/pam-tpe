package com.td.wallendar.addgroup.ui

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
import android.view.MenuInflater
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
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.LinearLayout
import android.view.Gravity
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
import java.lang.ref.WeakReference

class AddGroupPresenter(addGroupView: AddGroupView?, groupsRepository: GroupsRepository?, schedulerProvider: SchedulerProvider?) {
    private val view: WeakReference<AddGroupView?>?
    private val groupsRepository: GroupsRepository?
    private val disposable: CompositeDisposable?
    private val schedulerProvider: SchedulerProvider?
    fun createGroup(groupTitle: String?, ownerId: Long?) {
        disposable.add(groupsRepository.createGroup(AddGroupRequest(groupTitle, ownerId))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ group: Group? -> onGroupCreatedSuccessfully(group) }) { throwable: Throwable? -> OnGroupCreatedWithError(throwable) })
    }

    private fun onGroupCreatedSuccessfully(group: Group?) {
        view.get().onGroupCreated(group)
    }

    private fun OnGroupCreatedWithError(throwable: Throwable?) {
        view.get().onGroupCreatedWithErrors()
    }

    fun onViewDetached() {
        disposable.dispose()
    }

    init {
        view = WeakReference(addGroupView)
        this.groupsRepository = groupsRepository
        disposable = CompositeDisposable()
        this.schedulerProvider = schedulerProvider
    }
}