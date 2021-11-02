package com.td.wallendar.home.groups;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Group;

public class GroupViewHolder extends RecyclerView.ViewHolder {

    private OnGroupClickedListener listener;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final Group group) {
        final TextView textView = itemView.findViewById(R.id.row_group_name);

        //TODO setear el resto de la data del row
        textView.setText(group.getTitle());

        itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onGroupClicked(group.getId());
            }
        });
    }

    public void setOnGroupClickedListener(OnGroupClickedListener listener) {
        this.listener = listener;
    }
}
