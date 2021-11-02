package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.models.Debt;

import java.util.List;

import io.reactivex.Single;

public interface DebtsRepository {
    Single<List<Debt>> getTotalDebtsByUserId(final Long userId);
}
