package com.td.wallendar.home.events.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.td.wallendar.home.events.EventsAdapter;
import com.td.wallendar.models.Event;
import com.td.wallendar.repositories.EventsRepositoryImpl;

import java.lang.ref.WeakReference;
import java.util.List;

public class EventsViewImpl extends RecyclerView implements EventsView {
    private EventsAdapter eventsAdapter;
    private EventsPresenter eventsPresenter;

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
    public void bind(ExtendedFloatingActionButton addChargeFAB, final EventsAdapter eventsAdapter) {
        this.eventsAdapter = eventsAdapter;
        // Shrink floating button when scrolling, extend when stop. Just fancy fab
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addChargeFAB.extend();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && addChargeFAB.isExtended()) {
                    addChargeFAB.shrink();
                }
            }
        });
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setAdapter(eventsAdapter);
        initializePresenter();
    }

    private void initializePresenter() {
        this.eventsPresenter = new EventsPresenter(new WeakReference<>(this), new EventsRepositoryImpl());
        eventsPresenter.listEvents(1L);
    }

    @Override
    public void showEvents(List<Event> events) {
        this.eventsAdapter.setData(events);
    }
}
