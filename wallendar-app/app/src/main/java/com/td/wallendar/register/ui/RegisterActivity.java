package com.td.wallendar.register.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.home.ui.HomeActivity;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    private RegisterPresenter registerPresenter;

    private Button signUpButton;
    private TextInputEditText emailInput;
    private TextInputEditText firstnameInput;
    private TextInputEditText lastnameInput;
    private TextInputEditText passwordInput;
    private TextInputEditText repeatPasswordInput;
    private TextInputEditText phoneNumberInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createPresenter();

        setUpView();
    }

    @Override
    public void onStop() {
        super.onStop();
        registerPresenter.onViewDetached();
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return registerPresenter;
    }

    @Override
    public void registerSuccessful(long userId) {
        setLoggedUserId(userId);
        finishAffinity();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void registerFailed() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.register_failed),
                Toast.LENGTH_LONG).show();
    }

    private void createPresenter() {
        registerPresenter = (RegisterPresenter) getLastNonConfigurationInstance();

        if (registerPresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final ApplicationUsersRepository applicationUsersRepository = dependenciesContainer.getApplicationUsersRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            registerPresenter = new RegisterPresenter(this, applicationUsersRepository, schedulerProvider);
        }
    }

    private void setUpView() {
        signUpButton = findViewById(R.id.register_button_signin);
        emailInput = findViewById(R.id.register_email);
        firstnameInput = findViewById(R.id.register_firstname);
        lastnameInput = findViewById(R.id.register_lastname);
        passwordInput = findViewById(R.id.register_password);
        repeatPasswordInput = findViewById(R.id.register_confirm_password);
        phoneNumberInput = findViewById(R.id.register_phone_number);
        signUpButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String passwordRepeat = repeatPasswordInput.getText().toString();
            String firstname = firstnameInput.getText().toString();
            String lastname = lastnameInput.getText().toString();
            String phoneNumber = phoneNumberInput.getText().toString();

            if (validateRegisterInput(email, firstname, lastname, password, passwordRepeat)) {
                registerPresenter.attemptRegister(email, firstname, lastname, password, phoneNumber);
            }
        });
    }

    private boolean validateRegisterInput(String email, String firstname, String lastname,
                                          String password, String passwordRepeat) {
        if (email.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || password.isEmpty()
                || passwordRepeat.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.fill_all_inputs),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.equals(passwordRepeat)) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.passwords_must_match),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
