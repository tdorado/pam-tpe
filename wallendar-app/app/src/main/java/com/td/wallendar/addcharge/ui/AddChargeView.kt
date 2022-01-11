package com.td.wallendar.addcharge.ui

import com.td.wallendar.models.Charge
import com.td.wallendar.models.Group

interface AddChargeView {
    fun onGroupsLoadOk(groups: MutableList<Group>)
    fun onGroupsLoadError()
    fun chargeAddedOk(charge: Charge)
    fun chargeError()
    fun setSelectedGroup(groupId: Long)
}