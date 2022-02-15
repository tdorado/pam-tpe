package com.td.wallendar.home.groups.ui

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.td.wallendar.home.groups.GroupAdapter
import com.td.wallendar.models.Group

interface GroupsView<T : Group> {
    fun bind(groupAdapter: GroupAdapter<T>, addChargeFAB: ExtendedFloatingActionButton)
    fun bind(groupAdapter: GroupAdapter<T>)
}