package com.td.wallendar.dtos.response;

import java.util.List;

public class TotalDebtsResponse {
    private List<DebtResponse> debts;

    public TotalDebtsResponse() {
    }

    public TotalDebtsResponse(List<DebtResponse> debts) {
        this.debts = debts;
    }

    public List<DebtResponse> getDebts() {
        return debts;
    }

    public void setDebts(List<DebtResponse> debts) {
        this.debts = debts;
    }
}
