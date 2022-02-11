package com.td.wallendar.repositories

import com.td.wallendar.dtos.request.AddChargeRequest
import com.td.wallendar.models.Charge
import com.td.wallendar.repositories.interfaces.ChargesRepository
import com.td.wallendar.service.ChargesService
import com.td.wallendar.utils.mappers.ChargeMapper
import com.td.wallendar.utils.networking.RetrofitUtils
import io.reactivex.Single

class ChargesRepositoryImpl(val chargesService: ChargesService) : ChargesRepository {
    override fun addCharge(groupId: Long, addChargeRequest: AddChargeRequest): Single<Charge> {
        return RetrofitUtils.performRequest(chargesService.addCharge(groupId, addChargeRequest)).map { ChargeMapper.toModel(it) }
    }
}