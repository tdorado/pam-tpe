package com.td.wallendar.utils.mappers

import com.td.wallendar.dtos.response.ChargeResponse
import com.td.wallendar.models.Charge
import java.util.*

object ChargeMapper {
    fun toModel(chargeResponse: ChargeResponse): Charge {
        return Charge(
                chargeResponse.id,
                chargeResponse.title,
                ApplicationUserMapper.toModel(chargeResponse.owner),
                ApplicationUserMapper.toModel(chargeResponse.debtors),
                chargeResponse.amount,
                chargeResponse.date
        )
    }

    fun toModel(chargeResponseList: MutableSet<ChargeResponse>): MutableSet<Charge> {
        val charges: MutableSet<Charge> = HashSet()
        for (chargeResponse in chargeResponseList) {
            charges.add(toModel(chargeResponse))
        }
        return charges
    }
}