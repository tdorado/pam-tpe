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
            title.setText(history.getTitle());
            String userText = history.getFromUser().getEmail();
            String amountText = String.valueOf(history.getAmount());

            amount.setText(itemView.getContext().getString(R.string.user_paid_amount, userText, amountText));
            title.setVisibility(View.VISIBLE);
        } else {
            // deuda pagada no tiene titulo entonces lo hago gone
            title.setVisibility(View.GONE);

            // faltaria poner saber quien pago a quien que cantidad y ponerlo en amount
        }

        // fecha va en deuda pagada y charge
        month.setText(new SimpleDateFormat("MMM").format(history.getDate()));
        day.setText(new SimpleDateFormat("dd").format(history.getDate()));

    }
}