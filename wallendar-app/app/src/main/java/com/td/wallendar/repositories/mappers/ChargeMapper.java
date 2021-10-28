package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.ChargeResponse;
import com.td.wallendar.models.Charge;

import java.util.HashSet;
import java.util.Set;

public class ChargeMapper {
    public static Charge toModel(ChargeResponse chargeResponse) {
        return new Charge(
                chargeResponse.getTitle(),
                ApplicationUserMapper.toModel(chargeResponse.getOwner()),
                ApplicationUserMapper.toModel(chargeResponse.getDebtors()),
                chargeResponse.getAmount(),
                chargeResponse.getDate()
        );
    }

    public static Set<Charge> toModel(Set<ChargeResponse> chargeResponseList) {
        Set<Charge> charges = new HashSet<>();
        for (ChargeResponse chargeResponse : chargeResponseList) {
            charges.add(toModel(chargeResponse));
        }
        return charges;
    }
}
