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

    public void bind(final Debt debt) {
        final TextView textView = itemView.findViewById(R.id.row_balance_text);
        final Button remindButton = itemView.findViewById(R.id.remind_debt_button);
        // TODO
        remindButton.setOnClickListener(view -> Toast.makeText(view.getContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT)
                .show());
        itemView.findViewById(R.id.settle_up_debt_button).setOnClickListener(view -> Toast.makeText(view.getContext(), "Esta funcionalidad está en desarrollo todavía :)", Toast.LENGTH_SHORT)
                .show());

        String userFrom = debt.getFrom().getEmail();
        String userTo = debt.getTo().getEmail();
        String amount = String.valueOf(debt.getAmount());
        if (userFrom.equals("tdallas@itba.edu.ar")) {
            remindButton.setVisibility(View.GONE);
            textView.setText(itemView.getContext().getString(R.string.you_owe_to_user_amount, userTo, amount));
        } else {
            textView.setText(itemView.getContext().getString(R.string.user_owes_you_amount, userFrom, amount));
        }
    }

    public void setOnBalanceSettleUpClickedListener(OnBalanceSettleUpClickedListener listener) {
        this.onBalanceSettleUpClickedListener = listener;
    }

}