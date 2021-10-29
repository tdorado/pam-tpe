package com.td.wallendarbackend.dtos.responses;

import com.td.wallendarbackend.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private long id;
    private ApplicationUserResponse from;
    private ApplicationUserResponse to;
    private double amount;
    private Date date;
    private long groupId;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.from = new ApplicationUserResponse(payment.getFrom());
        this.to = new ApplicationUserResponse(payment.getTo());
        this.amount = payment.getAmount();
        this.date = payment.getDate();
        this.groupId = payment.getGroup().getId();
    }
}
