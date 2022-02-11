package com.td.wallendar.home.balances

import com.td.wallendar.models.Debt

interface OnBalanceSettleUpClickedListener {
    fun onBalanceSettleUpClick(debt: Debt)
}