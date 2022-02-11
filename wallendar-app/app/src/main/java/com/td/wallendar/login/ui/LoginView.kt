package com.td.wallendar.login.ui

interface LoginView {
    fun loginSuccessful(userId: Long)
    fun loginFailed()
}