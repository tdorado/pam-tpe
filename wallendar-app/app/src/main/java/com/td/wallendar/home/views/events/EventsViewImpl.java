package com.td.wallendar.home.views.events;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class EventsViewImpl extends LinearLayout implements EventsView {
    public EventsViewImpl(Context context) {
        super(context);
    }

    public EventsViewImpl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventsViewImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bind() {

    }
}
