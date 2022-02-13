package com.td.wallendar.di

import android.content.Context
import android.content.SharedPreferences
import com.td.wallendar.repositories.interfaces.*
import com.td.wallendar.service.*
import com.td.wallendar.utils.scheduler.SchedulerProvider
import retrofit2.Retrofit

interface DependenciesContainer {
    fun getApplicationContext(): Context
    fun getLoginSharedPreferences(): SharedPreferences
    fun getSchedulerProvider(): SchedulerProvider
    fun getGroupsRepository(): GroupsRepository
    fun getChargesRepository(): ChargesRepository
    fun getDebtsRepository(): DebtsRepository
    fun getApplicationUsersRepository(): ApplicationUsersRepository
    fun getRetrofit(): Retrofit
    fun getGroupsService(): GroupsService
    fun getChargesService(): ChargesService
    fun getDebtsService(): DebtsService
    fun getApplicationUsersService(): ApplicationUsersService
    fun getEventsRepository(): EventsRepository
    fun getEventsService(): EventsService
}