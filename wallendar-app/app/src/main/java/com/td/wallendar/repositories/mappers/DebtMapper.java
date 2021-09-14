package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.DebtResponse;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.User;

public class DebtMapper {
    public static Debt toModel(DebtResponse debtResponse) {
        Debt debt = new Debt();

        User to = new User();
        to.setEmail(debtResponse.getUser());
        debt.setTo(to);

        debt.setAmount(debtResponse.getAmount());

        return debt;
    }
}
