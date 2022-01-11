package com.td.wallendar.group.ui

import com.td.wallendar.models.Group
import com.td.wallendar.models.GroupHistory

interface GroupView {
    open fun bindGroup(group: Group)
    open fun bindGroupHistory(historic: MutableList<GroupHistory>)
    open fun onGroupLoadError()
}