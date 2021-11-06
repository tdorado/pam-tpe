package com.td.wallendar.home.profile.ui;

import static android.view.Gravity.CENTER;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.td.wallendar.R;
import com.td.wallendar.home.profile.OnLogoutClickedListener;
import com.td.wallendar.home.profile.OnShowAliasesClickedListener;
import com.td.wallendar.models.ApplicationUser;

public class ProfileViewImpl extends LinearLayout implements ProfileView {

    private OnShowAliasesClickedListener onShowAliasesClickedListener;
    private OnLogoutClickedListener onLogoutClickedListener;

    public ProfileViewImpl(Context context) {
        this(context, null);
    }

    public ProfileViewImpl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileViewImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_profile, this);
        setGravity(CENTER);
        setOrientation(VERTICAL);

    }

    @Override
    public void bind(ApplicationUser applicationUser,
                     OnShowAliasesClickedListener onShowAliasesClickedListener,
                     OnLogoutClickedListener onLogoutClickedListener) {
        this.onShowAliasesClickedListener = onShowAliasesClickedListener;
        this.onLogoutClickedListener = onLogoutClickedListener;

        TextView profileName = findViewById(R.id.profile_name);
        TextView profileEmail = findViewById(R.id.profile_email);
        String fullName = applicationUser.getFirstName() + " " + applicationUser.getLastName();

        profileName.setText(fullName);
        profileEmail.setText(applicationUser.getEmail());

        LinearLayout logoutButton = findViewById(R.id.layout_button_logout);
        logoutButton.setOnClickListener(v -> {
            onLogoutClickedListener.onLogoutClicked();
        });
    }

}
