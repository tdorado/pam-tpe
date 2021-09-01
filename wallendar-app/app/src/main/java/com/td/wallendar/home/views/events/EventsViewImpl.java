package com.td.wallendar.home.views.events;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class EventsViewImpl extends LinearLayout implements EventsView {
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
    public void bind() {

    }
}
