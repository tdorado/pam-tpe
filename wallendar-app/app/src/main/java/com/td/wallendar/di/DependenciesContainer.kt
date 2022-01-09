package com.td.wallendar.di

import android.content.Context
import android.content.SharedPreferences
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository
import com.td.wallendar.repositories.interfaces.ChargesRepository
import com.td.wallendar.repositories.interfaces.DebtsRepository
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.service.ApplicationUsersService
import com.td.wallendar.service.ChargesService
import com.td.wallendar.service.DebtsService
import com.td.wallendar.service.GroupsService
import com.td.wallendar.utils.scheduler.SchedulerProvider
import retrofit2.Retrofit

interface DependenciesContainer {
    fun getApplicationContext(): Context?
    fun getLoginSharedPreferences(): SharedPreferences?
    fun getSchedulerProvider(): SchedulerProvider?
    fun getGroupsRepository(): GroupsRepository?
    fun getChargesRepository(): ChargesRepository?
    fun getDebtsRepository(): DebtsRepository?
    fun getApplicationUsersRepository(): ApplicationUsersRepository?
    fun getRetrofit(): Retrofit?
    fun getGroupsService(): GroupsService?
    fun getChargesService(): ChargesService?
    fun getDebtsService(): DebtsService?
    fun getApplicationUsersService(): ApplicationUsersService?
}