package com.td.wallendar.groupbalance;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Debt;

import java.text.DecimalFormat;

public class GroupBalanceViewHolder extends RecyclerView.ViewHolder {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private OnGroupSettleUpClickedListener onGroupSettleUpClickedListener;

    public GroupBalanceViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final Debt debt, final long loggedUserId) {
        final TextView textView = itemView.findViewById(R.id.row_balance_text);
        final Button remindButton = itemView.findViewById(R.id.remind_debt_button);
        final Button settleUpButton = itemView.findViewById(R.id.settle_up_debt_button);

        String amount = df.format(debt.getAmount());
        if (debt.getFrom().getId() == loggedUserId) {
            remindButton.setVisibility(View.GONE);
            String userToText = debt.getTo().getFirstName() + " " + debt.getTo().getLastName();
            textView.setText(itemView.getContext().getString(R.string.you_owe_to_user_amount, userToText, amount));
        } else if (debt.getTo().getId() == loggedUserId){
            String userFromText = debt.getFrom().getFirstName() + " " + debt.getFrom().getLastName();
            textView.setText(itemView.getContext().getString(R.string.user_owes_you_amount, userFromText, amount));
        } else {
            String userFromText = debt.getFrom().getFirstName() + " " + debt.getFrom().getLastName();
            String userToText = debt.getTo().getFirstName() + " " + debt.getTo().getLastName();
            textView.setText(itemView.getContext().getString(R.string.user_owes_to_user_amount, userFromText, userToText, amount));
        }

        if(debt.getAmount() == 0){
            remindButton.setVisibility(View.GONE);
            settleUpButton.setVisibility(View.GONE);
        }

        remindButton.setOnClickListener(view ->
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.feature_not_ready), Toast.LENGTH_SHORT).show());

        settleUpButton.setOnClickListener(view -> onGroupSettleUpClickedListener.onGroupSettleUpClick(debt));
    }

    public void setOnGroupSettleUpClickedListener(OnGroupSettleUpClickedListener listener) {
        this.onGroupSettleUpClickedListener = listener;
    }

}