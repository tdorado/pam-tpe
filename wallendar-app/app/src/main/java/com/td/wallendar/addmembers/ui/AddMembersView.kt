package com.td.wallendar.addmembers.ui

interface AddMembersView {
    fun onMembersAddedSuccessfully(groupId: Long)
    fun onMembersAddedWithError()
}