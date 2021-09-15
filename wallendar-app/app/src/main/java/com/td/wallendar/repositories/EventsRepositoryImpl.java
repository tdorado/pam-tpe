package com.td.wallendar.repositories;

import com.td.wallendar.ServiceModule;
import com.td.wallendar.models.Event;
import com.td.wallendar.repositories.interfaces.EventsRepository;
import com.td.wallendar.repositories.mappers.EventMapper;
import com.td.wallendar.service.EventsService;

import java.util.List;

import io.reactivex.Observable;

public class EventsRepositoryImpl implements EventsRepository {

    private EventsService eventsService;

    public EventsRepositoryImpl() {
        this.eventsService = ServiceModule.getRetrofit().create(EventsService.class);
    }

    @Override
    public Observable<List<Event>> getEventsByUserId(long userId) {
        return eventsService.getEventsByUser(userId).map(EventMapper::toModel);
    }
}
