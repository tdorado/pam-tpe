package com.td.wallendar.addmembers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;

public class MembersViewHolder extends RecyclerView.ViewHolder {
    public MembersViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    // TODO agregar para remover email y setear listener etc etc
    public void bind(final String memberEmail) {
        final TextView textView = itemView.findViewById(R.id.member_text_mail);

        textView.setText(memberEmail);
    }
}
