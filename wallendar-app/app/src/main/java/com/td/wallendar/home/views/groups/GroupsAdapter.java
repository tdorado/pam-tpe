package com.td.wallendar.home.views.groups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;

import java.util.Arrays;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsViewHolder> {

    private List<String> testDataset = Arrays.asList("GROUP 1", "GROUP 2", "GROUP 3", "GROUP 4");

    @NonNull
    @Override
    public GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_groups, parent, false);
        return new GroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsViewHolder holder, int position) {
        holder.bind(testDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return testDataset == null ? 0 : testDataset.size();
    }
}
