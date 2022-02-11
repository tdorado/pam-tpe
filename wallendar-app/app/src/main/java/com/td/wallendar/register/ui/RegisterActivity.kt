package com.td.wallendar.register.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.td.wallendar.AbstractActivity
import com.td.wallendar.R
import com.td.wallendar.di.DependenciesContainerLocator
import com.td.wallendar.home.ui.HomeActivity

class RegisterActivity : AbstractActivity(), RegisterView {
    private var registerPresenter: RegisterPresenter? = null
    private var signUpButton: Button? = null
    private var emailInput: TextInputEditText? = null
    private var firstnameInput: TextInputEditText? = null
    private var lastnameInput: TextInputEditText? = null
    private var passwordInput: TextInputEditText? = null
    private var repeatPasswordInput: TextInputEditText? = null
    private var phoneNumberInput: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        createPresenter()
        setUpView()
    }

    public override fun onStop() {
        super.onStop()
        registerPresenter?.onViewDetached()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return registerPresenter
    }

    override fun registerSuccessful(userId: Long) {
        setLoggedUserId(userId)
        finishAffinity()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun registerFailed() {
        Toast.makeText(applicationContext,
                getString(R.string.register_failed),
                Toast.LENGTH_LONG).show()
    }

    private fun createPresenter() {
        registerPresenter = lastNonConfigurationInstance as RegisterPresenter?
        if (registerPresenter == null) {
            val dependenciesContainer = DependenciesContainerLocator.locateComponent(this)
            val applicationUsersRepository = dependenciesContainer.getApplicationUsersRepository()
            val schedulerProvider = dependenciesContainer.getSchedulerProvider()
            registerPresenter = RegisterPresenter(this, applicationUsersRepository, schedulerProvider)
        }
    }

    private fun setUpView() {
        signUpButton = findViewById(R.id.register_button_signin)
        emailInput = findViewById(R.id.register_email)
        firstnameInput = findViewById(R.id.register_firstname)
        lastnameInput = findViewById(R.id.register_lastname)
        passwordInput = findViewById(R.id.register_password)
        repeatPasswordInput = findViewById(R.id.register_confirm_password)
        phoneNumberInput = findViewById(R.id.register_phone_number)
        signUpButton?.setOnClickListener {
            val email = emailInput?.text.toString()
            val password = passwordInput?.text.toString()
            val passwordRepeat = repeatPasswordInput?.text.toString()
            val firstname = firstnameInput?.text.toString()
            val lastname = lastnameInput?.text.toString()
            val phoneNumber = phoneNumberInput?.text.toString()
            if (validateRegisterInput(email, firstname, lastname, password, passwordRepeat)) {
                registerPresenter?.attemptRegister(email, firstname, lastname, password, phoneNumber)
            }
        }
    }

    private fun validateRegisterInput(
            email: String, firstname: String, lastname: String,
            password: String, passwordRepeat: String
    ): Boolean {
        if (email.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || password.isEmpty()
                || passwordRepeat.isEmpty()) {
            Toast.makeText(applicationContext,
                    getString(R.string.fill_all_inputs),
                    Toast.LENGTH_LONG).show()
            return false
        }
        if (password != passwordRepeat) {
            Toast.makeText(applicationContext,
                    getString(R.string.passwords_must_match),
                    Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}