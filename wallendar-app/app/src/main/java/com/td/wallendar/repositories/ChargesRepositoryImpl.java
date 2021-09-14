package com.td.wallendar.repositories;

import com.td.wallendar.ServiceModule;
import com.td.wallendar.dtos.request.AddChargeRequest;
import com.td.wallendar.dtos.response.AddChargeResponse;
import com.td.wallendar.models.Charge;
import com.td.wallendar.repositories.interfaces.ChargesRepository;
import com.td.wallendar.service.ChargesService;

import io.reactivex.Observable;

public class ChargesRepositoryImpl implements ChargesRepository {

    public ChargesService chargesService;

    public ChargesRepositoryImpl() {
        this.chargesService = ServiceModule.getRetrofit().create(ChargesService.class);
    }
    @Override
    public Observable<AddChargeResponse> addCharge(Charge charge) {
        return chargesService.addCharge(AddChargeRequest.from(charge));
    }
}
