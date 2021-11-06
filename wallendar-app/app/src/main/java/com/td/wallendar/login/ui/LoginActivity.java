package com.td.wallendar.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.td.wallendar.AbstractActivity;
import com.td.wallendar.R;
import com.td.wallendar.di.DependenciesContainer;
import com.td.wallendar.di.DependenciesContainerLocator;
import com.td.wallendar.home.ui.HomeActivity;
import com.td.wallendar.register.ui.RegisterActivity;
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository;
import com.td.wallendar.utils.scheduler.SchedulerProvider;

public class LoginActivity extends AbstractActivity implements LoginView {

    private LoginPresenter loginPresenter;

    private Button signInButton;
    private Button signUpButton;
    private TextInputEditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createPresenter();

        setUpView();
    }

    @Override
    public void onStop() {
        super.onStop();
        loginPresenter.onViewDetached();
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return loginPresenter;
    }

    @Override
    public void loginSuccessful(long userId) {
        setLoggedUserId(userId);
        finishAffinity();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.email_or_password_incorrect),
                Toast.LENGTH_LONG).show();
    }

    private void createPresenter() {
        loginPresenter = (LoginPresenter) getLastNonConfigurationInstance();

        if (loginPresenter == null) {
            final DependenciesContainer dependenciesContainer = DependenciesContainerLocator.locateComponent(this);
            final ApplicationUsersRepository applicationUsersRepository = dependenciesContainer.getApplicationUsersRepository();
            final SchedulerProvider schedulerProvider = dependenciesContainer.getSchedulerProvider();
            loginPresenter = new LoginPresenter(this, applicationUsersRepository, schedulerProvider);
        }
    }

    private void setUpView() {
        signInButton = findViewById(R.id.login_button_signin);
        signUpButton = findViewById(R.id.login_button_signup);
        emailInput = findViewById(R.id.login_email);
        passwordInput = findViewById(R.id.login_password);

        signInButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                loginPresenter.attemptLogin(email, password);
            } else {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.enter_email_and_password),
                        Toast.LENGTH_LONG).show();
            }
        });

        signUpButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

}
