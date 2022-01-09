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

class HomePresenter(
        homeView: HomeView?, groupsRepository: GroupsRepository?,
        debtsRepository: DebtsRepository?,
        applicationUsersRepository: ApplicationUsersRepository?,
        schedulerProvider: SchedulerProvider?
) {
    private val homeView: WeakReference<HomeView?>?
    private val groupsRepository: GroupsRepository?
    private val debtsRepository: DebtsRepository?
    private val applicationUsersRepository: ApplicationUsersRepository?
    private val schedulerProvider: SchedulerProvider?
    private val disposable: CompositeDisposable?
    private var debtToSettleUp: Debt? = null
    fun onGroupsClicked() {
        if (homeView.get() != null) {
            homeView.get().showGroups()
        }
    }

    fun onBalancesClicked() {
        if (homeView.get() != null) {
            homeView.get().showBalances()
        }
    }

    fun onProfileClicked() {
        if (homeView.get() != null) {
            homeView.get().showProfile()
        }
    }

    fun onViewAttached(userId: Long) {
        disposable.add(groupsRepository.getGroupsByUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ groups: MutableList<Group?>? -> onGroupsReceived(groups) }) { throwable: Throwable? -> onGroupsError(throwable) })
        disposable.add(debtsRepository.getTotalDebtsByUserId(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ debts: MutableList<Debt?>? -> onDebtsReceived(debts) }) { throwable: Throwable? -> onDebtsError(throwable) })
        disposable.add(applicationUsersRepository.getUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ applicationUser: ApplicationUser? -> onLoggedUserReceived(applicationUser) }) { throwable: Throwable? -> onLoggedUserError(throwable) })
    }

    fun onSettleUpDebtClicked(debt: Debt?) {
        val addPaymentRequest = AddPaymentRequest(debt.getFrom().id,
                debt.getTo().id,
                debt.getAmount())
        debtToSettleUp = debt
        disposable.add(groupsRepository.addPayment(debt.getGroupId(), addPaymentRequest)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ group: Group? -> onPaymentReceived(group) }) { throwable: Throwable? -> onPaymentError(throwable) })
    }

    private fun onGroupsReceived(groups: MutableList<Group?>?) {
        if (homeView.get() != null) {
            homeView.get().bindGroups(groups)
        }
    }

    private fun onGroupsError(throwable: Throwable?) {
        homeView.get().errorGettingGroups()
    }

    private fun onDebtsReceived(debts: MutableList<Debt?>?) {
        if (homeView.get() != null) {
            homeView.get().bindDebts(debts)
        }
    }

    private fun onDebtsError(throwable: Throwable?) {
        homeView.get().errorGettingBalances()
    }

    private fun onLoggedUserReceived(applicationUser: ApplicationUser?) {
        if (homeView.get() != null) {
            homeView.get().bindProfile(applicationUser)
        }
    }

    private fun onLoggedUserError(throwable: Throwable?) {
        homeView.get().errorGettingUser()
    }

    private fun onPaymentReceived(group: Group?) {
        if (homeView.get() != null) {
            if (debtToSettleUp != null) {
                homeView.get().removeDebt(debtToSettleUp)
                debtToSettleUp = null
            }
            homeView.get().updateGroup(group)
        }
    }

    private fun onPaymentError(throwable: Throwable?) {
        homeView.get().errorPayingDebt()
    }

    fun onViewDetached() {
        // solo en esta activity hacemos el clear, porque como el home es singleton a veces se va a necesitar volver a agregar cosas al disposable
        disposable.clear()
    }

    fun onViewDestroyed() {
        // cuando se destruye ahi si se hace el dispose
        disposable.dispose()
    }

    init {
        this.homeView = WeakReference(homeView)
        this.groupsRepository = groupsRepository
        this.debtsRepository = debtsRepository
        this.applicationUsersRepository = applicationUsersRepository
        this.schedulerProvider = schedulerProvider
        disposable = CompositeDisposable()
    }
}