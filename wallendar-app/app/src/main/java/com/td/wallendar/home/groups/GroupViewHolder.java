package com.td.wallendar.home.groups;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Group;

import java.text.DecimalFormat;

public class GroupViewHolder extends RecyclerView.ViewHolder {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private OnGroupClickedListener listener;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final Group group, final long loggedUserId) {
        final TextView groupTitleTextView = itemView.findViewById(R.id.row_group_name);
        final TextView amountOwedTextView = itemView.findViewById(R.id.row_you_are_owed);
        final TextView amountYouOweTextView = itemView.findViewById(R.id.row_you_owe);

        double amountOwed = 0;
        double amountYouOwe = 0;

        for(Debt debt : group.getDebts()){
            if(debt.getFrom().getId() == loggedUserId) {
                amountYouOwe += debt.getAmount();
            }
            if(debt.getTo().getId() == loggedUserId) {
                amountOwed += debt.getAmount();
            }
        }

        groupTitleTextView.setText(group.getTitle());
        String amountOwedString = itemView.getContext().getString(R.string.you_are_owed, df.format(amountOwed));
        amountOwedTextView.setText(amountOwedString);
        String amountYouOweString = itemView.getContext().getString(R.string.you_owe, df.format(amountYouOwe));
        amountYouOweTextView.setText(amountYouOweString);

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
