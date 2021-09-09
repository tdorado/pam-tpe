package com.td.wallendar.home.events.ui;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.home.events.EventsAdapter;

public interface EventsView {
    void bind(final ExtendedFloatingActionButton addChargeFAB, final EventsAdapter eventsAdapter);
}
