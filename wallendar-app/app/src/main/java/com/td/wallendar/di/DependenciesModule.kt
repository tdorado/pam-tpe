package com.td.wallendar.di

import android.content.Context
import android.content.SharedPreferences
import com.td.wallendar.AbstractActivity
import com.td.wallendar.repositories.*
import com.td.wallendar.repositories.interfaces.*
import com.td.wallendar.service.*
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
                .baseUrl("http://10.0.2.2:8080/")
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

    fun provideEventsRepository(eventsService: EventsService): EventsRepository {
        return EventsRepositoryImpl(eventsService)
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

    fun provideEventsService(retrofit: Retrofit): EventsService? {
        return retrofit.create(EventsService::class.java)
    }

}