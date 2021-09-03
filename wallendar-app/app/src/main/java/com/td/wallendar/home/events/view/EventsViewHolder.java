package com.td.wallendar.home.events.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;

public class EventsViewHolder extends RecyclerView.ViewHolder {
    public EventsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final String text) {
        final TextView textView = itemView.findViewById(R.id.row_event_name);

        textView.setText(text);
    }

}