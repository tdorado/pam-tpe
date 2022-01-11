package com.td.wallendar.register.ui

interface RegisterView {
    fun registerSuccessful(userId: Long)
    fun registerFailed()
}