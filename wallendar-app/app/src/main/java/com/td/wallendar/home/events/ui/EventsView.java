package com.td.wallendar.home.events.ui;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.home.events.EventsAdapter;
import com.td.wallendar.models.Event;

import java.util.List;

public interface EventsView {
    void bind(final ExtendedFloatingActionButton addChargeFAB, final EventsAdapter eventsAdapter);
    void showEvents(final List<Event> events);
}
