package com.td.wallendar.groupmembers.ui;

import com.td.wallendar.models.ApplicationUser;

import java.util.List;

public interface GroupMembersView {
    void bind(List<ApplicationUser> members);

    void failedToLoadMembers();
}
