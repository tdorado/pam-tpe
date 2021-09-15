package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.ChargeResponse;
import com.td.wallendar.dtos.response.EventResponse;
import com.td.wallendar.dtos.response.EventsResponse;
import com.td.wallendar.models.Charge;
import com.td.wallendar.models.EqualCharge;
import com.td.wallendar.models.Event;
import com.td.wallendar.models.User;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {

    public static List<Event> toModel(final EventsResponse eventsResponse) {
        final List<Event> events = new ArrayList<>();
        for (EventResponse eventResponse : eventsResponse.getEvents()) {
            events.add(toModel(eventResponse));
        }
        return events;
    }

    public static Event toModel(final EventResponse eventResponse) {
        List<User> debtors = new ArrayList<>();
        for (String debtor : eventResponse.getUsers()) {
            debtors.add(new User(debtor, "", "", ""));
        }

        List<Charge> charges = new ArrayList<>();
        for (ChargeResponse chargeResponse : eventResponse.getCharges()) {
            charges.add(new EqualCharge(
                    chargeResponse.getChargeId(),
                    new User(chargeResponse.getOwner(), "", "", ""),
                    chargeResponse.getTitle(),
                    new ArrayList<>(),
                    chargeResponse.getAmount(),
                    chargeResponse.getDate()
            ));
        }

        return new Event(
                1L,
                eventResponse.getTitle(),
                new User(eventResponse.getOwner(), "", "", ""),
                debtors,
                charges,
                new ArrayList<>(),
                eventResponse.getDate(),
                new ArrayList<>()
        );
    }
}
