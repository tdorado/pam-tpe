package com.td.wallendar.repositories.interfaces

import com.td.wallendar.dtos.request.AddEventRequest
import com.td.wallendar.dtos.request.AddMemberByEmailRequest
import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.models.Event
import io.reactivex.Single

interface EventsRepository {
    fun getEventsByUser(userId: Long): Single<MutableList<Event>>
    fun getEvent(groupId: Long): Single<Event>
    fun createEvent(addEventRequest: AddEventRequest): Single<Event>
    fun addMemberByEmail(groupId: Long, addMemberByEmailRequest: AddMemberByEmailRequest): Single<Event>
    fun addMembers(groupId: Long, addMembersRequest: AddMembersRequest): Single<Event>
    fun addPayment(groupId: Long, addPaymentRequest: AddPaymentRequest): Single<Void>
}