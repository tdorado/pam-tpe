package com.td.wallendar.repositories.interfaces;

import com.td.wallendar.models.Debt;

import java.util.List;

import io.reactivex.Observable;

public interface DebtsRepository {
    Observable<List<Debt>> getTotalDebtsByUserId(final Long userId);
}
