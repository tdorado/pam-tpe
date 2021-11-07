package com.td.wallendar.groupmembers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.ApplicationUser;

public class GroupMembersViewHolder extends RecyclerView.ViewHolder {

    public GroupMembersViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final ApplicationUser applicationUser) {
        final TextView textView = itemView.findViewById(R.id.member_text_name);
        String userNameText = applicationUser.getFirstName() + " " + applicationUser.getLastName();
        textView.setText(userNameText);

    }

}