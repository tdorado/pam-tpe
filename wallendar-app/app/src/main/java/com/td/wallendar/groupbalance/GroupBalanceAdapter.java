package com.td.wallendar.groupbalance;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.models.Debt;

import java.util.ArrayList;
import java.util.List;

public class GroupBalanceAdapter extends RecyclerView.Adapter<GroupBalanceViewHolder> {

    private final List<Debt> dataset;
    private final long loggedUserId;
    private OnGroupSettleUpClickedListener onGroupSettleUpClickedListener;
    private OnGroupRemindClickedListener onGroupRemindClickedListener;

    public GroupBalanceAdapter(final long loggedUserId) {
        this.dataset = new ArrayList<>();
        this.loggedUserId = loggedUserId;
    }

    public void setOnGroupSettleUpClickedListener(OnGroupSettleUpClickedListener listener) {
        this.onGroupSettleUpClickedListener = listener;
    }

    public void setOnGroupRemindClickedListener(OnGroupRemindClickedListener listener) {
        this.onGroupRemindClickedListener = listener;
    }

    @NonNull
    @Override
    public GroupBalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_balances, parent, false);
        return new GroupBalanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupBalanceViewHolder holder, int position) {
        holder.bind(dataset.get(position), loggedUserId);
        holder.setOnGroupSettleUpClickedListener(onGroupSettleUpClickedListener);
        holder.setOnGroupRemindClickedListener(onGroupRemindClickedListener);
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataset(final List<Debt> newDataset) {
        this.dataset.clear();
        if (newDataset != null) {
            this.dataset.addAll(newDataset);
        }
        notifyDataSetChanged();
    }

    public void removeFromDataset(Debt debt) {
        int position = this.dataset.indexOf(debt);
        this.dataset.remove(position);
        notifyItemRemoved(position);
    }
}