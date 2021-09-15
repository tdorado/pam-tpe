package com.td.wallendar.home.balances.ui;

import com.td.wallendar.AbstractView;
import com.td.wallendar.models.Debt;

import java.util.List;

public interface BalancesView extends AbstractView {
    void bind();

    void listBalances(final List<Debt> balances);
}
