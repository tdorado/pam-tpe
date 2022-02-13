package com.td.wallendar.home.groupsandevents.ui

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.td.wallendar.home.groupsandevents.GroupAdapter

interface GroupsView {
    fun bind(groupAdapter: GroupAdapter, addChargeFAB: ExtendedFloatingActionButton)
}