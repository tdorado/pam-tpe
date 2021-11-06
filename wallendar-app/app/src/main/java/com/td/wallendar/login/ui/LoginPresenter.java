package com.td.wallendar.login.ui;

import com.td.wallendar.dtos.request.LoginRequest;
import com.td.wallendar.dtos.response.LoginResponse;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter {
    private final WeakReference<LoginView> loginView;
    private final ApplicationUsersRepository applicationUsersRepository;
    private final CompositeDisposable disposable;
    private final SchedulerProvider schedulerProvider;

    public LoginPresenter(LoginView loginView, ApplicationUsersRepository applicationUsersRepository, SchedulerProvider schedulerProvider) {
        this.loginView = new WeakReference<>(loginView);
        this.applicationUsersRepository = applicationUsersRepository;
        this.schedulerProvider = schedulerProvider;
        this.disposable = new CompositeDisposable();
    }

    public void attemptLogin(final String email, final String password) {
        disposable.add(applicationUsersRepository.authenticateUser(new LoginRequest(email, password))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onLoginAttemptSuccessful, this::onLoginAttemptError));
    }

    private void onLoginAttemptSuccessful(LoginResponse loginResponse) {
        getApplicationUser(loginResponse.getEmail());
    }

    private void onLoginAttemptError(Throwable throwable) {
        loginView.get().loginFailed();
    }

    private void getApplicationUser(final String email) {
        disposable.add(applicationUsersRepository.getUserByEmail(email)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onFindUserSuccessful, this::onFindUserFailed));
    }

    private void onFindUserSuccessful(ApplicationUser applicationUser) {
        loginView.get().loginSuccessful(applicationUser.getId());
    }

    private void onFindUserFailed(Throwable throwable) {
        loginView.get().loginFailed();
    }

    public void onViewDetached() {
        disposable.dispose();
    }
}
