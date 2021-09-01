package com.td.wallendar.home.groups.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;

public class GroupsViewHolder extends RecyclerView.ViewHolder {
    public GroupsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final String text) {
        final TextView textView = itemView.findViewById(R.id.row_group_name);

        textView.setText(text);
    }

}
