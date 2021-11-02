package com.td.wallendar.repositories;

import com.td.wallendar.models.Debt;
import com.td.wallendar.repositories.interfaces.DebtsRepository;
import com.td.wallendar.service.DebtsService;
import com.td.wallendar.utils.mappers.DebtMapper;
import com.td.wallendar.utils.networking.RetrofitUtils;

import java.util.List;

import io.reactivex.Single;

public class DebtsRepositoryImpl implements DebtsRepository {

    private final DebtsService debtsService;

    public DebtsRepositoryImpl(DebtsService debtsService) {
        this.debtsService = debtsService;
    }

    @Override
    public Single<List<Debt>> getTotalDebtsByUserId(Long userId) {
        return RetrofitUtils.performRequest(debtsService.getTotalDebtsByUserId(userId)).map(DebtMapper::toModel);
    }
}
