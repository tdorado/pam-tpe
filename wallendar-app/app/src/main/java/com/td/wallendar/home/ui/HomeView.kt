package com.td.wallendar.home.ui

import com.td.wallendar.models.ApplicationUser
import com.td.wallendar.models.Debt
import com.td.wallendar.models.Group

interface HomeView {
    fun showGroups()
    fun showBalances()
    fun showProfile()
    fun bindGroups(groups: MutableList<Group>)
    fun bindDebts(debts: MutableList<Debt>)
    fun bindProfile(applicationUser: ApplicationUser)
    fun removeDebt(debt: Debt)
    fun errorGettingUser()
    fun errorGettingGroups()
    fun errorGettingBalances()
    fun errorPayingDebt()
}