package com.td.wallendar.home.views.groups;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;

import java.util.List;

// FIXME
@RequiresApi(api = Build.VERSION_CODES.R)
public class GroupsAdapter extends RecyclerView.Adapter<GroupsViewHolder> {

    private List<String> testDataset = List.of("GRUPO 1", "GRUPO 2", "GRUPO 3", "GRUPO 4");

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
