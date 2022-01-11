package com.td.wallendar.home.balances.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.td.wallendar.home.balances.BalanceAdapter

class BalancesViewImpl @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr), BalancesView {
    override fun bind(balanceAdapter: BalanceAdapter) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = balanceAdapter
    }
}