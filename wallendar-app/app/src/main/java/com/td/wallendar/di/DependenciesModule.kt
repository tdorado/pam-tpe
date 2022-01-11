package com.td.wallendar.di

import android.content.Context
import android.content.SharedPreferences
import com.td.wallendar.AbstractActivity
import com.td.wallendar.repositories.ApplicationUsersRepositoryImpl
import com.td.wallendar.repositories.ChargesRepositoryImpl
import com.td.wallendar.repositories.DebtsRepositoryImpl
import com.td.wallendar.repositories.GroupsRepositoryImpl
import com.td.wallendar.repositories.interfaces.ApplicationUsersRepository
import com.td.wallendar.repositories.interfaces.ChargesRepository
import com.td.wallendar.repositories.interfaces.DebtsRepository
import com.td.wallendar.repositories.interfaces.GroupsRepository
import com.td.wallendar.service.ApplicationUsersService
import com.td.wallendar.service.ChargesService
import com.td.wallendar.service.DebtsService
import com.td.wallendar.service.GroupsService
import com.td.wallendar.utils.scheduler.AndroidSchedulerProvider
import com.td.wallendar.utils.scheduler.SchedulerProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DependenciesModule(context: Context) {

    private val applicationContext: Context = context

    /* default */
    fun getApplicationContext(): Context {
        return applicationContext
    }

    /* default */
    fun provideScheduler(): SchedulerProvider {
        return AndroidSchedulerProvider()
    }

    /* default */
    fun provideLoginSharedPreferences(): SharedPreferences? {
        return applicationContext.getSharedPreferences(AbstractActivity.Companion.LOGIN_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    /* default */
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://wallendar.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun provideGroupsService(retrofit: Retrofit): GroupsService {
        return retrofit.create(GroupsService::class.java)
    }

    fun provideDebtsService(retrofit: Retrofit): DebtsService {
        return retrofit.create(DebtsService::class.java)
    }

    fun provideChargesService(retrofit: Retrofit): ChargesService {
        return retrofit.create(ChargesService::class.java)
    }

    fun provideApplicationUsersService(retrofit: Retrofit): ApplicationUsersService {
        return retrofit.create(ApplicationUsersService::class.java)
    }

    fun provideGroupsRepository(groupsService: GroupsService): GroupsRepository {
        return GroupsRepositoryImpl(groupsService)
    }

    fun provideChargesRepository(chargesService: ChargesService): ChargesRepository {
        return ChargesRepositoryImpl(chargesService)
    }

    fun provideDebtsRepository(debtsService: DebtsService): DebtsRepository {
        return DebtsRepositoryImpl(debtsService)
    }

    fun provideApplicationUsersRepository(applicationUsersService: ApplicationUsersService): ApplicationUsersRepository {
        return ApplicationUsersRepositoryImpl(applicationUsersService)
    }

}