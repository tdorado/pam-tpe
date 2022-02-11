package com.td.wallendar.home.groups.ui

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.td.wallendar.home.groups.GroupAdapter

interface GroupsView {
    fun bind(groupAdapter: GroupAdapter, addChargeFAB: ExtendedFloatingActionButton)
}