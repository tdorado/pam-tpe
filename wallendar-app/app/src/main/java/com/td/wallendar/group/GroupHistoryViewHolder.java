package com.td.wallendar.group;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.GroupHistory;
import com.td.wallendar.models.GroupHistoryType;

import java.text.SimpleDateFormat;

public class GroupHistoryViewHolder extends RecyclerView.ViewHolder {
    public GroupHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @SuppressLint("SimpleDateFormat")
    public void bind(final GroupHistory history) {
        final TextView month = itemView.findViewById(R.id.group_activity_date_month);
        final TextView day = itemView.findViewById(R.id.group_activity_date_number);
        final TextView title = itemView.findViewById(R.id.group_activity_title);
        final TextView amount = itemView.findViewById(R.id.group_activity_amount);

        if (history.getGroupHistoryType() == GroupHistoryType.CHARGE) {
            title.setVisibility(View.VISIBLE);
            title.setText(history.getTitle());
            String userText = history.getFromUser().getFirstName() + " " + history.getFromUser().getLastName();
            String amountText = String.valueOf(history.getAmount());

            amount.setText(itemView.getContext().getString(R.string.user_paid_amount, userText, amountText));
        } else {
            title.setVisibility(View.GONE);
            String userFromText = history.getFromUser().getFirstName() + " " + history.getFromUser().getLastName();
            String amountText = String.valueOf(history.getAmount());
            String userToText = history.getToUser().getFirstName() + " " + history.getToUser().getLastName();

            amount.setText(itemView.getContext().getString(R.string.user_paid_amount_to, userFromText, amountText, userToText));
        }

        month.setText(new SimpleDateFormat("MMM").format(history.getDate()));
        day.setText(new SimpleDateFormat("dd").format(history.getDate()));
    }
}