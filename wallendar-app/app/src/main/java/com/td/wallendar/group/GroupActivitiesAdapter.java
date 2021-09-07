package com.td.wallendar.group;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.EqualCharge;
import com.td.wallendar.models.Group;
import com.td.wallendar.models.GroupHistory;
import com.td.wallendar.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class GroupActivitiesAdapter extends RecyclerView.Adapter<GroupHistoryViewHolder> {

    // I need kotlin
    private List<GroupHistory> historic = Arrays.asList(
            new EqualCharge(new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(new User(), "Group title", new ArrayList<>(), 2123d, new Date()),
            new EqualCharge(new User(), "Group title", new ArrayList<>(), 2123d, new Date())
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