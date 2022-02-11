package com.td.wallendar.home.profile.ui

import com.td.wallendar.home.profile.OnLogoutClickedListener
import com.td.wallendar.home.profile.OnShowAliasesClickedListener
import com.td.wallendar.models.ApplicationUser

interface ProfileView {
    fun bind(
            applicationUser: ApplicationUser,
            onShowAliasesClickedListener: OnShowAliasesClickedListener,
            onLogoutClickedListener: OnLogoutClickedListener
    )
}