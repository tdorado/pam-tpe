package com.td.wallendar.home.views.balances;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class BalancesViewImpl extends LinearLayout implements BalancesView {
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

    }
}
