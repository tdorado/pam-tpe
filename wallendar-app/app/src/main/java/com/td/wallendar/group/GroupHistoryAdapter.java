package com.td.wallendar.group;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.GroupHistory;

import java.util.ArrayList;
import java.util.List;

public class GroupHistoryAdapter extends RecyclerView.Adapter<GroupHistoryViewHolder> {

    private final List<GroupHistory> dataset;

    public GroupHistoryAdapter() {
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public GroupHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_group_activity, parent, false);
        return new GroupHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHistoryViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataset(final List<GroupHistory> newDataset) {
        this.dataset.clear();
        dataset.addAll(newDataset);
        notifyDataSetChanged();
    }

    public void addToDataset(final GroupHistory history) {
        this.dataset.add(0, history);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }
}