package com.td.wallendar.repositories

import com.td.wallendar.dtos.request.AddEventRequest
import com.td.wallendar.dtos.request.AddMemberByEmailRequest
import com.td.wallendar.dtos.request.AddMembersRequest
import com.td.wallendar.dtos.request.AddPaymentRequest
import com.td.wallendar.models.Event
import com.td.wallendar.repositories.interfaces.EventsRepository
import com.td.wallendar.service.EventsService
import com.td.wallendar.utils.mappers.EventMapper
import com.td.wallendar.utils.networking.RetrofitUtils
import io.reactivex.Single

class EventsRepositoryImpl(private val eventsService: EventsService) : EventsRepository {
    override fun getEventsByUser(userId: Long): Single<MutableList<Event>> {
        return RetrofitUtils
                .performRequest(eventsService.getEventsByUserId(userId))
                .map { EventMapper.toModel(it) }
    }

    override fun getEvent(groupId: Long): Single<Event> {
        return RetrofitUtils
                .performRequest(eventsService.getEventById(groupId))
                .map { EventMapper.toModel(it) }
    }

    override fun createEvent(addEventRequest: AddEventRequest): Single<Event> {
        return RetrofitUtils
                .performRequest(eventsService.createEvent(addEventRequest))
                .map { EventMapper.toModel(it) }
    }

    override fun addMemberByEmail(groupId: Long, addMemberByEmailRequest: AddMemberByEmailRequest): Single<Event> {
        return RetrofitUtils
                .performRequest(eventsService.addMemberByEmail(groupId, addMemberByEmailRequest))
                .map { EventMapper.toModel(it) }
    }

    override fun addMembers(groupId: Long, addMembersRequest: AddMembersRequest): Single<Event> =
            RetrofitUtils
                    .performRequest(eventsService.addMembers(groupId, addMembersRequest))
                    .map { EventMapper.toModel(it) }


    override fun addPayment(groupId: Long, addPaymentRequest: AddPaymentRequest) =
            RetrofitUtils
                    .performRequest(eventsService.addPayment(groupId, addPaymentRequest))
}