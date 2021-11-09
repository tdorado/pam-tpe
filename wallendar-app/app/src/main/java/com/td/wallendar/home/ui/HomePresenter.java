package com.td.wallendar.home.ui;

import com.td.wallendar.dtos.request.AddPaymentRequest;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Group;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.interfaces.GroupsRepository;
import com.td.wallendar.utils.networking.RequestException;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class HomePresenter {
    private final WeakReference<HomeView> homeView;
    private final GroupsRepository groupsRepository;
    private final DebtsRepository debtsRepository;
    private final ApplicationUsersRepository applicationUsersRepository;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable disposable;

    private Debt debtToSettleUp;

    public HomePresenter(final HomeView homeView, final GroupsRepository groupsRepository,
                         final DebtsRepository debtsRepository,
                         final ApplicationUsersRepository applicationUsersRepository,
                         final SchedulerProvider schedulerProvider) {
        this.homeView = new WeakReference<>(homeView);
        this.groupsRepository = groupsRepository;
        this.debtsRepository = debtsRepository;
        this.applicationUsersRepository = applicationUsersRepository;
        this.schedulerProvider = schedulerProvider;
        this.disposable = new CompositeDisposable();
    }

    public void onGroupsClicked() {
        if (homeView.get() != null) {
            homeView.get().showGroups();
        }
    }

    public void onBalancesClicked() {
        if (homeView.get() != null) {
            homeView.get().showBalances();
        }
    }

    public void onProfileClicked() {
        if (homeView.get() != null) {
            homeView.get().showProfile();
        }
    }

    public void onViewAttached(long userId) {
        disposable.add(groupsRepository.getGroupsByUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onGroupsReceived, this::onGroupsError));
        disposable.add(debtsRepository.getTotalDebtsByUserId(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onDebtsReceived, this::onDebtsError));
        disposable.add(applicationUsersRepository.getUser(userId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onLoggedUserReceived, this::onLoggedUserError));
    }

    public void onSettleUpDebtClicked(Debt debt) {
        AddPaymentRequest addPaymentRequest = new AddPaymentRequest(debt.getFrom().getId(),
                debt.getTo().getId(),
                debt.getAmount());
        debtToSettleUp = debt;
        disposable.add(groupsRepository.addPayment(debt.getGroupId(), addPaymentRequest)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onPaymentReceived, this::onPaymentError));
    }

    private void onGroupsReceived(List<Group> groups) {
        if (homeView.get() != null) {
            homeView.get().bindGroups(groups);
        }
    }

    private void onGroupsError(Throwable throwable) {
        homeView.get().errorGettingGroups();
    }

    private void onDebtsReceived(List<Debt> debts) {
        if (homeView.get() != null) {
            homeView.get().bindDebts(debts);
        }
    }

    private void onDebtsError(Throwable throwable) {
        homeView.get().errorGettingBalances();
    }

    private void onLoggedUserReceived(ApplicationUser applicationUser) {
        if (homeView.get() != null) {
            homeView.get().bindProfile(applicationUser);
        }
    }

    private void onLoggedUserError(Throwable throwable) {
        if (throwable instanceof RequestException) {
            RequestException exception = (RequestException) throwable;
            if (exception.getCode() == 404) {
                homeView.get().errorGettingUser();
            }
        }
    }

    private void onPaymentReceived(Group group) {
        if (homeView.get() != null) {
            if (debtToSettleUp != null) {
                homeView.get().removeDebt(debtToSettleUp);
                debtToSettleUp = null;
            }
            homeView.get().updateGroup(group);
        }
    }

    private void onPaymentError(Throwable throwable) {
        System.out.println(throwable);
    }

    public void onViewDetached() {
        // solo en esta activity hacemos el clear, porque como el home es singleton a veces se va a necesitar volver a agregar cosas al disposable
        disposable.clear();
    }

    public void onViewDestroyed() {
        // cuando se destruye ahi si se hace el dispose
        disposable.dispose();
    }
}
