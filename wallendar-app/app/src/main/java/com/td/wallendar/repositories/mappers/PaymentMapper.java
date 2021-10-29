package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.BalanceResponse;
import com.td.wallendar.dtos.response.PaymentResponse;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.Payment;

import java.util.HashSet;
import java.util.Set;

public class PaymentMapper {
    public static Payment toModel(PaymentResponse paymentResponse) {
        return new Payment(
                paymentResponse.getId(),
                ApplicationUserMapper.toModel(paymentResponse.getFrom()),
                ApplicationUserMapper.toModel(paymentResponse.getTo()),
                paymentResponse.getAmount(),
                paymentResponse.getDate()
        );
    }

    public static Set<Payment> toModel(Set<PaymentResponse> paymentResponseList) {
        Set<Payment> payments = new HashSet<>();
        for (PaymentResponse paymentResponse : paymentResponseList) {
            payments.add(toModel(paymentResponse));
        }
        return payments;
    }
}
