package com.td.wallendar.dtos.response;

public class AddChargeResponse {
    private Long chargeId;

    public AddChargeResponse(Long chargeId) {
        this.chargeId = chargeId;
    }

    public AddChargeResponse() {
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }
}
