package com.td.wallendar.home.balances.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.td.wallendar.home.balances.BalancesAdapter;
import com.td.wallendar.models.Debt;
import com.td.wallendar.repositories.DebtsRepositoryImpl;

import java.lang.ref.WeakReference;
import java.util.List;

public class BalancesViewImpl extends RecyclerView implements BalancesView {

    private BalancesAdapter balancesAdapter;
    private BalancesPresenter balancesPresenter;

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
    public void bind() {

        balancesAdapter = new BalancesAdapter();

        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setAdapter(balancesAdapter);

        buildPresenter();

        balancesPresenter.getBalances(getUserId());
    }

    @Override
    public void listBalances(List<Debt> balances) {
        balancesAdapter.setData(balances);
    }

    @Override
    public void buildPresenter() {
        balancesPresenter = new BalancesPresenter(new WeakReference<>(this), new DebtsRepositoryImpl());
    }

    @Override
    public Long getUserId() {
        return 1L;
    }
}
