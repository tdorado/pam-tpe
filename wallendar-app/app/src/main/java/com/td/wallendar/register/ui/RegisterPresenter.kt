package com.td.wallendar.register.ui

import com.td.wallendar.dtos.request.AddApplicationUserRequest
import com.td.wallendar.models.ApplicationUser
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

class RegisterPresenter(registerView: RegisterView, applicationUsersRepository: ApplicationUsersRepository, schedulerProvider: SchedulerProvider) {
    private val registerView: WeakReference<RegisterView> = WeakReference(registerView)
    private val applicationUsersRepository: ApplicationUsersRepository = applicationUsersRepository
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val schedulerProvider: SchedulerProvider = schedulerProvider

    fun onViewDetached() {
        disposable.dispose()
    }

    fun attemptRegister(email: String?, firstname: String?, lastname: String?, password: String?, phoneNumber: String?) {
        disposable.add(applicationUsersRepository.createUser(AddApplicationUserRequest(email, password, firstname, lastname, phoneNumber))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ applicationUser: ApplicationUser -> onRegisterAttemptSuccessful(applicationUser) }) { throwable: Throwable -> onRegisterAttemptError(throwable) })
    }

    private fun onRegisterAttemptSuccessful(applicationUser: ApplicationUser) {
        registerView.get()?.registerSuccessful(applicationUser.id)
    }

    private fun onRegisterAttemptError(throwable: Throwable) {
        registerView.get()?.registerFailed()
    }

}