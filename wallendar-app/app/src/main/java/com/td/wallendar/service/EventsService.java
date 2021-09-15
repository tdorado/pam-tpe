package com.td.wallendar.service;

import com.td.wallendar.dtos.response.EventResponse;
import com.td.wallendar.dtos.response.EventsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventsService {
    @GET("/events")
    Observable<EventsResponse> getEventsByUser(@Query("user_id") final Long userId);

    @GET("/events/{event_id}")
    Observable<EventResponse> getEventById(@Path("event_id") final Long eventId);
}
