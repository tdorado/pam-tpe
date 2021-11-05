package com.td.wallendar.register.ui;

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
}
