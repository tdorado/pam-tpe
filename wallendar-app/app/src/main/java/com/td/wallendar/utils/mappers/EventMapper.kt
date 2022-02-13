package com.td.wallendar.utils.mappers

import com.td.wallendar.dtos.response.EventResponse
import com.td.wallendar.models.Event
import java.util.*

object EventMapper {
    fun toModel(eventsResponse: MutableList<EventResponse>): MutableList<Event> {
        val groups: MutableList<Event> = ArrayList()
        for (group in eventsResponse) {
            groups.add(toModel(group))
        }
        return groups
    }

    fun toModel(eventResponse: EventResponse): Event {
        return Event(
                eventResponse.id,
                eventResponse.title,
                ApplicationUserMapper.toModel(eventResponse.owner),
                ApplicationUserMapper.toModel(eventResponse.members),
                ChargeMapper.toModel(eventResponse.charges),
                DebtMapper.toModel(eventResponse.debts),
                PaymentMapper.toModel(eventResponse.payments),
                eventResponse.date
        )
    }
}