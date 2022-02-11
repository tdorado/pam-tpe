package com.td.wallendar.home.balances

import com.td.wallendar.models.Debt

interface OnRemindButtonClickedListener {
    fun onRemindButtonClick(debt: Debt)
}