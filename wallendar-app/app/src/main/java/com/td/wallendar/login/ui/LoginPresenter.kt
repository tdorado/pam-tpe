package com.td.wallendar.login.ui

import com.td.wallendar.dtos.request.LoginRequest
import com.td.wallendar.dtos.response.LoginResponse
import com.td.wallendar.models.ApplicationUser
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository
import com.td.wallendar.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

class LoginPresenter(loginView: LoginView, private val applicationUsersRepository: ApplicationUsersRepository, private val schedulerProvider: SchedulerProvider) {
    private val loginView: WeakReference<LoginView> = WeakReference(loginView)
    private val disposable: CompositeDisposable = CompositeDisposable()

    fun attemptLogin(email: String, password: String) {
        disposable.add(applicationUsersRepository.authenticateUser(LoginRequest(email, password))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ loginResponse: LoginResponse -> onLoginAttemptSuccessful(loginResponse) }) { throwable: Throwable -> onLoginAttemptError(throwable) })
    }

    private fun onLoginAttemptSuccessful(loginResponse: LoginResponse) {
        getApplicationUser(loginResponse.email)
    }

    private fun onLoginAttemptError(throwable: Throwable) {
        loginView.get()?.loginFailed()
    }

    private fun getApplicationUser(email: String) {
        disposable.add(applicationUsersRepository.getUserByEmail(email)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ applicationUser: ApplicationUser -> onFindUserSuccessful(applicationUser) }) { throwable: Throwable -> onFindUserFailed(throwable) })
    }

    private fun onFindUserSuccessful(applicationUser: ApplicationUser) {
        loginView.get()?.loginSuccessful(applicationUser.id)
    }

    private fun onFindUserFailed(throwable: Throwable) {
        loginView.get()?.loginFailed()
    }

    fun onViewDetached() {
        disposable.dispose()
    }

}