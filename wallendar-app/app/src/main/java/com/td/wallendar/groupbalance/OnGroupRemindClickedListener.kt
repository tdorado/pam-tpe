package com.td.wallendar.groupbalance

import com.td.wallendar.models.Debt

interface OnGroupRemindClickedListener {
    fun onGroupRemindClick(debt: Debt)
}