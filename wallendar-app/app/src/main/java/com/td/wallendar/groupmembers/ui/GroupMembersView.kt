package com.td.wallendar.groupmembers.ui

import com.td.wallendar.models.ApplicationUser

interface GroupMembersView {
    fun bind(members: MutableList<ApplicationUser>)
    fun failedToLoadMembers()
}