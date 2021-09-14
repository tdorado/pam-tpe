package com.td.wallendar.home.groups;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsViewHolder> {

    private List<Group> groups;
    private final View.OnClickListener listener;

    public GroupsAdapter(final View.OnClickListener listener) {
        this.listener = listener;
        this.groups = new ArrayList<>();
    }

    @NonNull
    @Override
    public GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_groups, parent, false);
        view.setOnClickListener(listener);
        return new GroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsViewHolder holder, int position) {
        holder.bind(groups.get(position));
    }

    @Override
    public int getItemCount() {
        return groups == null ? 0 : groups.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(final List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    public Long getGroupIdAt(final int position) {
        if (groups.size() > position) {
            return groups.get(position).getId();
        }
        return null;
    }
}
