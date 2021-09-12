package com.td.wallendar.service;


import com.td.wallendar.dtos.response.EventResponse;
import com.td.wallendar.dtos.response.EventsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventsService {
    @GET("/events?user_id={user_id")
    Call<EventsResponse> getEventsByUser(@Path("user_id") final Long userId);

    @GET("/events/{event_id}")
    Call<EventResponse> getEventById(@Path("event_id") final Long eventId);
}
