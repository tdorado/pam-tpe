package com.td.wallendar.repositories.interfaces

import com.td.wallendar.dtos.request.AddChargeRequest
import com.td.wallendar.models.Charge
import io.reactivex.Single

interface ChargesRepository {
    fun addCharge(groupId: Long, addChargeRequest: AddChargeRequest, isEvent: Boolean): Single<Charge>
}