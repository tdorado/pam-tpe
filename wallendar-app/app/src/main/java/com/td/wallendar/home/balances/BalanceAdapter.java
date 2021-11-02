package com.td.wallendar.home.balances;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.home.groups.OnGroupClickedListener;
import com.td.wallendar.models.Debt;

import java.util.ArrayList;
import java.util.List;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceViewHolder> {

    private final List<Debt> dataset;
    private long loggedUserId;
    private OnBalanceSettleUpClickedListener onBalanceSettleUpClickedListener;

    public BalanceAdapter(final long loggedUserId) {
        this.dataset = new ArrayList<>();
        this.loggedUserId = loggedUserId;
    }

    public void setOnBalanceSettleUpClickedListener(OnBalanceSettleUpClickedListener listener) {
        this.onBalanceSettleUpClickedListener = listener;
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_balances, parent, false);
        return new BalanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        holder.bind(dataset.get(position), loggedUserId);
        holder.setOnBalanceSettleUpClickedListener(onBalanceSettleUpClickedListener);
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataset(final List<Debt> newDataset) {
        this.dataset.clear();
        if(newDataset != null){
            this.dataset.addAll(newDataset);
        }
        notifyDataSetChanged();
    }
}