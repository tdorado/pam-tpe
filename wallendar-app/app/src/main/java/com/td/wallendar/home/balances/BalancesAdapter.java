package com.td.wallendar.home.balances;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.R;
import com.td.wallendar.home.balances.BalancesViewHolder;

import java.util.Arrays;
import java.util.List;

public class BalancesAdapter extends RecyclerView.Adapter<BalancesViewHolder> {

    private List<String> testDataset = Arrays.asList("John Doe owes $1500 to you", "John Doe owes $500 to you", "You owe $1500 to John Doe");

    @NonNull
    @Override
    public BalancesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_balances, parent, false);
        return new BalancesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BalancesViewHolder holder, int position) {
        holder.bind(testDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return testDataset == null ? 0 : testDataset.size();
    }
}