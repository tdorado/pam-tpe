package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.models.Charge;

import io.reactivex.Single;

public interface ChargesRepository {
    Single<Charge> addCharge(final long groupId, final AddChargeRequest addChargeRequest);
}
