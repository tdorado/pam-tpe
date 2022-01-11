package com.td.wallendar.login.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.home.ui.HomeActivity
import com.td.wallendar.register.ui.RegisterActivity

class LoginActivity : AbstractActivity(), LoginView {
    private var loginPresenter: LoginPresenter? = null
    private var signInButton: Button? = null
    private var signUpButton: Button? = null
    private var emailInput: TextInputEditText? = null
    private var passwordInput: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createPresenter()
        setUpView()
    }

    public override fun onStop() {
        super.onStop()
        loginPresenter?.onViewDetached()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return loginPresenter
    }

    override fun loginSuccessful(userId: Long) {
        setLoggedUserId(userId)
        finishAffinity()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun loginFailed() {
        Toast.makeText(applicationContext,
                getString(R.string.email_or_password_incorrect),
                Toast.LENGTH_LONG).show()
    }

    private fun createPresenter() {
        loginPresenter = lastNonConfigurationInstance as LoginPresenter?
        if (loginPresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val applicationUsersRepository = dependenciesContainer.getApplicationUsersRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            loginPresenter = LoginPresenter(this, applicationUsersRepository, schedulerProvider)
        }
    }

    private fun setUpView() {
        signInButton = findViewById(R.id.login_button_signin)
        signUpButton = findViewById(R.id.login_button_signup)
        emailInput = findViewById(R.id.login_email)
        passwordInput = findViewById(R.id.login_password)
        signInButton?.setOnClickListener {
            val email = emailInput?.text.toString()
            val password = passwordInput?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginPresenter?.attemptLogin(email, password)
            } else {
                Toast.makeText(applicationContext,
                        getString(R.string.enter_email_and_password),
                        Toast.LENGTH_LONG).show()
            }
        }
        signUpButton?.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
    }
}