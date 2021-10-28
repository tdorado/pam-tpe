package com.td.wallendar.repositories.mappers;

import com.td.wallendar.dtos.response.DebtResponse;
import com.td.wallendar.dtos.response.TotalDebtsResponse;
import com.td.wallendar.models.ApplicationUser;
import com.td.wallendar.models.Debt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DebtMapper {
    public static Debt toModel(DebtResponse debtResponse) {
        return new Debt(
                debtResponse.getId(),
                ApplicationUserMapper.toModel(debtResponse.getFrom()),
                ApplicationUserMapper.toModel(debtResponse.getTo()),
                debtResponse.getAmount()
        );
    }

    public static Set<Debt> toModel(Set<DebtResponse> debtResponses) {
        Set<Debt> debts = new HashSet<>();
        for (DebtResponse debtResponse : debtResponses) {
            debts.add(toModel(debtResponse));
        }
        return debts;
    }

    public static List<Debt> toModel(List<DebtResponse> debtResponses) {
        List<Debt> debts = new ArrayList<>();
        for (DebtResponse debtResponse : debtResponses) {
            debts.add(toModel(debtResponse));
        }
        return debts;
    }
}
