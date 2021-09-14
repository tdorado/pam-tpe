package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.dtos.response.AddChargeResponse;
import com.td.wallendar.models.Charge;

import io.reactivex.Observable;

public interface ChargesRepository {
    Observable<AddChargeResponse> addCharge(final Charge charge);
}
