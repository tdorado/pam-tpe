package com.td.wallendar.addgroup.ui

import com.td.wallendar.models.Group as Group1

interface AddGroupView {
    fun onGroupCreated(group: Group1)
    fun onGroupCreatedWithErrors()
}