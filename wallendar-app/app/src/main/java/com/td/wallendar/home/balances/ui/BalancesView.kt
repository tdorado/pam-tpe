package com.td.wallendar.home.balances.ui

import com.td.wallendar.home.balances.BalanceAdapter

interface BalancesView {
    fun bind(balanceAdapter: BalanceAdapter)
}