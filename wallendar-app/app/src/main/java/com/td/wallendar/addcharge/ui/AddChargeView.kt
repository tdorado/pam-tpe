package com.td.wallendar.addcharge.ui

import com.td.wallendar.models.Charge
import com.td.wallendar.models.Group

interface AddChargeView {
    open fun onGroupsLoadOk(groups: MutableList<Group>)
    open fun onGroupsLoadError()
    open fun chargeAddedOk(charge: Charge)
    open fun chargeError()
    open fun setSelectedGroup(groupId: Long)
}