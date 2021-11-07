package com.td.wallendar.register.ui;

import com.td.wallendar.dtos.request.AddApplicationUserRequest;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class RegisterPresenter {

    private final WeakReference<RegisterView> registerView;
    private final ApplicationUsersRepository applicationUsersRepository;
    private final CompositeDisposable disposable;
    private final SchedulerProvider schedulerProvider;

    public RegisterPresenter(RegisterView registerView, ApplicationUsersRepository applicationUsersRepository, SchedulerProvider schedulerProvider) {
        this.registerView = new WeakReference<>(registerView);
        this.applicationUsersRepository = applicationUsersRepository;
        this.schedulerProvider = schedulerProvider;
        this.disposable = new CompositeDisposable();
    }

    public void onViewDetached() {
        disposable.dispose();
    }

    public void attemptRegister(String email, String firstname, String lastname, String password, String phoneNumber) {
        disposable.add(applicationUsersRepository.createUser(new AddApplicationUserRequest(email, password, firstname, lastname, phoneNumber))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onRegisterAttemptSuccessful, this::onRegisterAttemptError));
    }

    private void onRegisterAttemptSuccessful(ApplicationUser applicationUser) {
        registerView.get().registerSuccessful(applicationUser.getId());
    }

    private void onRegisterAttemptError(Throwable throwable) {
        registerView.get().registerFailed();
    }
}
