package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.models.Charge;

import io.reactivex.Observable;

public interface ChargesRepository {
    Observable<Charge> addCharge(final long groupId, final AddChargeRequest addChargeRequest);
}
