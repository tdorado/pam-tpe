package com.td.wallendar.home.balances;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Debt;

public class BalancesViewHolder extends RecyclerView.ViewHolder {
    public BalancesViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final Debt debt) {
        final TextView textView = itemView.findViewById(R.id.row_balance_text);
        final Button remindButton = itemView.findViewById(R.id.remind_debt_button);

        String userFrom = debt.getFrom().getEmail();
        String userTo = debt.getTo().getEmail();
        String amount = String.valueOf(debt.getAmount());
        if(userFrom.equals("tdallas@itba.edu.ar")){
            remindButton.setVisibility(View.GONE);
            textView.setText(itemView.getContext().getString(R.string.you_owe_to_user_amount, userTo, amount));
        }
        else{
            textView.setText(itemView.getContext().getString(R.string.user_owes_you_amount, userFrom, amount));
        }
    }

}