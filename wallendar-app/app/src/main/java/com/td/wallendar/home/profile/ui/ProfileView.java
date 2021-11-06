package com.td.wallendar.home.profile.ui;

import com.td.wallendar.home.profile.OnLogoutClickedListener;
import com.td.wallendar.home.profile.OnShowAliasesClickedListener;
import com.td.wallendar.models.ApplicationUser;

public interface ProfileView {
    void bind(ApplicationUser applicationUser,
              OnShowAliasesClickedListener onShowAliasesClickedListener,
              OnLogoutClickedListener onLogoutClickedListener);
}
