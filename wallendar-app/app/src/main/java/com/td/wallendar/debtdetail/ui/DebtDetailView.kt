package com.td.wallendar.debtdetail.ui

import com.td.wallendar.models.DebtDetail

interface DebtDetailView {
    fun bind(details: List<DebtDetail>)
    fun failedToLoadDetails()
}