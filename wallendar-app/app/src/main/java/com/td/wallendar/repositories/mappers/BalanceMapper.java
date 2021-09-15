package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.BalanceResponse;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.User;

public class BalanceMapper {
    public static Debt toModel(BalanceResponse balanceResponse) {
        Debt debt = new Debt();

        User to = new User();
        to.setEmail(balanceResponse.getUser());
        debt.setTo(to);

        debt.setAmount(balanceResponse.getAmount());

        return debt;
    }
}
