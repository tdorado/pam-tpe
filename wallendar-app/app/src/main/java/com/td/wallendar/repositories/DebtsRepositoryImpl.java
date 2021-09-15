package com.td.wallendar.repositories;

import com.td.wallendar.ServiceModule;
import com.td.wallendar.models.Debt;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.repositories.mappers.DebtMapper;
import com.td.wallendar.service.DebtsServices;

import java.util.List;

import io.reactivex.Observable;

public class DebtsRepositoryImpl implements DebtsRepository {

    private DebtsServices debtsServices;

    public DebtsRepositoryImpl() {
        debtsServices = ServiceModule.getRetrofit().create(DebtsServices.class);
    }

    @Override
    public Observable<List<Debt>> getTotalDebtsByUserId(Long userId) {
        return debtsServices.getTotalDebtsByUserId(userId).map(DebtMapper::toModel);
    }
}
