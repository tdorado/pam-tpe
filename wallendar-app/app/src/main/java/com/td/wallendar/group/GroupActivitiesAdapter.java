package com.td.wallendar.group;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.GroupHistory;

import java.util.List;

public class GroupActivitiesAdapter extends RecyclerView.Adapter<GroupHistoryViewHolder> {

    private List<GroupHistory> historic;

    public GroupActivitiesAdapter() {
    }

    @NonNull
    @Override
    public GroupHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_group_activity, parent, false);
        return new GroupHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHistoryViewHolder holder, int position) {
        holder.bind(historic.get(position));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(final List<GroupHistory> historic) {
        this.historic = historic;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return historic == null ? 0 : historic.size();
    }
}