package com.td.wallendar.home.balances;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;

public class BalancesViewHolder extends RecyclerView.ViewHolder {
    public BalancesViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(final String text) {
        final TextView textView = itemView.findViewById(R.id.row_balance_text);

        textView.setText(text);
    }

}