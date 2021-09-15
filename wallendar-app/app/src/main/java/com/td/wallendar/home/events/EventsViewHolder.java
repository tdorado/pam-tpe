package com.td.wallendar.home.events;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Event;
import com.td.wallendar.utils.DateFormatter;

public class EventsViewHolder extends RecyclerView.ViewHolder {
    public EventsViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void bind(final Event event) {
        final TextView eventName = itemView.findViewById(R.id.row_event_name);
        eventName.setText(event.getTitle());

        final TextView eventDate = itemView.findViewById(R.id.row_event_date);
        eventDate.setText(DateFormatter.formatDateAsString(event.getDate()));

        // FIXME this should be some kind of recycler, or we could choose
        // to only show two lines: user debt with max value, and who use who owes me the max value
        final TextView eventOwnDebt = itemView.findViewById(R.id.row_event_own_debt);
        final TextView eventDebt = itemView.findViewById(R.id.row_event_debt);
    }

}