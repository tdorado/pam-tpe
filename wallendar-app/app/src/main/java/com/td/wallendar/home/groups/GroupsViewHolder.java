package com.td.wallendar.home.groups;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Group;

import java.util.List;

public class GroupsViewHolder extends RecyclerView.ViewHolder {
    public GroupsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final Group groups) {
        final TextView textView = itemView.findViewById(R.id.row_group_name);

        textView.setText(groups.getTitle());
    }

}
