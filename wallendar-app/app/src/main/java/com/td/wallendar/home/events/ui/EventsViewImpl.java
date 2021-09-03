package com.td.wallendar.home.events.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.home.events.EventsAdapter;

public class EventsViewImpl extends RecyclerView implements EventsView {
    public EventsViewImpl(Context context) {
        this(context, null);
    }

    public EventsViewImpl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventsViewImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bind(final EventsAdapter eventsAdapter) {
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setAdapter(eventsAdapter);
    }
}
