package com.td.wallendarbackend.repositories;

import com.td.wallendarbackend.models.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository  extends JpaRepository<Debt, Long> {
}
