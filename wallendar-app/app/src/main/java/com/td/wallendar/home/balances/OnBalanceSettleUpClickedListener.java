package com.td.wallendar.home.balances;

import com.td.wallendar.models.Debt;

public interface OnBalanceSettleUpClickedListener {
    void onBalanceSettleUpClick(long groupId, Debt debt);
}