package com.td.wallendar.groupbalance.ui;

import com.td.wallendar.models.Debt;

import java.util.List;

public interface GroupBalanceView {
    void bind(List<Debt> debts);

    void failedToLoadDebts();

    void failedToSettleUpDebt();

    void onSettleUpPaymentDone(Debt debt);
}
