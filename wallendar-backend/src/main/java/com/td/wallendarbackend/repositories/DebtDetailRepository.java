package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.DebtDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebtDetailRepository extends JpaRepository<DebtDetail, Long> {
    @Query("SELECT d FROM DebtDetail d WHERE d.debt.id = :debtId")
    List<DebtDetail> findAllByDebtId(@Param("debtId") long debtId);
}
