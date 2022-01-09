package com.td.wallendar

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.login.ui.LoginActivity

abstract class AbstractActivity : AppCompatActivity() {
    private lateinit var loginSharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
        loginSharedPreferences = dependenciesContainer.getLoginSharedPreferences()
    }

    fun checkIfUserLogged(): Boolean {
        if (loginSharedPreferences.getLong(LOGGED_USER_ID, loggedOutValue) == loggedOutValue) {
            //si no esta logeado, llevame al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return false
        }
        return true
    }

    fun setLoggedUserId(value: Long) {
        val editor = loginSharedPreferences.edit()
        editor.putLong(LOGGED_USER_ID, value)
        editor.apply()
    }

    fun logout() {
        val editor = loginSharedPreferences.edit()
        editor.putLong(LOGGED_USER_ID, loggedOutValue)
        editor.apply()

        //cuando deslogea llevame al login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun getLoggedUserId(): Long {
        val value = loginSharedPreferences.getLong(LOGGED_USER_ID, loggedOutValue)
        if (value == loggedOutValue) {
            //si no esta logeado, llevame al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        return 0L
    }

    companion object {
        val LOGIN_SHARED_PREFERENCES: String? = "LOGIN_SHARED_PREFERENCES"
        private val LOGGED_USER_ID: String? = "LOGGED_USER_ID"
        private const val loggedOutValue: Long = -1
    }
}