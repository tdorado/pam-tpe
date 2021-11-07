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

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    private final List<Group> dataset;
    private final long loggedUserId;
    private OnGroupClickedListener listener;

    public GroupAdapter(final long loggedUserId) {
        this.dataset = new ArrayList<>();
        this.loggedUserId = loggedUserId;

    }

    public void setOnGroupClickedListener(OnGroupClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_groups, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.bind(dataset.get(position), loggedUserId);
        holder.setOnGroupClickedListener(listener);
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataset(final List<Group> newDataset) {
        this.dataset.clear();
        if (newDataset != null) {
            this.dataset.addAll(newDataset);
        }
        notifyDataSetChanged();
    }

    public void addToDataset(Group group) {
        this.dataset.add(group);
        notifyItemInserted(dataset.size() - 1);
    }

    public void updateInDataset(Group group) {
        int position = this.dataset.indexOf(group);
        this.dataset.set(position, group);
        notifyItemChanged(position);
    }
}
