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

class ProductionDependenciesContainer(context: Context) : DependenciesContainer {
    private val dependenciesModule: DependenciesModule = DependenciesModule(context)
    private var schedulerProvider: SchedulerProvider? = null
    private var loginSharedPreferences: SharedPreferences? = null
    private var retrofit: Retrofit? = null
    private var groupsRepository: GroupsRepository? = null
    private var chargesRepository: ChargesRepository? = null
    private var debtsRepository: DebtsRepository? = null
    private var applicationUsersRepository: ApplicationUsersRepository? = null
    private var groupsService: GroupsService? = null
    private var chargesService: ChargesService? = null
    private var debtsService: DebtsService? = null
    private var applicationUsersService: ApplicationUsersService? = null

    override fun getApplicationContext(): Context {
        return dependenciesModule.getApplicationContext()
    }

    override fun getLoginSharedPreferences(): SharedPreferences {
        if (loginSharedPreferences == null) {
            loginSharedPreferences = dependenciesModule.provideLoginSharedPreferences()
        }
        return loginSharedPreferences!!
    }

    override fun getSchedulerProvider(): SchedulerProvider {
        if (schedulerProvider == null) {
            schedulerProvider = dependenciesModule.provideScheduler()
        }
        return schedulerProvider!!
    }

    override fun getGroupsRepository(): GroupsRepository {
        if (groupsRepository == null) {
            groupsRepository = dependenciesModule.provideGroupsRepository(getGroupsService())
        }
        return groupsRepository!!
    }

    override fun getChargesRepository(): ChargesRepository {
        if (chargesRepository == null) {
            chargesRepository = dependenciesModule.provideChargesRepository(getChargesService())
        }
        return chargesRepository!!
    }

    override fun getDebtsRepository(): DebtsRepository {
        if (debtsRepository == null) {
            debtsRepository = dependenciesModule.provideDebtsRepository(getDebtsService())
        }
        return debtsRepository!!
    }

    override fun getApplicationUsersRepository(): ApplicationUsersRepository {
        if (applicationUsersRepository == null) {
            applicationUsersRepository = dependenciesModule.provideApplicationUsersRepository(getApplicationUsersService())
        }
        return applicationUsersRepository!!
    }

    override fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = dependenciesModule.provideRetrofit()
        }
        return retrofit!!
    }

    override fun getGroupsService(): GroupsService {
        if (groupsService == null) {
            groupsService = dependenciesModule.provideGroupsService(getRetrofit())
        }
        return groupsService!!
    }

    override fun getChargesService(): ChargesService {
        if (chargesService == null) {
            chargesService = dependenciesModule.provideChargesService(getRetrofit())
        }
        return chargesService!!
    }

    override fun getDebtsService(): DebtsService {
        if (debtsService == null) {
            debtsService = dependenciesModule.provideDebtsService(getRetrofit())
        }
        return debtsService!!
    }

    override fun getApplicationUsersService(): ApplicationUsersService {
        if (applicationUsersService == null) {
            applicationUsersService = dependenciesModule.provideApplicationUsersService(getRetrofit())
        }
        return applicationUsersService!!
    }

}