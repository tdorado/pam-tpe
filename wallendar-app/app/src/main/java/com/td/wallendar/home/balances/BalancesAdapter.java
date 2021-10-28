package com.td.wallendar.home.balances;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Debt;

import java.util.List;

public class BalancesAdapter extends RecyclerView.Adapter<BalancesViewHolder> {

    private List<Debt> dataset;

    @NonNull
    @Override
    public BalancesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_balances, parent, false);
        return new BalancesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BalancesViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(final List<Debt> debts) {
        this.dataset = debts;
        notifyDataSetChanged();
    }
}