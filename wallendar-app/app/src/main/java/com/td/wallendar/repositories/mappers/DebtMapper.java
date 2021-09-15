package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.DebtResponse;
import com.td.wallendar.dtos.response.TotalDebtsResponse;
import com.td.wallendar.models.Debt;
import com.td.wallendar.models.User;

import java.util.ArrayList;
import java.util.List;

public class DebtMapper {
    public static List<Debt> toModel(TotalDebtsResponse totalDebtsResponse) {
        List<Debt> result = new ArrayList<>();
        for(DebtResponse debtResponse: totalDebtsResponse.getDebts()){
            Debt debt = new Debt();

            debt.setTo(new User(debtResponse.getUserTo(), "", "", ""));
            debt.setFrom(new User(debtResponse.getUserFrom(), "", "", ""));
            debt.setAmount(debtResponse.getAmount());
            debt.setSettledUp(debtResponse.isSettledUp());

            result.add(debt);
        }
        return result;
    }
}
