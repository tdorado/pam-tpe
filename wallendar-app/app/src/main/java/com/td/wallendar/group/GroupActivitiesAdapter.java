package com.td.wallendar.group;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.EqualCharge;
import com.td.wallendar.models.GroupHistory;
import com.td.wallendar.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GroupActivitiesAdapter extends RecyclerView.Adapter<GroupHistoryViewHolder> {

    // I need kotlin
    private List<GroupHistory> historic = Arrays.asList(
            new EqualCharge(1L,new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(2L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(3L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(4L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(5L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(6L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(7L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(8L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(9L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(10L, new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(11L, new User(), "Group title", new ArrayList<>(), 2123d, new Date())
    );

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