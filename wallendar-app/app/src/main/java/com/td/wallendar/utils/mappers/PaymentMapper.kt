package com.td.wallendar.utils.mappers

import com.td.wallendar.dtos.response.PaymentResponse
import com.td.wallendar.models.Payment
import java.util.*

object PaymentMapper {
    fun toModel(paymentResponse: PaymentResponse): Payment{
        return Payment(
                paymentResponse.id,
                ApplicationUserMapper.toModel(paymentResponse.from),
                ApplicationUserMapper.toModel(paymentResponse.to),
                paymentResponse.amount,
                paymentResponse.date
        )
    }

    fun toModel(paymentResponseList: MutableSet<PaymentResponse>): MutableSet<Payment>{
        val payments: MutableSet<Payment> = HashSet()
        for (paymentResponse in paymentResponseList) {
            payments.add(toModel(paymentResponse))
        }
        return payments
    }
}