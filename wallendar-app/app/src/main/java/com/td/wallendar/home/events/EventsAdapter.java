package com.td.wallendar.home.events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.home.events.EventsViewHolder;

import java.util.Arrays;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsViewHolder> {

    private List<String> testDataset = Arrays.asList("EVENT 1", "EVENT 2", "EVENT 3", "EVENT 4");

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_events, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        holder.bind(testDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return testDataset == null ? 0 : testDataset.size();
    }
}