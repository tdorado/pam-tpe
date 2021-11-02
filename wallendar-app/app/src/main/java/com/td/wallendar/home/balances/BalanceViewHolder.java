package com.td.wallendar.home.balances;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Debt;

public class BalanceViewHolder extends RecyclerView.ViewHolder {
    private OnBalanceSettleUpClickedListener onBalanceSettleUpClickedListener;

    public BalanceViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final Debt debt, final long loggedUserId) {
        final TextView textView = itemView.findViewById(R.id.row_balance_text);
        final Button remindButton = itemView.findViewById(R.id.remind_debt_button);
        // TODO
        remindButton.setOnClickListener(view -> Toast.makeText(view.getContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT)
                .show());
        itemView.findViewById(R.id.settle_up_debt_button).setOnClickListener(
                view -> Toast.makeText(view.getContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT).show());

        String amount = String.valueOf(debt.getAmount());
        if (debt.getFrom().getId() == loggedUserId) {
            remindButton.setVisibility(View.GONE);
            String userToText = debt.getTo().getFirstName() + " " + debt.getTo().getLastName();
            textView.setText(itemView.getContext().getString(R.string.you_owe_to_user_amount, userToText, amount));
        } else {
            String userFromText = debt.getFrom().getFirstName() + " " + debt.getFrom().getLastName();
            textView.setText(itemView.getContext().getString(R.string.user_owes_you_amount, userFromText, amount));
        }
    }

    public void setOnBalanceSettleUpClickedListener(OnBalanceSettleUpClickedListener listener) {
        this.onBalanceSettleUpClickedListener = listener;
    }

}