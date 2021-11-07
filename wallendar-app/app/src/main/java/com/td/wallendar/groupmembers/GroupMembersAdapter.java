package com.td.wallendar.groupmembers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.ApplicationUser;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersViewHolder> {

    private final List<ApplicationUser> dataset;

    public GroupMembersAdapter() {
        this.dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public GroupMembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_group_member, parent, false);
        return new GroupMembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMembersViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataset(final List<ApplicationUser> newDataset) {
        this.dataset.clear();
        if (newDataset != null) {
            this.dataset.addAll(newDataset);
        }
        notifyDataSetChanged();
    }

}