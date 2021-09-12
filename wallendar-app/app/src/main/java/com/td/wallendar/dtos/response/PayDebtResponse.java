package com.td.wallendar.dtos.response;

public class PayDebtResponse {
    private double amountPayed;

    public PayDebtResponse(double amountPayed) {
        this.amountPayed = amountPayed;
    }

    public PayDebtResponse() {
    }

    public double getAmountPayed() {
        return amountPayed;
    }

    public void setAmountPayed(double amountPayed) {
        this.amountPayed = amountPayed;
    }
}
