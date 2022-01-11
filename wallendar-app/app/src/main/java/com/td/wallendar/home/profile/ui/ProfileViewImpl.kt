package com.td.wallendar.home.profile.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.td.wallendar.R
import com.td.wallendar.home.profile.OnLogoutClickedListener
import com.td.wallendar.home.profile.OnShowAliasesClickedListener
import com.td.wallendar.models.ApplicationUser

class ProfileViewImpl @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), ProfileView {
    private var onShowAliasesClickedListener: OnShowAliasesClickedListener? = null
    private var onLogoutClickedListener: OnLogoutClickedListener? = null
    override fun bind(
            applicationUser: ApplicationUser,
            onShowAliasesClickedListener: OnShowAliasesClickedListener,
            onLogoutClickedListener: OnLogoutClickedListener
    ) {
        this.onShowAliasesClickedListener = onShowAliasesClickedListener
        this.onLogoutClickedListener = onLogoutClickedListener
        val profileName = findViewById<TextView?>(R.id.profile_name)
        val profileEmail = findViewById<TextView?>(R.id.profile_email)
        val fullName = applicationUser.firstName + " " + applicationUser.lastName
        profileName.text = fullName
        profileEmail.text = applicationUser.email
        val logoutButton = findViewById<LinearLayout?>(R.id.layout_button_logout)
        logoutButton.setOnClickListener { onLogoutClickedListener.onLogoutClicked() }
    }

    init {
        inflate(context, R.layout.view_profile, this)
        gravity = Gravity.CENTER
        orientation = VERTICAL
    }
}