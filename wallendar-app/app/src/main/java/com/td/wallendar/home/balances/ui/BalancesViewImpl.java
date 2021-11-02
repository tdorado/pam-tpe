package com.td.wallendar.home.balances.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.home.balances.BalanceAdapter;

public class BalancesViewImpl extends RecyclerView implements BalancesView {

    public BalancesViewImpl(Context context) {
        this(context, null);
    }

    public BalancesViewImpl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BalancesViewImpl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bind(final BalanceAdapter balanceAdapter) {
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setAdapter(balanceAdapter);
    }

}
