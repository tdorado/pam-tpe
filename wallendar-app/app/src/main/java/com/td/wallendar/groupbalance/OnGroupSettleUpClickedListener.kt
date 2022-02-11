package com.td.wallendar.groupbalance

import com.td.wallendar.models.Debt

interface OnGroupSettleUpClickedListener {
    fun onGroupSettleUpClick(debt: Debt)
}