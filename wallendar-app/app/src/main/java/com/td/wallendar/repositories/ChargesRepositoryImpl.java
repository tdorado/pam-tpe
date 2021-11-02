package com.td.wallendar.repositories;

import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.models.Charge;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.service.ChargesService;
import com.td.wallendar.utils.mappers.ChargeMapper;
import com.td.wallendar.utils.networking.RetrofitUtils;

import io.reactivex.Single;

public class ChargesRepositoryImpl implements ChargesRepository {

    public final ChargesService chargesService;

    public ChargesRepositoryImpl(ChargesService chargesService) {
        this.chargesService = chargesService;
    }

    @Override
    public Single<Charge> addCharge(final long groupId, final AddChargeRequest addChargeRequest) {
        return RetrofitUtils.performRequest(chargesService.addCharge(groupId, addChargeRequest)).map(ChargeMapper::toModel);
    }
}
