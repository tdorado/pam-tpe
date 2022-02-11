package com.td.wallendar.groupbalance.ui

import com.td.wallendar.models.Debt

interface GroupBalanceView {
    fun bind(debts: MutableList<Debt>)
    fun failedToLoadDebts()
    fun failedToSettleUpDebt()
    fun onSettleUpPaymentDone(debt: Debt)
}