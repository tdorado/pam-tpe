package com.td.wallendar.repositories.interfaces;


import com.td.wallendar.models.Event;

import java.util.List;

import io.reactivex.Observable;

public interface EventsRepository {
    Observable<List<Event>> getEventsByUserId(final long userId);
}
